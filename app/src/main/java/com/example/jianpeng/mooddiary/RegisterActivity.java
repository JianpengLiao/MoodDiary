package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends BaseCompatActivity {

    private EditText inputUserID, inputPassword, inputCPassword;
    private Button registerButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputUserID = findViewById(R.id.et_Register_UserID);
        inputPassword = findViewById(R.id.et_Register_Password);
        inputCPassword=findViewById(R.id.et_Register_CPassword);
        registerButton = findViewById(R.id.btn_Register_Register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }


    public void onClickLinktoLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void onClickRegister(View view) {
        String userid = inputUserID.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String cpassword=inputCPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!userid.isEmpty() && !password.isEmpty() && !cpassword.isEmpty()) {

            if(password.equals(cpassword)){
                // Avoid multiple clicks on the button
                registerButton.setClickable(false);

                // Display the progress Dialog
                progressDialog.setMessage("Registering ...");
                if (!progressDialog.isShowing())
                    progressDialog.show();

                RegisterRequest(userid,password);
             }
             else {
                showToast("The Password and Confirm password are different, please check them!",Toast.LENGTH_LONG);
                registerButton.setClickable(true);
            }
        }
        else {
            // Prompt user to enter credentials
            showToast("Enter your register information.", Toast.LENGTH_LONG);
        }
    }



    public void RegisterRequest(final String userid, final String password) {
        //请求地址
        final String url = new Config().getRegisterUrl();
        final String tag = "Register";

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
                                //成功
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Register success, please login your account!", Toast.LENGTH_LONG);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                //失败
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                showToast("Your account already exists, register failure", Toast.LENGTH_LONG);
                                registerButton.setClickable(true);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Register failure, please try again later!", Toast.LENGTH_SHORT);
                            registerButton.setClickable(true);
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //响应错误
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                showToast("No network connection, please try again later!", Toast.LENGTH_LONG);
                registerButton.setClickable(true);
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", userid);  //给请求中填充要附带的数据
                params.put("Password", password);
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
