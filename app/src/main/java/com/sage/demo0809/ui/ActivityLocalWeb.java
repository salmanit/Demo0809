package com.sage.demo0809.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setLoadsImagesAutomatically(true);
//        //启用数据库
//        mWebSettings.setDatabaseEnabled(true);
//        //设置定位的数据库路径
//        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        mWebSettings.setGeolocationDatabasePath(dir);
//        //启用地理定位
//        mWebSettings.setGeolocationEnabled(true);
//        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);

        mWebSettings.setSupportMultipleWindows(false);
        mWebview.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                MyLog.i("shouldOverrideUrlLoading11======"+url);
//                    view.loadUrl(url);
//                return true;
//            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.i("shouldOverrideUrlLoading11======"+url);
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    //给予提示，没有对应的程序,返回ture,点击没任何反应，如果false，则会显示一个无法打开的页面。。
                    return true;
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                MyLog.i("onLoadResource22======"+url);
                try {
                    MyLog.i("+++++++++++++"+url.substring(url.indexOf("changeTitle@")));
                } catch (Exception e) {

                }
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                MyLog.i("shouldOverrideKeyEvent33======"+event.toString());
                return super.shouldOverrideKeyEvent(view, event);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                try {
                    MyLog.i("shouldInterceptRequest44======"+request.getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                MyLog.i("shouldInterceptRequest55======"+url);
                return super.shouldInterceptRequest(view, url);
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                MyLog.i("onJsConfirm======"+url);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                MyLog.i("onJsAlert======"+url);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                MyLog.i("onJsPrompt======"+url);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                MyLog.i("onJsBeforeUnload======"+url);
                return super.onJsBeforeUnload(view, url, message, result);

            }
        });

        String dongwang="http://saas.dongsport.com/sso/login-GA.jsp";
//        mWebview.loadUrl(fileUrl);
//        mWebview.loadUrl("http://apps.daishutijian.com/wechat/org/package/");
//        mWebview.loadUrl("file:///android_asset/test.html");
        mWebview.loadUrl("http://10.0.3.132:8082/thanks/iframe.html");
    }

    @Override
    public void onBackPressed() {
        if(mWebview.canGoBack()){
            mWebview.goBack();
        }else
        super.onBackPressed();
    }
}
