package com.sage.demo0809.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.view.accessibility.AccessibilityEvent;

import com.sage.demo0809.MyLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sage on 2016/11/1.
 */

public class OtherAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        MyLog.i("onAccessibilityEvent=========11==="+event.toString());


        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                //界面点击
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                //界面文字改动
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logWrite("==============onCreate=============");
        registerReceiver(minuteTimeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }
    private BroadcastReceiver minuteTimeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {

                logWrite(new SimpleDateFormat("MM_dd HH:mm:ss").format(new Date())+" "+isAppBefore()+"\n");
            }
        }
    };

    public boolean isAppBefore() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(10000);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(
                    getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private void logWrite(String msg){
        System.out.println("======="+msg);
        File file=new File(Environment.getExternalStorageDirectory(),"abcdefg.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            FileOutputStream fos=new FileOutputStream(file,true);
            fos.write(msg.getBytes("utf-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onInterrupt() {
        logWrite("==============onInterrupt=============");
        MyLog.i("RedAccessibilityService========11==onInterrupt");
    }


    /**下边的是动态的添加配置信息，也可以通过xml文件设置,监听多个应用数组里就写多个包名的*/
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.platform.undergraduate"};
        serviceInfo.notificationTimeout=100;
        setServiceInfo(serviceInfo);

    }
}
