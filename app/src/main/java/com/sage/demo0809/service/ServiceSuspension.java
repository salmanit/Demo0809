package com.sage.demo0809.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.ui.ActivityLists;

import java.util.LinkedList;

/**
 * Created by Sage on 2016/11/14.
 */

public class ServiceSuspension extends Service {
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    View mFloatView;

    private static final String TAG = "FxService";

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i(TAG, "oncreat");
        try {
            showNotification();
            createFloatView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarkNotificationTheme(Context context) {
        return !isSimilarColor(Color.BLACK, getNotificationColor(context));
    }
    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor=baseColor|0xff000000;
        int simpleColor=color|0xff000000;
        int baseRed=Color.red(simpleBaseColor)-Color.red(simpleColor);
        int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
        int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
        double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
        if (value<180.0) {
            return true;
        }
        return false;
    }

    private void showNotification() {
         boolean isDark=isDarkNotificationTheme(this);

        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.layout_step_notify_custom);
        remoteViews.setTextViewText(R.id.tv_step,22222+"\n步");
        remoteViews.setTextViewText(R.id.tv_distance,22+"\n公里");
        remoteViews.setTextViewText(R.id.tv_calorie,123+"\n千卡");
        remoteViews.setInt(R.id.tv_step,"setTextColor",isDark?Color.WHITE:Color.BLACK);
        remoteViews.setInt(R.id.tv_distance,"setTextColor",isDark?Color.WHITE:Color.BLACK);
        remoteViews.setInt(R.id.tv_calorie,"setTextColor",isDark?Color.WHITE:Color.BLACK);
        Intent intent=new Intent(getApplicationContext(), ActivityLists.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),200,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setContent(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
        startForeground(999, builder.build());
    }

    /**
     * 获取通知栏颜色
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        Notification notification=builder.build();
        int layoutId=notification.contentView.getLayoutId();
        ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title)!=null) {
            MyLog.i("======11===="+((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor());
//            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color= Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups=new LinkedList<>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size()>0) {
            ViewGroup viewGroup1=viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                }
                else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor()!=-1) {
                        color=((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                        MyLog.i("=============="+color);
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }




    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private void createFloatView()
    {
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(Context.WINDOW_SERVICE);
        Log.i(TAG, "mWindowManager--->" + mWindowManager);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

         /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.layout_suspension_test, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = mFloatLayout.findViewById(R.id.btn_click);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
        //设置监听浮动窗口的触摸移动
        mFloatLayout.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;
                Log.i(TAG, "RawX" + event.getRawX());
                Log.i(TAG, "X" + event.getX());
                //减25为状态栏的高度
                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight()/2 - 25;
                Log.i(TAG, "RawY" + event.getRawY());
                Log.i(TAG, "Y" + event.getY());
                //刷新
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;  //此处必须返回false，否则OnClickListener获取不到监听
            }
        });

        mFloatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Toast.makeText(getApplication(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mFloatLayout != null)
        {
            //移除悬浮窗口
            mWindowManager.removeView(mFloatLayout);
        }

        try {
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(999);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GestureDetectorCompat gestureDetectorCompat;

    private void test(){
        gestureDetectorCompat=new GestureDetectorCompat(this,new GestureDetector.SimpleOnGestureListener());
    }

}
