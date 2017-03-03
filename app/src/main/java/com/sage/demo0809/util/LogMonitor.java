package com.sage.demo0809.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Sage on 2017/3/2.
 */

public class LogMonitor {
    private static LogMonitor logMonitor=new LogMonitor();
    private HandlerThread handlerThread=new HandlerThread("log");
    private Handler handler;
    private static long block_time=1000;

    private LogMonitor(){
        handlerThread.start();
        handler=new Handler(handlerThread.getLooper());
    }
    public static LogMonitor getInstance(){
        return logMonitor;
    }

    private static Runnable logRunnable=new Runnable() {
        @Override
        public void run() {
            StringBuilder sb=new StringBuilder();
            StackTraceElement[] elements= Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement temp:elements){
                sb.append(temp.toString()+"\n");
            }
            Log.e("TAG", sb.toString());
        }
    };


    public void startMonitor(){
        handler.postDelayed(logRunnable,block_time);
    }

    public void removeMonitor(){
        handler.removeCallbacks(logRunnable);
    }
}
