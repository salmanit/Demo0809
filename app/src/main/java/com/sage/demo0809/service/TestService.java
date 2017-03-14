package com.sage.demo0809.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Sage on 2017/1/16.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class TestService extends Service implements SensorEventListener {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            addCountStepListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sensor detectorSensor;
    Sensor countSensor;
    private SensorManager mySensorManager;
    private Sensor myAccelerometer;
    int stepSensor = -1;
    SharedPreferences sp;

    //测步器&计步器传感器
    private void addCountStepListener() {
        sp = getSharedPreferences("aaaaa", Context.MODE_PRIVATE);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        detectorSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        countSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            stepSensor = 0;

            mySensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
//            mySensorManager.registerListener(tempListener, countSensor, SensorManager.SENSOR_DELAY_UI);
            mySensorManager.requestTriggerSensor(triggerEventListener,countSensor);

        }
        else
        if (detectorSensor != null) {
            stepSensor = 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mySensorManager.registerListener(detectorSensorListener, detectorSensor, SensorManager.SENSOR_DELAY_UI);
                }
            }).start();

        } else {

        }
    }

    private TriggerEventListener triggerEventListener=new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            System.out.println("triggerEventListener temp===========" + Arrays.toString(event.values));
        }
    };
    private SensorEventListener tempListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            System.out.println("temp===========" + Arrays.toString(event.values));
            mySensorManager.unregisterListener(tempListener);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private SensorEventListener detectorSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            long realTime = System.currentTimeMillis() + ((event.timestamp - SystemClock.elapsedRealtimeNanos()) / 1000000L);
            System.out.println(event.sensor.getName()+"=detectorSensorListener="  + "======" + Arrays.toString(event.values)
                    + "==" + format.format(new Date(realTime)) + "timestamp==" + event.timestamp + "accuracy====" + event.accuracy
                    + "time=" + SystemClock.elapsedRealtimeNanos());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");

    @Override
    public void onSensorChanged(SensorEvent event) {
        long realTime = System.currentTimeMillis() + ((event.timestamp - SystemClock.elapsedRealtimeNanos()) / 1000000L);
        System.out.println(event.sensor.getName()+"=stepSensor=" + stepSensor + "======" + Arrays.toString(event.values)
                + "==" + format.format(new Date(realTime)) + "timestamp==" + event.timestamp + "accuracy====" + event.accuracy
                + "time=" + SystemClock.elapsedRealtimeNanos());

        sp.edit().putFloat("stepTemp", event.values[0]).apply();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        mySensorManager.unregisterListener(this);
//
//    }
}
