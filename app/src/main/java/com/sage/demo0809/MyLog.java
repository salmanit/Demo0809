package com.sage.demo0809;

import android.util.Log;

/**
 * Created by Sage on 2016/8/9.
 */
public class MyLog {

    public static boolean showLog=true;
    public static void i(String msg){
        i(null,msg);
    }
    public static void i( String tag, String msg) {
        if (!showLog) {
            return;
        }
        if(tag==null){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//            for(int i=0;i<stackTrace.length;i++){
//                System.out.println("i="+i+",="+stackTrace[i].getFileName()+",="+stackTrace[i].getMethodName());
//            }
            int index = 5;
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();
            methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(":");
           tag = stringBuilder.toString();
        }

        Log.i(tag, msg);

    }
}
