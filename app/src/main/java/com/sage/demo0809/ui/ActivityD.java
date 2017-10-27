package com.sage.demo0809.ui;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.step.SHealthConnectService;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ActivityD extends ActivityBase {
    String url = "http://sunroam.imgup.cn/aerospace/bjc/19.mp4";
    Toolbar toolbar;
    ImageView iv_thumb;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setTitle("");
//        toolbar.setFitsSystemWindows(false);
        iv_thumb = (ImageView) findViewById(R.id.iv_thumb);
//        if(Build.VERSION.SDK_INT>=21)
//            getWindow().setStatusBarColor(Color.WHITE);
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = createVideoThumbnail(url, MediaStore.Video.Thumbnails.MINI_KIND);
                e.onNext(bitmap);
            }
        })
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        iv_thumb.setImageBitmap(bitmap);
                    }
                });
        Observable
                .create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
                        Bitmap bitmap = get(new File(Environment.getExternalStorageDirectory(),
                                "device-2016-12-14-143818.mp4").getAbsolutePath());
                        e.onNext(bitmap);
                    }
                })
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        MyLog.i("=============");
                        et1.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }
                });

        iv_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStep();
            }
        });
        et1 = (EditText) findViewById(R.id.et1);
        EventBus.getDefault().register(this);
    }

    private void startStep() {
//        bindService(new Intent(this,SHealthConnectService.class),conn,BIND_AUTO_CREATE);
    }

    SHealthConnectService.MySHealthBind binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (SHealthConnectService.MySHealthBind) service;
            MyLog.i("111111111111=====onServiceConnected");
            if (binder != null) {
                binder.startConnect(ActivityD.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (binder != null)
            unbindService(conn);

    }

    @org.simple.eventbus.Subscriber(tag = "S_Health_Step")
    public void getStep(int step) {
        et1.setText("步数" + step);
    }

    private Bitmap get(String filePath) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MINI_KIND);
        MyLog.i("======" + (bitmap == null));
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap createVideoThumbnail(String url, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (bitmap == null) return null;
        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 96, 96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}
