package com.sage.demo0809.step;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sage.demo0809.MyLog;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sage on 2016/8/11.
 */
public class SHealthGetService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private StepCountReporter mReporter;
    private void getShealth() {
        mKeySet = new HashSet<>();
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mStore = new HealthDataStore(this, mConnectionListener);
        mStore.connectService();
    }


    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {

                @Override
                public void onResult(HealthPermissionManager.PermissionResult result) {
                    MyLog.i( "Permission callback is received.");
                    Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();

                    if (resultMap.containsValue(Boolean.FALSE)) {
                        showPermissionAlarmDialog();
                    } else {
                        // Get the current step count and display it
                        mReporter.start();
                    }
                }
            };
    private final HealthDataStore.ConnectionListener mConnectionListener =
            new HealthDataStore.ConnectionListener() {

                @Override
                public void onConnected() {
                    HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
                    mReporter = new StepCountReporter(mStore);

                    try {
                        // Check whether the permissions that this application needs are acquired
                        Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                        if (resultMap.containsValue(Boolean.FALSE)) {
                            // Request the permission for reading step counts if it is not acquired
                            pmsManager.requestPermissions(mKeySet, this).setResultListener(mPermissionListener);
                        } else {
                            // Get the current step count and display it
                            mReporter.start();
//                            updateDataType(1);
                        }
                    } catch (Exception e) {
                        MyLog.i( "Permission setting fails.");
                    }
                }

                @Override
                public void onConnectionFailed(HealthConnectionErrorResult error) {
                    MyLog.i( "Health data service is not available.");
                    showConnectionFailureDialog(error);
                }

                @Override
                public void onDisconnected() {
                    MyLog.i("Health data service is disconnected.");
                }
            };
    private void showConnectionFailureDialog(HealthConnectionErrorResult error) {
//        if(isFinishing()){
//            return;
//        }
        mConnError = error;
        String message = "连接S健康失败";
        if (mConnError.hasResolution()) {
            switch(error.getErrorCode()) {
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
//        FragmentDiaOkCancel.create("温馨提示",message).setListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mConnError.hasResolution()) {
//                    mConnError.resolve(ActivityStepDataType.this);
//                }
//            }
//        }).show(getSupportFragmentManager(),"showAlert");

    }
    private void showPermissionAlarmDialog() {
//        if (isFinishing()) {
//            return;
//        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Notice");
        alert.setMessage("All permissions should be acquired");
        alert.setPositiveButton("OK", null);
        alert.show();
    }
}
