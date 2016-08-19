package com.sage.demo0809.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.step.SHealthConnectService;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ActivityStep extends AppCompatActivity {

    SHealthConnectService.MySHealthBind binder;
    TextView tv_step;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder= (SHealthConnectService.MySHealthBind) service;
            MyLog.i("111111111111=====onServiceConnected");
            if(binder!=null){
                binder.startConnect(ActivityStep.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_step = (TextView) findViewById(R.id.tv_step);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),ActivityD.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        EventBus.getDefault().register(this);
        startService(new Intent(this,SHealthConnectService.class));
        bindService(new Intent(this,SHealthConnectService.class),conn,BIND_AUTO_CREATE);

        float size=tv_step.getTextSize();
        MyLog.i("30 size=="+size+" =="+getResources().getDisplayMetrics().density+"=="+getResources().getDisplayMetrics().densityDpi);

new SimpleDateFormat("yyyy_MM-dd").format(new Date(111111111));
        spiltString("dddd,1111");
        spiltString("dddd,1111,");
        spiltString(",dddd,1111");
        spiltString("");
        spiltString(",");

    }

    private void spiltString(String string){
        String[] arr=string.split(",");
        MyLog.i(Arrays.toString(arr)+"===="+arr.length+"===="+string);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(binder!=null)
        unbindService(conn);

    }

    @Subscriber(tag = "S_Health_Step")
    public void getStep(int step){
        tv_step.setText("步数"+step);
    }

//    private HealthConnectionErrorResult mConnError;
//    @Subscriber(tag = "showConnectionFailureDialog")
//    public void showConnectionFailureDialog(HealthConnectionErrorResult error) {
//        if (isFinishing()) {
//            return;
//        }
//        mConnError = error;
//        String message = "连接S健康失败";
//        if (mConnError.hasResolution()) {
//            switch (error.getErrorCode()) {
//                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
//                    message = "请安装S健康";
//                    break;
//                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
//                    message = "请更新S健康";
//                    break;
//                case HealthConnectionErrorResult.PLATFORM_DISABLED:
//                    message = "请允许S健康读取步数";
//                    break;
//                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
//                    message = "请同意S健康协议";
//                    break;
//                default:
//                    message = "请打开S健康使其可用";
//                    break;
//            }
//        }
//        FragmentDiaOkCancel.create("温馨提示", message).setListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mConnError.hasResolution()) {
//                    mConnError.resolve(ActivityStep.this);
//                }
//            }
//        }).show(getSupportFragmentManager(), "showAlert");
//
//    }
//
//    @Subscriber(tag = "showPermissionAlarmDialog")
//    public void showPermissionAlarmDialog(String result) {
//        if (isFinishing()) {
//            return;
//        }
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Notice");
//        alert.setMessage("All permissions should be acquired");
//        alert.setPositiveButton("OK", null);
//        alert.show();
//    }
//
//    private Set<HealthPermissionManager.PermissionKey> mKeySet;
//    @Subscriber(tag = "SHealthPermissionCheck")
//    public void SHealthPermissionCheck(HealthDataStore mStore) {
//        mKeySet = new HashSet<>();
//        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
//        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
//        try {
//            // Check whether the permissions that this application needs are acquired
//            Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);
//
//            if (resultMap.containsValue(Boolean.FALSE)) {
//                // Request the permission for reading step counts if it is not acquired
//                pmsManager.requestPermissions(mKeySet, this).setResultListener(mPermissionListener);
//            } else {
//                // Get the current step count and display it
//                startSHealthService();
////            updateDataType(1);
//            }
//        } catch (Exception e) {
//            MyLog.i( "Permission setting fails.");
//        }
//    }
//    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
//            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {
//
//                @Override
//                public void onResult(HealthPermissionManager.PermissionResult result) {
//                    MyLog.i( "Permission callback is received.");
//                    Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();
//
//                    if (resultMap.containsValue(Boolean.FALSE)) {
//                        EventBus.getDefault().post("","showPermissionAlarmDialog");
//                    } else {
//                        // Get the current step count and display it
//                        startSHealthService();
//                    }
//                }
//            };
//    private void startSHealthService(){
//        startService(new Intent(this, SHealthConnectService.class).setAction(SHealthConnectService.ACTION_START_OBSERVER_STEP));
//    }
}
