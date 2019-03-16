package com.example.jianpeng.mooddiary;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ActivityManager{

    private ActivityManager() {}
    private static List<Activity> actList = new ArrayList<>();

    public static void addActivity(Activity act) {
        actList.add(act);
    }


    public static void removeActivity(Activity act) {
        actList.remove(act);
    }


    public static void finishAll() {
        for (Activity act : actList) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }

    }



}
