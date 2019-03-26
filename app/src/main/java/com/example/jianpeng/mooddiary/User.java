package com.example.jianpeng.mooddiary;

public class User {
    private static String username;
    private static int n_headportrait;
    private static String email=null;
    private static String phone=null;

    public static String getUserName(){
        return username;
    }

    public static void setUsername(String str){
        username=str;
    }

    public static int getN_headportrait(){
        return n_headportrait;
    }

    public static void setN_headportrait(int n){
        n_headportrait=n;
    }

    public static String getEmail(){
        return email;
    }

    public static void setEmail(String e){
        email=e;
    }

    public static String getPhone(){
        return phone;
    }

    public static void setPhone(String p){
        phone=p;
    }
}
