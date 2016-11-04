package com.sage.demo0809.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

import com.sage.demo0809.MyLog;

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
    public void onInterrupt() {

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
