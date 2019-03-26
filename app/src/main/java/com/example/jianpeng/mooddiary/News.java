package com.example.jianpeng.mooddiary;

public class News {
    String title;
    String url;
    String picName;

    public  News(){

    }

    public  News(String t, String u, String pN){
        title=t;
        url=u;
        picName=pN;
    }

    public String getTitle(){
        return title;
    }
    public String getUrl(){
        return url;
    }
    public String getPicName(){
        return picName;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public void setPicName(String pn){
        this.picName=pn;
    }
}
