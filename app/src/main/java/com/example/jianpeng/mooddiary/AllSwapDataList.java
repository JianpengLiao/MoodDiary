package com.example.jianpeng.mooddiary;

import java.util.ArrayList;

public class AllSwapDataList {
    private static ArrayList<String> CardDiaryPictureList=new ArrayList<String>();
    private static ArrayList<String> TravelDiaryPictureList=new ArrayList<String>();
    private static ArrayList<News> NewsList=new ArrayList<News>();

    public static void setCardDiaryPictureList(ArrayList<String> L){
        CardDiaryPictureList=L;
    }
    public static void setTravelDiaryPictureList(ArrayList<String> L){
        TravelDiaryPictureList=L;
    }
    public static void setNewsList(ArrayList<News> L){
        NewsList=L;
    }

    public static ArrayList<String> getCardDiaryPictureList(){
        return CardDiaryPictureList;
    }
    public static ArrayList<String> getTravelDiaryPictureList(){
        return TravelDiaryPictureList;
    }
    public static ArrayList<News> getNewsList(){
        return NewsList;
    }
}
