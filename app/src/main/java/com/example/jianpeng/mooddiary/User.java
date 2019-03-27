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


    public static int getHeadPictureID(){
        int id=1;
        switch (n_headportrait) {
            case 1:
                id=R.drawable.me_avatar;
                break;
            case 2:
                id=R.drawable.me_avatar_2;
                break;
            case 3:
                id=R.drawable.me_avatar_3;
                break;
            case 4:
                id=R.drawable.me_avatar_4;
                break;
            case 5:
                id=R.drawable.me_avatar_5;
                break;
            case 6:
                id=R.drawable.me_avatar_6;
                break;
            case 7:
                id=R.drawable.me_avatar_7;
                break;
            case 8:
                id=R.drawable.me_avatar_8;
                break;
            case 9:
                id=R.drawable.me_avatar_9;
                break;
        }
        return id;
    }
}
