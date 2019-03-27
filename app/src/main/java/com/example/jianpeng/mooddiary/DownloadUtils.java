package com.example.jianpeng.mooddiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";



    public static String downloadFile(String name) {

        String RequestURL = "http://120.79.26.213:8080/MoodDiaryWebAPP/upload/photo/"+name;

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("GET"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码

            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            if (res == 200) {
                //通过图片路径的输入流，可以直接将输入流的信息变为一张图片
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                BitmapUtil.saveImgToDisk(name,bitmap);

                System.out.println(SUCCESS+"  "+name);
                return SUCCESS;

            }
            else {
                System.out.println(FAILURE+"  "+name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException",e.toString());
        }
        return FAILURE;

    }
}
