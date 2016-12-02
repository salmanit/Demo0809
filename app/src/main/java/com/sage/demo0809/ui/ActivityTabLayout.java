package com.sage.demo0809.ui;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sage.demo0809.BuildConfig;
import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.widget.WebViewVideoFull;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

/**
 * Created by Sage on 2016/11/24.
 */

public class ActivityTabLayout extends ActivityBase {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.wv)
    WebViewVideoFull wv;
    @BindView(R.id.wv2)
    WebView wv2;

    @BindView(R.id.tbsContent)
    com.tencent.smtt.sdk.WebView t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);
        initMyToolbar();

        initTentcent();

//        wv.loadUrl("http://voip.vtc365.com/LiveVideoServer/m_liveVideoRoom.do?groupId=731&key=fLmlBp02FK81SZ65AYui");
//        wv2.getSettings().setJavaScriptEnabled(true);
//        wv2.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                MyLog.i("mylog========="+errorCode);
//                switch (errorCode){
//
//                }
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                MyLog.i("mylog====new======="+error.getErrorCode());
//            }
//
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                MyLog.i("mylog====onReceivedHttpError======="+errorResponse.getStatusCode());
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//                MyLog.i("mylog====onReceivedSslError======="+error.getUrl());
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                MyLog.i("mylog=======onPageFinished"+url);
//                wv2.getSettings().setBlockNetworkImage(false);
//            }
//
//            @Override
//            public void onPageCommitVisible(WebView view, String url) {
//                super.onPageCommitVisible(view, url);
//                MyLog.i("mylog=======onPageCommitVisible"+url);
//
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                MyLog.i("mylog=======onPageStarted"+url);
//            }
//        });
//        wv2.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                MyLog.i("mylog====onProgressChanged======="+newProgress);
//            }
//
//        });
//        initSettings(wv2);
////        wv2.loadUrl("http://voip.vtc365.com/LiveVideoServer/play4ThirdParty.do?video.videoId=346721");
//        wv2.loadUrl("http://sunroam.imgup.cn/evaluationH5/?userId=23395370&systemflag=87000025");
    }

    protected void initSettings(WebView webView) {
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        //启用数据库
        mWebSettings.setDatabaseEnabled(true);
        //启用地理定位
        mWebSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);

        mWebSettings.setBlockNetworkImage(true);
        mWebSettings.setAppCacheEnabled(true);
    }


    private void initTentcent() {
        com.tencent.smtt.sdk.WebSettings mWebSettings = t.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        //启用数据库
        mWebSettings.setDatabaseEnabled(true);
        //启用地理定位
        mWebSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);

        mWebSettings.setAppCacheEnabled(true);
        t.loadUrl("http://sunroam.imgup.cn/evaluationH5/?userId=23395370&systemflag=87000025");
    }


    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void updateCookies(String url, String value) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) { // 2.3及以下

            CookieSyncManager.createInstance(getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, value);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            CookieSyncManager.getInstance().sync();
        }
    }


}
