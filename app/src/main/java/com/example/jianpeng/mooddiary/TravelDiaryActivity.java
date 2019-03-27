package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TravelDiaryActivity extends BaseCompatActivity {

    private ArrayList<Travel_Diary> traveldiaryList = new ArrayList<Travel_Diary>();
    private ProgressDialog progressDialog;
    Travel_Diary_ArrayAdapter traveldiaryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Update Travel Diary ...");
        if (!progressDialog.isShowing())
            progressDialog.show();

        GetMoodTagRequest(User.getUserName());
        Travel_Diary_List.clearTravelDiary();
    }


    public void initListView(){
        SortTravelDiary();
        traveldiaryArrayAdapter =
                new Travel_Diary_ArrayAdapter(
                        getBaseContext(),
                        R.layout.layout_travel_diary_item, // the layout for each item in the list
                        traveldiaryList); // the arrayList to feed the arrayAdapter

        ListView listView = findViewById(R.id.listView_TravelDiary);
        listView.setAdapter(traveldiaryArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Travel_Diary_List.setonClickItem(i);
                Intent intent = new Intent(getApplicationContext(), Travel_Diary_Show_Activity.class);
                startActivity(intent);
            }
        });
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }



    private void SortTravelDiary(){
        Travel_Diary temptraveldiary=new Travel_Diary();
        Date d1,d2;
        for (int i=0; i<traveldiaryList.size()-1;i++){
            for(int j=i+1;j<traveldiaryList.size();j++){
                d1=traveldiaryList.get(i).getTime();
                d2=traveldiaryList.get(j).getTime();
                if(d1.before(d2)){
                    temptraveldiary=traveldiaryList.get(i);
                    traveldiaryList.set(i,traveldiaryList.get(j));
                    traveldiaryList.set(j,temptraveldiary);
                }
            }
        }
        for (int k=0;k<traveldiaryList.size();k++){
            Travel_Diary_List.addTravelDiary(traveldiaryList.get(k));
        }
    }



    private void generateDummyContent(JSONObject jsonObject) {

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("TravelDiaryArray");
            JSONArray jsonArrayName=jsonObject.getJSONArray("TravelDiaryBmpNameArray");
            int TravelDiaryNumber=jsonArray.length();
            int PictureNumber=jsonArrayName.length();
            if(TravelDiaryNumber==0)
                showToast("You don't have any Travel Diary, just add it.",Toast.LENGTH_LONG);
            else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subjsonObject = (JSONObject) jsonArray.get(i);

                    String strdate = subjsonObject.getString("strDate");
                    String strtext = subjsonObject.getString("strText");

                    SimpleDateFormat df = new SimpleDateFormat ("HH:mm:ss dd/MM/yyyy");
                    try {
                        Date date = df.parse(strdate);
                        Travel_Diary temtraveldairy=new Travel_Diary(date, strtext);
                        traveldiaryList.add(temtraveldairy);
                    }
                    catch (ParseException e){
                        showToast("Update the Travel Diary failure, please try again later!", Toast.LENGTH_SHORT);
                    }

                }
            }
            for(int j=0;j<PictureNumber;j++){
                JSONObject subjsonObjectName=(JSONObject)jsonArrayName.get(j);
                String strPictureName=subjsonObjectName.getString("bmpname");
                Travel_Diary_List.addTravelDiaryPictureName(strPictureName);
            }

            new DownloadPicture().execute();

        } catch (JSONException e) {
            showToast("Update the Travel Diary failure, please try again later!", Toast.LENGTH_SHORT);
        }

        //SortTravelDiary();
        //initListView();

    }



    public void GetMoodTagRequest(final String username) {
        final String url = new Config().getGetTravelDiaryUrl();
        final String tag = "GetTravelDiary";
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
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("TravelDiary");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                //成功操作
                                generateDummyContent(jsonObject);
                            } else {
                                //失败操作
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Can not update the Travel Diary, please try again later!", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Can not update the Travel Diary, please try again later!", Toast.LENGTH_SHORT);
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
                showToast("Connection failure, please try again later!", Toast.LENGTH_LONG);
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





    public void onClickBack(View view) {
        onBackPressed();
    }


    public void onClickcAddTravelDiary(View view) {
        Intent intent = new Intent(getApplicationContext(), Travel_Diary_Add_Activity.class);
        startActivity(intent);
        finish();
    }


    //show Toast
    public void showToast(String str, int showTime) {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //set the display location
        toast.show();
    }

    public void onClickcUpDate(View view) {
        traveldiaryArrayAdapter.notifyDataSetChanged();
    }




    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("Result");
            //将数据进行显示到界面等操作
            if(val.equals("OK")){
                initListView();
            }
        }
    };




    public class DownloadPicture extends AsyncTask<String,Void,String> {

        String path=BitmapUtil.getDeafaultFilePath();
        String checkDownload;
        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> TravelDiaryPictureNameList=Travel_Diary_List.getTravelDiaryPictureNameList();
            for(int i=0;i<TravelDiaryPictureNameList.size();i++){
                String name=TravelDiaryPictureNameList.get(i);
                String Bmppath=path+name;
                File f=new File(Bmppath);
                if(!f.exists())
                {
                    checkDownload=DownloadUtils.downloadFile(name);
                }
            }
            return checkDownload;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Message msg = Message.obtain();
            Bundle data = new Bundle();
            data.putString("Result", "OK");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }
}
