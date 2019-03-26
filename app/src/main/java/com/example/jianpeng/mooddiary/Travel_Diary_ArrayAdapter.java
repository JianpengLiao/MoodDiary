package com.example.jianpeng.mooddiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travel_Diary_ArrayAdapter extends ArrayAdapter<Travel_Diary> {

    private int resourceId;
    Context context;
    private Travel_Diary_ViewHolder viewHolder;
    public static String name;

    public Travel_Diary_ArrayAdapter(Context context, int resource, List<Travel_Diary> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Travel_Diary traveldiary = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new Travel_Diary_ViewHolder();
            viewHolder.TextView_Time = view.findViewById(R.id.tv_TravelDiary_Time);
            viewHolder.TextView_Text = view.findViewById(R.id.tv_TravelDiary_Text);
            viewHolder.ImageView_Image=view.findViewById(R.id.iv_TravelDiary);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (Travel_Diary_ViewHolder) view.getTag();
        }

        viewHolder.TextView_Time.setText(traveldiary.getstrTime());
        String subStr=initContent(traveldiary.getText());
        viewHolder.TextView_Text.setText(subStr);
        String path=BitmapUtil.getDeafaultFilePath()+name;
        final Bitmap bitmap =  BitmapFactory.decodeFile(path);
        if(bitmap!=null) {
            Bitmap bmp = ImageUtils.zoomImage(bitmap, 100, 100);
            //final Bitmap dexbitmap = ThumbnailUtils.extractThumbnail(bitmap,100,100);
            viewHolder.ImageView_Image.setImageBitmap(bmp);
        }
        return view;
    }


    public static String initContent(String input){

        int flag=0;
        String subStr="";
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);


        int i=0;
        while(m.find()){
            if(flag==0) {
                String s = m.group();
                name = s.replaceAll("\\<img src=\"|\"\\/>", "").trim();
                flag=1;
            }
            int start = m.start();
            int end = m.end();
            subStr=subStr+input.substring(i,start);
            i=end;
        }
        subStr=subStr+input.substring(i,input.length());
        return subStr;
    }


}
