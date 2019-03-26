package com.example.jianpeng.mooddiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travel_Diary_Show_Activity extends BaseCompatActivity {

    private EditText et_Date, et_Text;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel__diary__show);

        et_Date=findViewById(R.id.ed_TravelDiary_Show_Date);
        et_Text=findViewById(R.id.et_TravelDiary_Show_Text);
        context=getApplicationContext();

        if (et_Date != null) {
            et_Date.setCursorVisible(false);      //设置输入框中的光标不可见
            et_Date.setFocusable(false);
            et_Date.setFocusableInTouchMode(false);     //触摸时也得不到焦点
        }
        if (et_Text != null) {
            et_Text.setCursorVisible(false);      //设置输入框中的光标不可见
            et_Text.setFocusable(false);
            et_Text.setFocusableInTouchMode(false);     //触摸时也得不到焦点
        }

        initShow();
    }


    private void initShow(){
        int i=Travel_Diary_List.getOnClickItem();
        Travel_Diary temtraceldiary=Travel_Diary_List.getTravelDiary(i);

        String strDate=temtraceldiary.getstrTime();
        String strText=temtraceldiary.getText();
        initText(strDate,strText);
    }


    private void initText(String strDate,String input) {
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);

        SpannableString spannable = new SpannableString(input);
        while (m.find()) {
            //这里s保存的是整个式子，即<img src="xxx"/>，start和end保存的是下标
            String s = m.group();
            int start = m.start();
            int end = m.end();
            //path是去掉<img src=""/>的中间的图片路径
            String name = s.replaceAll("\\<img src=\"|\"\\/>", "").trim();
            String path=BitmapUtil.getDeafaultFilePath()+name;

            //利用spannableString和ImageSpan来替换掉这些图片
            int width = ScreenUtils.getScreenWidth(context);
            int height = ScreenUtils.getScreenHeight(context);

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                String onlyText;
                if(bitmap==null) {
                    onlyText = Travel_Diary_ArrayAdapter.initContent(input);
                    et_Text.setText(onlyText);
                }
                else {
                    bitmap = ImageUtils.zoomImage(bitmap, width * 0.88, bitmap.getHeight() / (bitmap.getWidth() / (width * 0.88)));
                    ImageSpan imageSpan = new ImageSpan(context, bitmap);
                    spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    et_Text.setText(spannable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        et_Date.setText(strDate);
    }


    public void onClickCancel(View view) {
        onBackPressed();
    }
}
