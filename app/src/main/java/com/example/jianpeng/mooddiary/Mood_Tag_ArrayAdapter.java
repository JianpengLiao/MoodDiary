package com.example.jianpeng.mooddiary;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;



public class Mood_Tag_ArrayAdapter extends ArrayAdapter<Mood_Tag> {

    private int resourceId;

    public Mood_Tag_ArrayAdapter(Context context, int resource, List<Mood_Tag> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mood_Tag moodtag = getItem(position);
        View view;
        Mood_Tag_ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new Mood_Tag_ViewHolder();
            viewHolder.TextView_Time = view.findViewById(R.id.tv_MoodTag_Time);
            viewHolder.TextView_Text = view.findViewById(R.id.tv_MoodTag_Text);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (Mood_Tag_ViewHolder) view.getTag();
        }

        viewHolder.TextView_Time.setText(moodtag.getstrTime());
        viewHolder.TextView_Text.setText(moodtag.getText());
        return view;

    }


}
