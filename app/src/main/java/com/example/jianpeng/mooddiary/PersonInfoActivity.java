package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfoActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        TextView textView=findViewById(R.id.tv_UserName);
        textView.setText(User.getUserName());
        ImageView ivhead=findViewById(R.id.iv_PHead);
        int headid=User.getHeadPictureID();
        ivhead.setImageResource(headid);

        if(!User.getEmail().equals("null")) {
            TextView tvEmail=findViewById(R.id.tv_Email);
            tvEmail.setText(User.getEmail());
        }
        if(!User.getPhone().equals("null")){
            TextView tvPhone=findViewById(R.id.tv_Phone);
            tvPhone.setText(User.getPhone());
        }
    }

    public void onClickBack(View view) {
        MeFragment.instance.changeHead();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        MeFragment.instance.changeHead();
        super.onBackPressed();
    }

    public void onClickChangeHeadPortrait(View view) {
        Intent intent = new Intent(getApplicationContext(), Change_headportrait_Activity.class);
        startActivity(intent);
        finish();
    }
}
