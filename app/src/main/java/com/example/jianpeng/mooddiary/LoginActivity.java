package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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


public class LoginActivity extends BaseCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText inputUserID, inputPassword;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUserID = findViewById(R.id.et_UserID);
        inputPassword = findViewById(R.id.et_Password);
        loginButton = findViewById(R.id.btn_Login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox) findViewById(R.id.checkBox);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            inputUserID.setText(account);
            inputPassword.setText(password);
            rememberPass.setChecked(true);
        }
    }

    public void onClickLogin(View view) {
        String userid = inputUserID.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!userid.isEmpty() && !password.isEmpty()) {

            // Avoid multiple clicks on the button
            loginButton.setClickable(false);

            //Todo : ensure the user has Internet connection

            // Display the progress Dialog
            progressDialog.setMessage("Logging in ...");
            if (!progressDialog.isShowing())
                progressDialog.show();

            //Todo: need to check weather the user has Internet before attempting checking the data
            // Start fetching the data from the Internet
            LoginRequest(userid,password);
            editor = pref.edit();
            if (rememberPass.isChecked()) { // 检查复选框是否被选中
                editor.putBoolean("remember_password", true);
                editor.putString("account", userid);
                editor.putString("password", password);
            }
            else {
                editor.clear();
            }
            editor.commit();

        }
        else {
            // Prompt user to enter credentials
            showToast("Enter your credentials.", Toast.LENGTH_LONG);
        }

    }


    public void onClickLinktoRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }



    public void LoginRequest(final String accountNumber, final String password) {
        //请求地址
        final String url = new Config().getLoginUrl();
        final String tag = "Login";

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
                                User.setUsername(accountNumber);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                                showToast("Incorrect UserID or Password,please try again", Toast.LENGTH_LONG);
                                loginButton.setClickable(true);
                            }
                        } catch (JSONException e) {
                            //请求异常
                            if(progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            showToast("Login failure, please try again later!", Toast.LENGTH_SHORT);
                            loginButton.setClickable(true);
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
                loginButton.setClickable(true);
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //给请求中填充要附带的数据
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
