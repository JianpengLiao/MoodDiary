package com.example.jianpeng.mooddiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;




public class News_ArrayAdapter extends ArrayAdapter<News> {

    private int resourceId;
    Context context;
    private News_ViewHolder viewHolder;


    public News_ArrayAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new News_ViewHolder();
            viewHolder.TextTile = view.findViewById(R.id.tv_NewsTitle);
            viewHolder.ImgPic=view.findViewById(R.id.iv_News);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (News_ViewHolder) view.getTag();
        }

        viewHolder.TextTile.setText(news.getTitle());
        String picName=news.getPicName();
        String picPath=BitmapUtil.getDeafaultFilePath()+picName;
        final Bitmap bitmap =  BitmapFactory.decodeFile(picPath);
        if(bitmap!=null) {
            Bitmap bmp = ImageUtils.zoomImage(bitmap, 100, 100);
            //final Bitmap dexbitmap = ThumbnailUtils.extractThumbnail(bitmap,100,100);
            viewHolder.ImgPic.setImageBitmap(bmp);
        }
        return view;
    }




}

