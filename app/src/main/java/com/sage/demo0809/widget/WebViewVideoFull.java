package com.sage.demo0809.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sage.demo0809.BuildConfig;

/**
 * Created by Sage on 2016/11/24.
 */

public class WebViewVideoFull extends WebView {
    public ProgressBar progressbar;
    public TextView tv_error;
    private FragmentActivity activity;
    private String webViewUrl;
    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    public WebViewVideoFull(Context context) {
        super(context);
        initDefault(context);
    }

    public WebViewVideoFull(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault(context);
    }

    public WebViewVideoFull(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault(context);
    }


    private void initDefault(Context context){
        activity= (FragmentActivity) context;
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 15, 0, 0));
        addView(progressbar);

        tv_error=new TextView(context);
        tv_error.setText("点击刷新");
        tv_error.setBackgroundColor(Color.parseColor("#66000000"));
        tv_error.setLayoutParams( new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 250, 300));
        tv_error.setPadding(20,10,10,20);
        tv_error.setVisibility(GONE);
        addView(tv_error);

        initSettings();
        setWebChromeClient(new WebChromeClientCustom());
        setWebViewClient(new WebViewClientCustom());
    }
    public class WebChromeClientCustom extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            try {
                if (newProgress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    if (progressbar.getVisibility() == GONE)
                        progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(newProgress);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onProgressChanged(view, newProgress);
        }

        /*** 视频播放相关的方法 **/

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            showCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            hideCustomView();
        }
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message,
//                                 JsResult result) {
//            Toast.makeText(view.getContext(),message,Toast.LENGTH_SHORT).show();
//            LogUtil.i("onJsAlert url=="+url+"  ==="+message);
//            result.confirm();
//            return true;
//        }
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            LogUtil.i("onJsConfirm url=="+url+"  ==="+message);
//            return super.onJsConfirm(view, url, message, result);
//        }
//
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//            LogUtil.i("onJsPrompt url=="+url+"  ==="+message+"  defaultValue="+defaultValue);
//            return super.onJsPrompt(view, url, message, defaultValue, result);
//        }
    }

    private void LogDebug(String msg){
        if(BuildConfig.DEBUG){
            System.out.println("======"+msg);
        }
    }
    public class WebViewClientCustom extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if( url.startsWith("http:") || url.startsWith("https:") ) {
                view.loadUrl(url);
                return true;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                //给予提示，没有对应的程序,返回ture,点击没任何反应，如果false，则会显示一个无法打开的页面。。
                return true;
            }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            LogDebug("WebViewerror" + errorCode);
            tv_error.setVisibility(View.VISIBLE);
            tv_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_error.setVisibility(View.GONE);
                    loadUrl(failingUrl);
                }
            });
        }

    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        try {
            AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) progressbar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            progressbar.setLayoutParams(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }


    protected  void initSettings(){
        WebSettings mWebSettings =getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        //启用数据库
        mWebSettings.setDatabaseEnabled(true);
        //启用地理定位
        mWebSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        ((Activity)getContext()).getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(activity);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(FragmentActivity ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
