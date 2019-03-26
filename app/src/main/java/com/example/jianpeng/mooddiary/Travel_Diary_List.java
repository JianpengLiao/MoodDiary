package com.example.jianpeng.mooddiary;

import java.util.ArrayList;

public class Travel_Diary_List {

    private static ArrayList<Travel_Diary> TravelDiaryList=new ArrayList<Travel_Diary>();
    private static int onClickItem=0;

    private static ArrayList<String> TravelDiaryPictureNameList=new ArrayList<String>();

    Travel_Diary_List(){
        TravelDiaryList=new ArrayList<Travel_Diary>();
        TravelDiaryPictureNameList=new ArrayList<String>();
        onClickItem=0;
    }

    public static void addTravelDiary(Travel_Diary travel_diary){
        TravelDiaryList.add(travel_diary);
    }

    public static void addTravelDiaryPictureName(String travel_diaryPic){
        TravelDiaryPictureNameList.add(travel_diaryPic);
    }

    public static Travel_Diary getTravelDiary(int i){
        return TravelDiaryList.get(i);
    }

    public static void setonClickItem(int i){
        onClickItem=i;
    }
    public static int getOnClickItem(){
        return onClickItem;
    }

    public static void clearTravelDiary(){
        TravelDiaryList.clear();
    }

    public static ArrayList<String> getTravelDiaryPictureNameList(){
        return TravelDiaryPictureNameList;
    }

}
