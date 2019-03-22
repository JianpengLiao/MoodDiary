package com.example.jianpeng.mooddiary;

public class Config {

    //Login
    private String url_Login = "http://120.79.26.213:8080/MoodDiaryWebAPP/LoginServlet";

    //Register
    private String url_Register = "http://120.79.26.213:8080/MoodDiaryWebAPP/RegisterServlet";

    //AddMoodTag
    private String url_AddMoodTag="http://120.79.26.213:8080/MoodDiaryWebAPP/AddMoodTagServlet";

    //GetMoodTag
    private String url_GetMoodTag="http://120.79.26.213:8080/MoodDiaryWebAPP/GetMoodTagServlet";

    //AddWorkRecord
    private String url_AddWorkRecord="http://120.79.26.213:8080/MoodDiaryWebAPP/AddWorkRecordServlet";

    //GetWorkRecord
    private String url_GetWorkRecord="http://120.79.26.213:8080/MoodDiaryWebAPP/GetWorkRecordServlet";

    //AddTravelDiary
    private String url_AddTravelDiary="http://120.79.26.213:8080/MoodDiaryWebAPP/AddTravelDiaryServlet";


    public String getLoginUrl() {
        return url_Login;
    }

    public String getRegisterUrl() {
        return url_Register;
    }

    public String getAddMoodTagUrl(){
        return url_AddMoodTag;
    }

    public String getGetMoodTagUrl(){
        return url_GetMoodTag;
    }

    public String getAddWorkRecordUrl(){
        return url_AddWorkRecord;
    }

    public String getGetWorkRecordUrl(){
        return url_GetWorkRecord;
    }

    public String getAddTravelDiaryUrl(){
        return url_AddTravelDiary;
    }

}
