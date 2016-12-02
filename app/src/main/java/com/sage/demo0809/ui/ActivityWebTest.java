package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sage.demo0809.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/11/30.
 */

public class ActivityWebTest extends ActivityBase {

    @BindView(R.id.wv)
    WebView wv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        ButterKnife.bind(this);

        initSettings(wv);
        wv.loadUrl("http://www.top-eap.com/case6.html");
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

        mWebSettings.setAppCacheEnabled(true);


        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
    }
}
