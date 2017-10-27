package com.sage.demo0809.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.app.ApiManager;
import com.sage.demo0809.app.RxHelper;
import com.sage.demo0809.app.RxSubscribe;
import com.sage.demo0809.bean.MyRecordChart;
import com.sage.demo0809.bean.MyRecordParams;

import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            setSupportActionBar(toolbar);
            tintManager.setStatusBarTintResource(android.R.color.holo_blue_bright);
        }
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        String path=new File(Environment.getExternalStorageDirectory(),"test.png").getAbsolutePath();
//        ApiManager.getService().uploadAvatar(new BaseParams(), MultipartBody.Part.create(RequestBody.create(MediaType.parse("image/png"),new File(path))))
//                .compose(new RxHelper<UploadAvatar>().handleResult())
//                .subscribe(new RxSubscribe<UploadAvatar>() {
//
//                    @Override
//                    public void onNext(UploadAvatar o) {
//                        showToast(o.avatar);
//                    }
//
//                    @Override
//                    public void _onError(String msg) {
//                        showToast(msg);
//                    }
//                });


        MyRecordParams params = new MyRecordParams();
        MyLog.i("---------" + params.toString());
//        ApiManager.getService().getTodayInfo(params)
//                .compose(new RxHelper<MyRecordChart>().handleBase())
//                .subscribe(new RxSubscribe<MyRecordChart>() {
//                    @Override
//                    public void _onError(String msg) {
//                        showToast(msg);
//                    }
//
//                    @Override
//                    public void onNext(MyRecordChart myRecordChart) {
//                        showToast(myRecordChart.weekAverage+"==");
//                    }
//                });
//        ApiManager.getService().getTodayInfo("13795492180","888888","1.0","360","hr","1")
//                .compose(new RxHelper<MyRecordChart>().handleBase())
//                .subscribe(new RxSubscribe<MyRecordChart>() {
//                    @Override
//                    public void _onError(String msg) {
//                        showToast(msg+"==");
//                    }
//
//                    @Override
//                    public void onNext(MyRecordChart myRecordChart) {
//                        showToast(myRecordChart.weekAverage+"==1111");
//                    }
//                });

        HashMap<String, String> map = new HashMap<>();
        map.put("version", "1.0");
        map.put("channel", "360");
        map.put("mobile_phone", "13795492180");
        map.put("password", "888888");
        map.put("search", "hr");
        map.put("page", "1");
        ApiManager.getService().getTodayInfo(map)
                .compose(new RxHelper<MyRecordChart>().handleBase())
                .subscribe(new RxSubscribe<MyRecordChart>() {
                    @Override
                    public void _onError(String msg) {
                        showToast("error==" + msg);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(MyRecordChart myRecordChart) {
                        showToast("=======" + myRecordChart.toString());
                    }
                });

//        ApiManager.getService().login(new LoginParam())
////                .compose(new RxHelper<String>().handleResult())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<LoginReply>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MyLog.i("error========="+e.toString());
//            }
//
//            @Override
//            public void onNext(LoginReply s) {
//                MyLog.i("s========="+s.toString());
//            }
//        });
    }

    public void showToast(String msg) {
        Snackbar.make(findViewById(Window.ID_ANDROID_CONTENT), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
