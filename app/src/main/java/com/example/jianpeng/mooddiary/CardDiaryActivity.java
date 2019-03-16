package com.example.jianpeng.mooddiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CardDiaryActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_diary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
