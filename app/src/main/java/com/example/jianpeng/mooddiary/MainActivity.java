package com.example.jianpeng.mooddiary;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView bottomNavigationView;
    private  HomeFragment home_fragment;
    private CalendarFragment calendar_fragment;
    private NewsFragment news_fragment;
    private MeFragment me_fragment;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        new DownloadPicture().execute();
    }

    private void initFragment() {
        home_fragment = new HomeFragment();
        calendar_fragment = new CalendarFragment();
        news_fragment = new NewsFragment();
        me_fragment=new MeFragment();
        fragments = new Fragment[]{home_fragment,calendar_fragment,news_fragment, me_fragment};
        lastfragment=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,home_fragment).show(home_fragment).commit();
        bottomNavigationView=findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;
                    }
                    return true;
                }
                case R.id.navigation_calendar:
                {
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;
                    }
                    return true;
                }
                case R.id.navigation_news:
                {
                    if(lastfragment!=2)
                    {
                        switchFragment(lastfragment,2);
                        lastfragment=2;
                    }
                    return true;
                }
                case R.id.navigation_me:
                {
                    if(lastfragment!=3)
                    {
                        switchFragment(lastfragment,3);
                        lastfragment=3;
                    }
                    return true;
                }
            }
            return false;
        }
    };


    //切换Fragment
    private void switchFragment(int lastfragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(!fragments[index].isAdded())
        {
            transaction.add(R.id.mainview,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }



    public class DownloadPicture extends AsyncTask<String,Void,String> {

        String path=BitmapUtil.getDeafaultFilePath();
        String checkDownload;
        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> tempCDList= AllSwapDataList.getCardDiaryPictureList();
            ArrayList<String> tempTDList= AllSwapDataList.getTravelDiaryPictureList();
            ArrayList<News> tempNewsList=AllSwapDataList.getNewsList();

            for (int i = 0; i < tempCDList.size(); i++) {
                String name = tempCDList.get(i);
                String Bmppath = path + name;
                File f = new File(Bmppath);
                if (!f.exists()) {
                    checkDownload = DownloadUtils.downloadFile(name);
                }
            }
            for (int j = 0; j < tempTDList.size(); j++) {
                String name = tempTDList.get(j);
                String Bmppath = path + name;
                File f = new File(Bmppath);
                if (!f.exists()) {
                    checkDownload = DownloadUtils.downloadFile(name);
                }
            }
            for (int k = 0; k < tempNewsList.size(); k++) {
                News tempNews = tempNewsList.get(k);
                String name=tempNews.getPicName();
                if(name==null)
                    continue;
                String Bmppath = path + name;
                File f = new File(Bmppath);
                if (!f.exists()) {
                    checkDownload = DownloadUtils.downloadFile(name);
                }
            }
            return checkDownload;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }






}
