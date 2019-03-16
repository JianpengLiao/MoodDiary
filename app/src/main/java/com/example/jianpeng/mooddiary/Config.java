package com.example.jianpeng.mooddiary;

public class Config {

    //Login
    private String url_Login = "http://120.79.26.213:8080/MoodDiaryWebAPP/LoginServlet";

    //Register
    private String url_Register = "http://120.79.26.213:8080/MoodDiaryWebAPP/RegisterServlet";

    public String getLoginUrl() {
        return url_Login;
    }

    public String getRegisterUrl() {
        return url_Register;
    }
}
