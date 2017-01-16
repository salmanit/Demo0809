package com.sage.demo0809.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.sage.demo0809.MyLog;

/**
 * Created by Sage on 2017/1/16.
 */

public class ReceiverShotdown extends BroadcastReceiver {

    Sensor countSensor;
    private SensorManager mySensorManager;
    @Override
    public void onReceive(final Context context, Intent intent) {

        mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        countSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            mySensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    System.out.println("stepSensor="+event.values[0]);
                    context.getSharedPreferences("aaaaa", Context.MODE_PRIVATE).edit().putFloat("step",event.values[0]).commit();
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
}
