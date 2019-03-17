package com.example.jianpeng.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;



public class Work_Record_ArrayAdapter extends ArrayAdapter<Work_Record> {

    private int resourceId;

    public Work_Record_ArrayAdapter(Context context, int resource, List<Work_Record> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Work_Record workrecord = getItem(position);
        View view;
        Work_Record_ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new Work_Record_ViewHolder();
            viewHolder.TextView_Time = view.findViewById(R.id.tv_WorkRecord_Time);
            viewHolder.TextView_Text = view.findViewById(R.id.tv_WorkRecord_Text);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (Work_Record_ViewHolder) view.getTag();
        }

        viewHolder.TextView_Time.setText(workrecord.getstrTime());
        viewHolder.TextView_Text.setText(workrecord.getText());
        return view;

    }

}
