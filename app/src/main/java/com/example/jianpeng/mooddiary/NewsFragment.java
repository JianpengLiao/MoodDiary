package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment  {



    public ArrayList<News> newsList=new ArrayList<News>();
    ListView listView;
    News_ArrayAdapter newsArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsList=AllSwapDataList.getNewsList();

        newsArrayAdapter =
                new News_ArrayAdapter(
                        getActivity(),
                        R.layout.layout_news_item, // the layout for each item in the list
                        newsList); // the arrayList to feed the arrayAdapter

        listView = getActivity().findViewById(R.id.listView_News);
        listView.setAdapter(newsArrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News tempNews=newsList.get(i);
                String tempUrl=tempNews.getUrl();
                Intent intent = new Intent(getActivity(), News_Show_Activity.class);
                intent.putExtra("Url", tempUrl);
                startActivity(intent);
            }
        });




    }










}
