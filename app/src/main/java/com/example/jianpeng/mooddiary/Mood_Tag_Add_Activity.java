package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;




public class Mood_Tag_Add_Activity extends AppCompatActivity implements DatePicker.OnDateChangedListener{

    private ProgressDialog progressDialog;
    private Context context;
    private EditText et_MoodTagText, et_MoodTagDate;
    private Button saveButton;
    private int year, month, day, hour, minute, second;
    private StringBuffer strdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood__tag__add);

        et_MoodTagText=findViewById(R.id.et_addMoodTagText);
        et_MoodTagDate=findViewById(R.id.et_addMoodTagDate);
        saveButton=findViewById(R.id.btn_addMoodTagSave);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        context = this;
        strdate = new StringBuffer();

        if (et_MoodTagDate !=null){
            et_MoodTagDate.setCursorVisible(false);      //设置输入框中的光标不可见
            et_MoodTagDate.setFocusable(false);
            et_MoodTagDate.setFocusableInTouchMode(false);     //触摸时也得不到焦点
        }

        initDateTime();
    }


    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second=calendar.get(Calendar.SECOND);
        strdate.append(hour).append(":").append(minute).append(":").append(second).append(" ")
                .append(day).append("/").append(String.valueOf(month)).append("/").append(String.valueOf(year));
        et_MoodTagDate.setText(strdate);
    }



    public void onClickSetDate(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (strdate.length() > 0) {
                    strdate.delete(0, strdate.length());
                }
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);
                second=calendar.get(Calendar.SECOND);
                strdate.append(hour).append(":").append(minute).append(":").append(second).append(" ")
                        .append(day).append("/").append(String.valueOf(month)).append("/").append(String.valueOf(year));
                et_MoodTagDate.setText(strdate);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.layout_dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("Set Date");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear+1;
        this.day = dayOfMonth;
    }


    public void onClickCancel(View view) {
        android.support.v7.app.AlertDialog.Builder Dlg=new android.support.v7.app.AlertDialog.Builder(this);
        Dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), MoodTagActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Dlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dlg.setMessage("\nAre you sure to cancel editing?");
        Dlg.setTitle("Confirm");
        Dlg.setIcon(R.mipmap.ic_launcher);
        Dlg.show();

    }


    public void onClickSave(View view) {
        String strdate=this.strdate.toString();
        String strText=et_MoodTagText.getText().toString().trim();
        String username=User.getUserName();

        if (!strText.isEmpty()) {
            saveButton.setClickable(false);

            // Display the progress Dialog
            progressDialog.setMessage("Add Mood Tag ...");
            if (!progressDialog.isShowing())
                progressDialog.show();

            AddMoodTagRequest(username,strdate,strText);

        }
        else {
            showToast("Mood Tag can not be empty.", Toast.LENGTH_LONG);
        }

    }


    public void AddMoodTagRequest(final String username, final String strdate, final String strText) {
        //请求地址
        final String url = new Config().getAddMoodTagUrl();
        final String tag = "AddMoodTag";

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
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                //做自己的登录成功操作，如页面跳转
                                Intent intent = new Intent(getApplicationContext(), MoodTagActivity.class);
                                startActivity(intent);
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                finish();

                            } else {
                                //做自己的登录失败操作，如Toast提示
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Save Mood Tag failure, please try again later!", Toast.LENGTH_LONG);
                                saveButton.setClickable(true);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Save Mood Tag failure, please try again later!", Toast.LENGTH_SHORT);
                            saveButton.setClickable(true);
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
                saveButton.setClickable(true);
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", username);
                params.put("strDate", strdate);  //给请求中填充要附带的数据
                params.put("strText", strText);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }


    //show Toast
    public void showToast(String str, int showTime)
    {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //set the display location
        //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        //v.setTextColor(getResources().getColor(R.color.messageTextClor));
        toast.show();
    }

}
