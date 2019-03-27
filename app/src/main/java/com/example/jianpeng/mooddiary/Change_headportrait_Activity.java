package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class Change_headportrait_Activity extends BaseCompatActivity {

    private int n_headportrait;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_headportrait);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        n_headportrait=1;
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(getApplicationContext(), PersonInfoActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick1(View view) {
        n_headportrait=1;
        saveHeadportrait();
    }

    public void onClick2(View view) {
        n_headportrait=2;
        saveHeadportrait();
    }

    public void onClick3(View view) {
        n_headportrait=3;
        saveHeadportrait();
    }

    public void onClick4(View view) {
        n_headportrait=4;
        saveHeadportrait();
    }

    public void onClick5(View view) {
        n_headportrait=5;
        saveHeadportrait();
    }

    public void onClick6(View view) {
        n_headportrait=6;
        saveHeadportrait();
    }

    public void onClick7(View view) {
        n_headportrait=7;
        saveHeadportrait();
    }

    public void onClick8(View view) {
        n_headportrait=8;
        saveHeadportrait();
    }

    public void onClick9(View view) {
        n_headportrait=9;
        saveHeadportrait();
    }


    private void saveHeadportrait(){
        android.support.v7.app.AlertDialog.Builder Dlg=new android.support.v7.app.AlertDialog.Builder(this);
        Dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SaveHeadportraitRequest(User.getUserName(),n_headportrait);
            }
        });
        Dlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dlg.setMessage("\nAre you sure to select this head portrait?");
        Dlg.setTitle("Confirm");
        Dlg.setIcon(R.mipmap.ic_launcher);
        Dlg.show();

    }


    public void SaveHeadportraitRequest(String Username,int n_headportrait) {
        final String username=Username;
        final int nheadportrait=n_headportrait;
        //请求地址
        final String url = new Config().getSaveHeadPortraitUrl();
        final String tag = "getSaveHeadPortrait";

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
                                User.setN_headportrait(nheadportrait);
                                Intent intent = new Intent(getApplicationContext(), PersonInfoActivity.class);
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
                                showToast("Save Head Portrait failure, please try again later!", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Save Head Portrait failure, please try again later!", Toast.LENGTH_SHORT);
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
                params.put("nHeadPortrait", String.valueOf(nheadportrait));  //给请求中填充要附带的数据
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
