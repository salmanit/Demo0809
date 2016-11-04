package com.sage.demo0809.step;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.sage.demo0809.MyLog;
import com.sage.imagechooser.FragmentDiaOkCancel;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sage on 2016/8/12.
 */
public class SHealthConnectService extends Service {

    public static String ACTION_START_OBSERVER_STEP="action_start_observer_step";/**开始监听s健康步数变化*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MySHealthBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        MyLog.i("111111111111=====onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(intent!=null&& TextUtils.equals(ACTION_START_OBSERVER_STEP,intent.getAction())){
//            startObserver();
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopObserver();
        disConnect();
    }
    public enum ServiceStatus{
        readStep,/**观察者默认监听步数中*/
        Connected,/**已经建立连接，可能正在判断权限*/
        ConnectionFailed,/**连接失败*/
        Disconnected,/**断开连接*/
//        init;/**刚启动服务初始化中*/
    }
    public ServiceStatus connectStatus=ServiceStatus.Disconnected;
    public FragmentActivity activity;
    public class MySHealthBind extends Binder{

        public void startConnect(FragmentActivity ac){
            activity=ac;
            MyLog.i("status=============="+connectStatus);
            if(mStore!=null){
                if(!isConnecting()){
                    mStore.connectService();
                }else if(isObserver()){
                    readTodayStepCount();
                }
            }
        }
        public void stopConnect(){
            if(mStore!=null){
                mStore.disconnectService();
            }
        }

        public ServiceStatus getStatus(){
            return connectStatus;
        }
        public boolean isObserver(){
            return connectStatus==ServiceStatus.readStep;
        }

        public boolean isConnecting(){
            return connectStatus!=ServiceStatus.Disconnected&&connectStatus!=ServiceStatus.ConnectionFailed;
        }
    }


    private HealthConnectionErrorResult mConnError;
    @Subscriber(tag = "showConnectionFailureDialog")
    public void showConnectionFailureDialog(HealthConnectionErrorResult error) {
        if (activity.isFinishing()) {
            return;
        }
        mConnError = error;
        String message = "连接S健康失败";
        if (mConnError.hasResolution()) {
            switch (error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    message = "请安装S健康";
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    message = "请更新S健康";
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    message = "请允许S健康读取步数";
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    message = "请同意S健康协议";
                    break;
                default:
                    message = "请打开S健康使其可用";
                    break;
            }
        }
        FragmentDiaOkCancel.create("温馨提示", message).setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConnError.hasResolution()) {
                    mConnError.resolve(activity);
                }
            }
        }).show(activity.getSupportFragmentManager(), "showAlert");

    }

    public void showPermissionAlarmDialog() {
        if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Notice");
        alert.setMessage("All permissions should be acquired");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    public void SHealthPermissionCheck(HealthDataStore mStore) {
        mKeySet = new HashSet<>();
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Check whether the permissions that this application needs are acquired
            Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

            if (resultMap.containsValue(Boolean.FALSE)) {

                // Request the permission for reading step counts if it is not acquired
                pmsManager.requestPermissions(mKeySet, activity).setResultListener(mPermissionListener);
            } else {
                // Get the current step count and display it
                startObserver();
//            updateDataType(1);
            }
        } catch (Exception e) {
            MyLog.i( "Permission setting fails.");
        }
    }
    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {

                @Override
                public void onResult(HealthPermissionManager.PermissionResult result) {
                    MyLog.i( "Permission callback is received.");
                    Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();

                    if (resultMap.containsValue(Boolean.FALSE)) {
                        if(mStore!=null){
                            mStore.disconnectService();
                        }
                        connectStatus=ServiceStatus.Disconnected;
                        showPermissionAlarmDialog();
                    } else {
                        // Get the current step count and display it
                        startObserver();
                    }
                }
            };

    private  HealthDataStore mStore;
    public void init() {

        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mStore = new HealthDataStore(this, mConnectionListener);

    }

    public void disConnect(){
        if(mStore!=null)
            mStore.disconnectService();
    }
    private final HealthDataStore.ConnectionListener mConnectionListener =
            new HealthDataStore.ConnectionListener() {

                @Override
                public void onConnected() {
                    connectStatus=ServiceStatus.Connected;
                    SHealthPermissionCheck(mStore);
                }

                @Override
                public void onConnectionFailed(HealthConnectionErrorResult error) {
                    connectStatus=ServiceStatus.ConnectionFailed;
                    MyLog.i( "Health data service is not available.");
                    showConnectionFailureDialog(error);
                }

                @Override
                public void onDisconnected() {
                    connectStatus=ServiceStatus.Disconnected;
                    MyLog.i("Health data service is disconnected.");
                }
            };


    public void startObserver() {
        connectStatus=ServiceStatus.readStep;
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.StepCount.HEALTH_DATA_TYPE, mObserver);
        readTodayStepCount();
    }
    public void stopObserver(){
        if(mStore!=null&&mObserver!=null)
            HealthDataObserver.removeObserver(mStore, mObserver);
    }
    // Read the today's step count on demand
    private void readTodayStepCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // Set time range from start time of today to the current time
        long startTime = getStartTimeOfToday();
        long endTime = System.currentTimeMillis();
        HealthDataResolver.Filter filter = HealthDataResolver.Filter.and(HealthDataResolver.Filter.greaterThanEquals(HealthConstants.StepCount.START_TIME, startTime),
                HealthDataResolver.Filter.lessThanEquals(HealthConstants.StepCount.START_TIME, endTime));

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.StepCount.HEALTH_DATA_TYPE)
                .setProperties(new String[] {HealthConstants.StepCount.COUNT
                        ,HealthConstants.StepCount.CALORIE
                        , HealthConstants.StepCount.DISTANCE
                        , HealthConstants.StepCount.CREATE_TIME
                        , HealthConstants.StepCount.END_TIME
                        , HealthConstants.StepCount.UPDATE_TIME
                        })
                .setFilter(filter)
                .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            MyLog.i(e.toString());
        }
    }

    private long getStartTimeOfToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = new HealthResultHolder.ResultListener<HealthDataResolver.ReadResult>() {
        @Override
        public void onResult(HealthDataResolver.ReadResult result) {
            int count = 0;
            Cursor c = null;

            try {
                c = result.getResultCursor();
                if (c != null) {
                    /**s健康卡路里的单位是大卡，距离是米，都是double类型的，时间都是long的**/
                    MyLog.i("=="+c.getCount()+"=="+c.getColumnCount()+"=="+ Arrays.toString(c.getColumnNames()));
                    double calorie=0;
                    double distance=0;
                    while (c.moveToNext()) {
                        count += c.getInt(c.getColumnIndex(HealthConstants.StepCount.COUNT));
                        MyLog.i(c.getString(0)+"=="+c.getString(1)+"=="+c.getString(2)+"=="+c.getString(3)+"=="+c.getString(4)+"=="+c.getString(5));
                        calorie+=c.getDouble(1);
                        distance+=c.getDouble(2);
                    }
                    MyLog.i(calorie+"======"+distance);
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }

            EventBus.getDefault().post(count,"S_Health_Step");
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // Update the step count when a change event is received
        @Override
        public void onChange(String dataTypeName) {
            MyLog.i("Observer receives a data changed event");
            readTodayStepCount();
        }
    };
}
