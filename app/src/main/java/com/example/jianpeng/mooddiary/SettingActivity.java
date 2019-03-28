package com.example.jianpeng.mooddiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }


    public void onClickLogOut(View view) {

        AlertDialog.Builder Dlg=new AlertDialog.Builder(this);
        Dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                ActivityManager.finishAll();
            }
        });
        Dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dlg.setMessage("\nAre you sure to log out?");
        Dlg.setTitle("Confirm");
        Dlg.setIcon(R.mipmap.ic_launcher);
        Dlg.show();
    }

    public void onClickCancel(View view) {
        onBackPressed();
    }

    public void onClickHelp(View view) {
        showToast("This feature will be implemented in version 2.0, so stay tuned", Toast.LENGTH_LONG);
    }

    public void onClickAbout(View view) {
        showToast("This feature will be implemented in version 2.0, so stay tuned", Toast.LENGTH_LONG);
    }


    //show Toast
    public void showToast(String str, int showTime)
    {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //set the display location
        toast.show();
    }
}

