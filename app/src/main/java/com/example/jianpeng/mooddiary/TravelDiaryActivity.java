package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class TravelDiaryActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);

        showToast("You don't have any MoodTag, just add it.",Toast.LENGTH_LONG);
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
        //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        //v.setTextColor(getResources().getColor(R.color.messageTextClor));
        toast.show();
    }
}
