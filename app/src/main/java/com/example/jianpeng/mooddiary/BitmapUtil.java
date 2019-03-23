package com.example.jianpeng.mooddiary;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class BitmapUtil {

    private static final String DEAFAULT_FILE_PATH = getSdCardPath() + "//MoodDiary//Picture//";

    private BitmapUtil() {

    }


    /**

     * 保存图片到本地 第一个参数是图片名称 第二个参数为需要保存的bitmap

     * */
    public static void saveImgToDisk(String imgName, Bitmap bitmap) {
        makeRootDirectory(DEAFAULT_FILE_PATH);
        File file = new File(DEAFAULT_FILE_PATH +imgName);
        if(file == null) {
            return;
        }
        if(isFileExists(file.getPath())) {
            return;
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            String path = file.getPath();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**

     * 判断文件路径是否已经存在

     * @param filePath 文件路径

     * */
    private static boolean isFileExists(String filePath) {
        try {
            File file = new File( filePath );
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**

     * 返回SD卡跟目录 <br>

     *

     * @return SD卡跟目录

     */
    public static String getSdCardPath() {
        File sdDir ;
        boolean sdCardExist = isSdCardExist(); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        } else {
            return null;
        }
    }

    /**

     * 判断SD卡是否存在 <br>

     *

     * @return SD卡存在返回true，否则返回false

     */

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }

    public static String getDeafaultFilePath(){
        return DEAFAULT_FILE_PATH;
    }


    /**
     　　* 将bitmap转换成base64字符串
     　　*
     　　* @param bitmap
     　　* @return base64 字符串
     　　*/
    public static String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }


    /**
     　　* 将base64转换成bitmap图片
     　　*
     　　* @param string base64字符串
     　　* @return bitmap
     　　*/
    public Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
