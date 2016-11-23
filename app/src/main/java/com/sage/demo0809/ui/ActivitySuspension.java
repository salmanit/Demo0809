package com.sage.demo0809.ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sage.demo0809.R;
import com.sage.demo0809.service.ServiceSuspension;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/11/14.
 * 此类还未完工，触摸移动写的不好，
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_open_service, R.id.btn_close_service, R.id.btn_open, R.id.btn_close})
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
                if(mFloatLayout != null)
                {
                    mWindowManager.removeView(mFloatLayout);
                    finish();
                }

                break;
        }
    }

    private static final String TAG = "FloatWindowTest";
    WindowManager mWindowManager;
    WindowManager.LayoutParams wmParams;
    LinearLayout mFloatLayout;
    Button mFloatView;
    private void createFloatView()
    {
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
        wmParams.format = PixelFormat.RGBA_8888;;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = this.getLayoutInflater();//LayoutInflater.from(getApplication());

        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.layout_suspension_for_activty, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        //setContentView(R.layout.main);
        mFloatView = (Button)mFloatLayout.findViewById(R.id.btn_activity);

        Log.i(TAG, "mFloatView" + mFloatView);
        Log.i(TAG, "mFloatView--parent-->" + mFloatView.getParent());
        Log.i(TAG, "mFloatView--parent--parent-->" + mFloatView.getParent().getParent());
        //绑定触摸移动监听
        mFloatLayout.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                wmParams.x = (int)event.getRawX() - mFloatLayout.getWidth()/2;
                //25为状态栏高度
                wmParams.y = (int)event.getRawY() - mFloatLayout.getHeight()/2 - 40;
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        //绑定点击监听
        mFloatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                showToast("click for what");
            }
        });

    }


}
