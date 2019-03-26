package com.example.jianpeng.mooddiary;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Travel_Diary {

    private Date time;
    private String text;
    private int numberOfBmp;
    public ArrayList<Bitmap> BmpArray;
    public ArrayList<String> BmpNameArray;


    public Travel_Diary(){
        BmpArray=new ArrayList<Bitmap>();
        BmpNameArray=new ArrayList<String>();
        numberOfBmp=0;
    }
    public Travel_Diary(Date t, String str){
        time=t;
        text=str;
        BmpArray=new ArrayList<Bitmap>();
        BmpNameArray=new ArrayList<String>();
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

    public int getNumberOfBmp(){
        return numberOfBmp;
    }

    public void setNumberOfBmp(int n){
        numberOfBmp=n;
    }

    public ArrayList<Bitmap> getBmpArray() {
        return BmpArray;
    }

    public void setBmpArray(ArrayList<Bitmap> bmpArray) {
        BmpArray = bmpArray;
    }

    public ArrayList<String> getBmpNameArray() {
        return BmpNameArray;
    }

    public void setBmpNameArry(ArrayList<String> bmpnameArray) {
        BmpNameArray = bmpnameArray;
    }
}
