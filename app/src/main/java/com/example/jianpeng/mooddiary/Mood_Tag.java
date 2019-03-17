package com.example.jianpeng.mooddiary;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Mood_Tag {
    private Date time;
    private String text;

    public Mood_Tag(){}
    public Mood_Tag(Date t, String str){
        time=t;
        text=str;
    }

    public void setTime(Date t){
        time=t;
    }
    public void setText(String str){
        text=str;
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
}
