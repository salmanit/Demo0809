package com.sage.demo0809.app;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

/**
 * Created by Sage on 2016/9/20.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
