package com.example.jianpeng.mooddiary;

import android.graphics.Bitmap;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Card_Diary {

    private Date time;
    private String text;
    Bitmap bmp;
    String bmpName;
    int numberOfBmp;


    public Card_Diary(){
        bmp=null;
        bmpName=null;
        numberOfBmp=0;
    }

    public Card_Diary(Date t, String str){
        time=t;
        text=str;
        bmp=null;
        bmpName=null;
        numberOfBmp=0;
    }

    public void setTime(Date t){
        time=t;
    }
    public void setText(String str){
        text=str;
    }


    public void setstrTime(String strt){
        SimpleDateFormat df = new SimpleDateFormat ("HH:mm:ss dd/MM/yyyy");
        try {
            time = df.parse(strt);
        }
        catch (ParseException e){
            Log.e("Failure", e.getMessage(), e);
        }
    }

    public Date getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getstrTime(){
        SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy" );
        String strTime = sdf.format(time);
        return strTime;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public void setBitmap(Bitmap b) {
        bmp = b;
        numberOfBmp=1;
    }

    public String getBitmapName() {
        return bmpName;
    }

    public void setBitmapName(String bn) {
        bmpName = bn;
    }

    public int getNumberOfBmp(){
        return numberOfBmp;
    }
}
