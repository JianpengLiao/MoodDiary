package com.example.jianpeng.mooddiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Travel_Diary_ViewHolder viewHolder;

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
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (Travel_Diary_ViewHolder) view.getTag();
        }

        viewHolder.TextView_Time.setText(traveldiary.getstrTime());
        initContent(traveldiary.getText());
        return view;

    }


    private void initContent(String input){

        String subStr="";
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);

        int i=0;
        while(m.find()){
            int start = m.start();
            int end = m.end();
            subStr=subStr+input.substring(i,start);
            i=end;
        }
        subStr=subStr+input.substring(i,input.length());
        viewHolder.TextView_Text.setText(subStr);
    }


}
