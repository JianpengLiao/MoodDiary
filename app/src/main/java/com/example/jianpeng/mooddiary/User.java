package com.example.jianpeng.mooddiary;

public class User {
    private static String username;

    public static String getUserName(){
        return username;
    }

    public static void setUsername(String str){
        username=str;
    }

}
