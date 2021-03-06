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

    private String url_GetTravelDiary="http://120.79.26.213:8080/MoodDiaryWebAPP/GetTravelDiaryServlet";

    private String url_AddTravelDiaryPicture="http://120.79.26.213:8080/MoodDiaryWebAPP/AddTravelDiaryPictureServlet";

    private String url_AddCardDiary="http://120.79.26.213:8080/MoodDiaryWebAPP/AddCardDiaryServlet";

    private String url_GetCardDiary="http://120.79.26.213:8080/MoodDiaryWebAPP/GetCardDiaryServlet";

    private String url_SaveHeadPortrait="http://120.79.26.213:8080/MoodDiaryWebAPP/SaveHeadPortraitServlet";


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

    public String getGetTravelDiaryUrl(){
        return url_GetTravelDiary;
    }

    public String getAddCardDiaryUrl(){
        return url_AddCardDiary;
    }

    public String getGetCardDiaryUrl(){
        return url_GetCardDiary;
    }

    public String getSaveHeadPortraitUrl(){
        return url_SaveHeadPortrait;
    }

    public String getUrl_AddTravelDiaryPicture(){
        return url_AddTravelDiaryPicture;
    }
}
