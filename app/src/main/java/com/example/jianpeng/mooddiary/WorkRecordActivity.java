package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkRecordActivity extends BaseCompatActivity {

    private ArrayList<Work_Record> workrecordList = new ArrayList<Work_Record>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_record);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Update Work Record ...");
        if (!progressDialog.isShowing())
            progressDialog.show();

        GetWorkRecordRequest(User.getUserName());
    }


    private void SortWorkRecord(){
        Work_Record tempworkrecord=new Work_Record();
        Date d1,d2;
        for (int i=0; i<workrecordList.size()-1;i++){
            for(int j=i+1;j<workrecordList.size();j++){
                d1=workrecordList.get(i).getTime();
                d2=workrecordList.get(j).getTime();
                if(d1.before(d2)){
                    tempworkrecord=workrecordList.get(i);
                    workrecordList.set(i,workrecordList.get(j));
                    workrecordList.set(j,tempworkrecord);
                }
            }
        }
    }


    private void generateDummyContent(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("WorkRecordArray");
            int MoodTagNumber=jsonArray.length();
            if(MoodTagNumber==0)
                showToast("You don't have any Work Record, just add it.",Toast.LENGTH_LONG);
            else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subjsonObject = (JSONObject) jsonArray.get(i);

                    String strdate = subjsonObject.getString("strDate");
                    String strtext = subjsonObject.getString("strText");

                    SimpleDateFormat df = new SimpleDateFormat ("HH:mm:ss dd/MM/yyyy");
                    try {
                        Date date = df.parse(strdate);
                        workrecordList.add(new Work_Record(date, strtext));
                    }
                    catch (ParseException e){
                        showToast("Update the Work Record failure, please try again later!", Toast.LENGTH_SHORT);
                    }

                }
            }

        } catch (JSONException e) {
            showToast("Update the Work Record failure, please try again later!", Toast.LENGTH_SHORT);
        }

        SortWorkRecord();

        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Work_Record_ArrayAdapter workrecordArrayAdapter =
                new Work_Record_ArrayAdapter(
                        getBaseContext(),
                        R.layout.layout_work_record_item, // the layout for each item in the list
                        workrecordList); // the arrayList to feed the arrayAdapter

        ListView listView = findViewById(R.id.listView_WorkRecord);
        listView.setAdapter(workrecordArrayAdapter);
    }



    public void GetWorkRecordRequest(final String username) {
        final String url = new Config().getGetWorkRecordUrl();
        final String tag = "GetMoodTag";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("WorkRecord");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                //做自己的登录成功操作，如页面跳转
                                generateDummyContent(jsonObject);
                            } else {
                                //做自己的登录失败操作，如Toast提示
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Can not update the Work Record, please try again later!", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Can not update the Work Record, please try again later!", Toast.LENGTH_SHORT);
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //响应错误操作
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                showToast("No network connection, please try again later!", Toast.LENGTH_LONG);
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", username);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }


    //show Toast
    public void showToast(String str, int showTime) {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //set the display location
        //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        //v.setTextColor(getResources().getColor(R.color.messageTextClor));
        toast.show();
    }




    public void onClickBack(View view) {
        onBackPressed();
    }


    public void onClickAddWorkRecord(View view) {
        Intent intent = new Intent(getApplicationContext(), Work_Record_Add_Activity.class);
        startActivity(intent);
        finish();
    }
}
