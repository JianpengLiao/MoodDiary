package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PersonInfoActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
    }

    public void onClickBack(View view) {
        onBackPressed();
    }


    public void onClickChangeHeadPortrait(View view) {
        Intent intent = new Intent(getApplicationContext(), Change_headportrait_Activity.class);
        startActivity(intent);
        finish();
    }
}
