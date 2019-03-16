package com.example.jianpeng.mooddiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class BaseCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建活动时，将其加入管理器中
        ActivityManager.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

}
