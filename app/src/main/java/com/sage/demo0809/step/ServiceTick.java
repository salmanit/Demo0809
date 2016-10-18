package com.sage.demo0809.step;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sage on 2016/10/9.
 */

public class ServiceTick extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiverTick=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,new SimpleDateFormat("HH:mm:ss").format(new Date()),Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter=new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiverTick,filter);
    }

    BroadcastReceiver receiverTick;
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiverTick!=null){
            try {
                unregisterReceiver(receiverTick);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
