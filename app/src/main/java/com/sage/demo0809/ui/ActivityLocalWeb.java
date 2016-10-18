package com.sage.demo0809.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.bean.User;

import java.util.ArrayList;

/**
 * Created by Sage on 2016/10/9.
 */

public class ActivityLocalWeb extends ActivityBase {


    WebView mWebview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);

        mWebview= (WebView) findViewById(R.id.wv);
        String fileUrl = "file:///android_asset/++tel.html";
//        mWebview.getSettings().setDefaultTextEncodingName("utf-8");

        ArrayList<User> lists=new ArrayList<>();
        for(int i=0;i<5;i++){
            User user=new User();
            user.name="ddd"+i;
            user.age=i+1;
            lists.add(user);
        }

        MyLog.i("==========="+new Gson().toJson(lists));
        WebSettings mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
//        mWebSettings.setSupportZoom(true);
//        mWebSettings.setBuiltInZoomControls(true);
        //mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        //启用数据库
        mWebSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        mWebSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        mWebSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);

        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.i("======"+url);

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebview.loadUrl(fileUrl);
    }
}
