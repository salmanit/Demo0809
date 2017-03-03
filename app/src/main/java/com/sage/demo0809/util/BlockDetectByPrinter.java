package com.sage.demo0809.util;

import android.os.Looper;
import android.util.Printer;

/**
 * Created by Sage on 2017/3/2.
 */

public class BlockDetectByPrinter {

    public static void start(){Looper.loop();
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START=">>>>> Dispatching to";
            private static final String END="<<<<< Finished to";
            @Override
            public void println(String x) {
                if(x.startsWith(START)){
                LogMonitor.getInstance().startMonitor();
                }else if(x.startsWith(END)){
                LogMonitor.getInstance().startMonitor();
                }
            }
        });
    }
}
