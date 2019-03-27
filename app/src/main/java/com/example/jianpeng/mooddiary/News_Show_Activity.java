package com.example.jianpeng.mooddiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class News_Show_Activity extends BaseCompatActivity {

    WebView webview;
    private SlowlyProgressBar slowlyProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__show);

        //获取上一个Activity传过来的值
        Intent intent = getIntent();
        String Url = intent.getStringExtra("Url");


        slowlyProgressBar = new SlowlyProgressBar(findViewById(R.id.p),
                getWindowManager().getDefaultDisplay().getWidth())
                .setViewHeight(3);


        webview = (WebView) findViewById(R.id.WebView);
        webview.getSettings().setJavaScriptEnabled(true);// 启用js 不启动的话网页是js的打不开
        //该设置使打开新链接时还在同一个WebView中打开，不加该设置，打开新链接时将会在android自带浏览器中打开网页。
        webview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                slowlyProgressBar.setProgress(newProgress);


            }

        });


        webview.loadUrl(Url); // 进入相应的界面



    }

    public void onClickCancel(View view) {
        onBackPressed();
    }
}
