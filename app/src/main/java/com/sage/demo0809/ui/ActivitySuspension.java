package com.sage.demo0809.ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.service.ServiceSuspension;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/11/14.
 */

public class ActivitySuspension extends ActivityBase {

    @BindView(R.id.btn_open_service)
    Button btnOpenService;
    @BindView(R.id.btn_close_service)
    Button btnCloseService;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension);
        ButterKnife.bind(this);
//        tv1.setTextColor(-436207617);
//        tv2.setTextColor(0);
//        tv3.setTextColor(2147483647);
//        tv4.setTextColor(-1);
        tv1.setTextColor(-452984832);
        tv2.setTextColor(-1728053248);
        tv3.setTextColor(2130706432);
        tv4.setTextColor(-1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @OnClick({R.id.btn_open_service, R.id.btn_close_service, R.id.btn_open, R.id.btn_close, R.id.btn_display})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_service:
                startService(new Intent(this, ServiceSuspension.class));
                break;
            case R.id.btn_close_service:

                stopService(new Intent(this, ServiceSuspension.class));
                break;
            case R.id.btn_open:
                createFloatView();
                break;
            case R.id.btn_close:
                if (mFloatLayout != null) {
                    mWindowManager.removeView(mFloatLayout);
                    finish();
                }

                break;
            case R.id.btn_display:
//                try {
//                    View temp=LayoutInflater.from(this).inflate(R.layout.layout_suspension_test,null);
//                    Presentation presentation=new Presentation(this,findViewById(R.id.layout_test).getDisplay());
//                    presentation.setContentView(R.layout.layout_suspension_test);
//                    presentation.show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                ViewCompat.setTranslationY(btnClose, 500);
                ViewCompat.setRotation(btnClose, 20);
                break;
        }
    }

    private static final String TAG = "FloatWindowTest";
    WindowManager mWindowManager;
    WindowManager.LayoutParams wmParams;
    LinearLayout mFloatLayout;
    View mFloatView;
    int[] location;

    private void createFloatView() {
        //获取LayoutParams对象
        wmParams = new WindowManager.LayoutParams();

        //获取的是LocalWindowManager对象
        mWindowManager = this.getWindowManager();
        Log.i(TAG, "mWindowManager1--->" + this.getWindowManager());
        //mWindowManager = getWindow().getWindowManager();
        Log.i(TAG, "mWindowManager2--->" + getWindow().getWindowManager());

        //获取的是CompatModeWrapper对象
        //mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        Log.i(TAG, "mWindowManager3--->" + mWindowManager);
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wmParams.format = PixelFormat.RGBA_8888;
        ;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.CENTER;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = this.getLayoutInflater();//LayoutInflater.from(getApplication());

        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.layout_suspension_for_activty, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        //setContentView(R.layout.main);
        mFloatView = (View) mFloatLayout.findViewById(R.id.btn_activity);

        Log.i(TAG, "mFloatView" + mFloatView);
        Log.i(TAG, "mFloatView--parent-->" + mFloatView.getParent());
        Log.i(TAG, "mFloatView--parent--parent-->" + mFloatView.getParent().getParent());
        //绑定触摸移动监听
        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (location == null) {
                    location = new int[2];
                    mFloatLayout.getLocationOnScreen(location);
                }
                wmParams.x = (int) ((int) event.getRawX() - mFloatLayout.getWidth() / 2 - location[0]);
                //25为状态栏高度
                wmParams.y = (int) ((int) event.getRawY() - mFloatLayout.getHeight() / 2 - 40 - location[1]);
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        //绑定点击监听
        mFloatView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showToast("click for what");
            }
        });

    }


}
