package com.sage.demo0809.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.sage.demo0809.R;

/**
 * Created by Administrator on 2016/1/28.
 */
public class BatteryView extends View {
    private Context mContext;
    /**
     * 电池电量
     */
    private float level;
    /**
     * 控件半径
     */
    private int radius;
    /**
     * 波浪圆的半径 (弃用，被控件半径-padding 取代)
     */
    private int flowRadius;
    /**
     * 波浪圆的颜色值
     */
    private int flowColor;
    /**
     * 绘制的电量文字颜色值
     */
    private int textColor;
    /**
     * 绘制的电量文字大小
     */
    private int textSize;
    /**
     * 波浪圆X轴偏移
     */
    private float FAI;
    /**
     * 波浪圆振幅
     */
    private float A;
    /**
     * 波浪圆的周期
     */
    private float W;
    /**
     * 波浪圆Y轴偏移
     */
    private float K;

    // 画图相关
    /**
     * 画笔(遮罩)
     */
    private Paint mPaint;
    /**
     * 画笔 绘制，电量
     */
    private Paint mTextPaint;
    /**
     *  绘制矩形波浪的Path
     */
    private Path mPath;
    /**
     * 设置抗锯齿
     */
    private PaintFlagsDrawFilter mDrawFilter;
    /**
     * 设置遮罩模式
     */
    private PorterDuffXfermode mPorterDuffXfermode;
    /**
     * 存放遮罩图片
     */
    private Bitmap mBitmap;
    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 控件高度
     */
    private int mHeight;
    /**
     * 中心点 横坐标
     */
    private int mCenterX;
    /**
     * 中心点 纵坐标
     */
    private int mCenterY;
    /**
     * 遮罩取源区域
     */
    private Rect mSrcRect;
    /**
     * 遮罩绘制目标区域
     */
    private Rect mDestRect;
    /**
     * 用于得到将要绘制的电量文字信息的区域
     */
    private Rect mTextRect;
    /**
     * 用于构建将要绘制的电量文字的字符串
     */
    private StringBuilder sb;

    /**
     * 暴漏出给外部调用的接口，用于监听
     */
    interface OnLevelChangeListener {
        int onLevelChange();
    }

    OnLevelChangeListener mOnLevelChangeListener;

    public void setOnLevelChangeListener(
            OnLevelChangeListener onLevelChangeListener) {
        mOnLevelChangeListener = onLevelChangeListener;
    }

    /**
     * 监听电池广播
     */
    private BroadcastReceiver mBatteryChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                LogI("onReceiver BatteryChanged");
                level = intent.getIntExtra("level", -1);
                int curScale = intent.getIntExtra("scale", -1);
                sb.delete(0, sb.length());
                sb.append("" + (int) level * 100 / curScale).append("%");
                //计算AK，以防在电量接近边界，最大最小的时候，绘图超过范围
                setA();
                //setK();
            }
        }
    };

    /**
     * 设置Y轴偏移K, 以防在电量接近边界，最大最小的时候，绘图超过范围（弃用）
     */
    private void setK() {
        // K:-5~+5 , level :0~100
        /*float k = 20f/100f;
        K = k*level;*/
    }

    /**
     * 设置振幅A，A的计算公式为一个二次方程，在level = 50时，振幅最大，在两端最小，在0和100临界值时为0
     */
    private void setA() {
        //y = ax;
        /*float k = 10f / 50f ;
        A = level<50? k*level:-k*(level-100);*/
        //y  = ax2+bx+c ( 0,3) (50,10 )( 100,3)
        if (level == 0 || level == 100) {
            A = 0;
        } else {
            float a = -7f / 2500;
            float b = -100 * a;
            float c = 3;
            A = (float) (a * Math.pow(level, 2) + b * level + c);
        }

        //LogI("A:"+A);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogI("onAttachedToWindow");
        //onAttachedToWindow会在onDraw前调用，注册监听电量变化的广播
        mContext.registerReceiver(mBatteryChangeReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogI("onDetachedFromWindow");
        //防止内存泄露，需要注销该广播
        mContext.unregisterReceiver(mBatteryChangeReceiver);
    }


    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LogI("inital func");
        init();
        // 获取我们在attr中定义的样式属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BatteryView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.BatteryView_batteryLevel:
                    level = a.getFloat(attr, 0);
                    break;
                case R.styleable.BatteryView_flowRadius:
                    flowRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.BatteryView_radius:
                    radius = a.getInt(attr, getWidth());
                    break;
                case R.styleable.BatteryView_textColor:
                    textColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.BatteryView_textSize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.BatteryView_flowViewColor:
                    flowColor = a.getColor(attr,0x88FFFFFF);
                    break;
            }
        }
        a.recycle();

        initPaint();

        //这个线程就是为了将波浪滚动起来，改变FAI，即X轴的偏移 ,稍后完善会用属性动画实现
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    changeFAI();
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }

            private void changeFAI() {
                FAI += 0.2;
            }
        }.start();
    }

    /**
     * 初始化参数函数
     */
    private void init() {
        level = 0;
        radius = getWidth();
        flowRadius = 150;
        flowColor = 0x88FFFFFF;
        FAI = 0;
        A = 9;
        W = 0.75f;
        K = 0;
    }
    /**
     * 初始化画笔相关参数
     */
    private void initPaint(){
        // 绘图参数初始化
        // 抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        //设置遮罩模式为，先绘制DST,再绘制SRC,取交集，留下DST
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        // 防抖动
        mPaint.setDither(true);
        // 开启图像过滤
        mPaint.setFilterBitmap(true);
        //绘制出的滚动波浪的颜色
        mPaint.setColor(flowColor);
        // 初始化遮罩图片
        mBitmap = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.battery_view_bg_round)).getBitmap();
        //初始化电量文字的Paint和Rect
        mTextPaint = new Paint();
        //设置文字样式，实心，居中，字体
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
		/*Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");
		mTextPaint.setTypeface(tf);*/
        mTextRect = new Rect();
        sb = new StringBuilder();
    }

    //private int temp = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        //LogI("flowRadius" + flowRadius + "  getPaddingRight:" + getPaddingRight());
        //if(temp++==0)
        //LogI("onDraw");
        // 从canvas层面抗锯齿
        canvas.setDrawFilter(mDrawFilter);
        //新开启一个图层，用于绘制波浪
        int sc = canvas.saveLayer(0 + getPaddingLeft(), 0 + getPaddingTop(), mWidth - getPaddingRight(), mHeight - getPaddingBottom(), null,
                Canvas.ALL_SAVE_FLAG);
        //清除上次的path
        mPath.reset();
/*		level = 0;
		setA();
		//setK();
*/
        //利用正弦曲线方程 Y = A sin(wx+FAI)+k，将计算后的坐标传入Path.quadTo()方法（绘制贝塞尔曲线）中,构建波浪曲线。
        for (int x = mDestRect.left; x < mDestRect.right; x++) {
            float y = (float) (A * Math.sin(Math.PI / 250 * W * x + FAI) + K + (mDestRect.bottom - mDestRect.top)
                    * 1.0f / 100 * (100 - level));
            if (x == mDestRect.left) {
                mPath.moveTo(x, y);
            }
            mPath.quadTo(x, y, x + 1, y);
        }
        //将Path连结起来,形成闭环
        mPath.lineTo(mDestRect.right, mDestRect.bottom);
        mPath.lineTo(mDestRect.left, mDestRect.bottom);
        mPath.close();
        //绘制波浪图形(图形上部是波浪，下部是矩形) （DST）
        canvas.drawPath(mPath, mPaint);
        //设置遮罩模式(图像混合模式)
        mPaint.setXfermode(mPorterDuffXfermode);
        //绘制用于遮罩的圆形 (SRC)
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);
        //设置遮罩模式为null
        mPaint.setXfermode(null);
        //将这个新图层绘制的bitmap，与上一个图层合并(显示)
        canvas.restoreToCount(sc);
        //canvas.restore(); 也可以

        //绘制电量文字的 字体大小和颜色 需要每次都重新设置 否则绘制不出文字，原因不明。
		/*mTextPaint.setTextSize(textSize);
		mTextPaint.setColor(textColor);
		mTextPaint.getTextBounds(sb.toString(), 0, sb.toString().length(), mTextRect);
		//绘制电池电量
		canvas.drawText(sb.toString(), mCenterX, mCenterY,mTextPaint);
		mTextPaint.setTextSize(24);*/

        postInvalidateDelayed(50);
    }

    /**
     * 通过 onSizeChanged方法 获取当前view的宽高，以及中心点坐标
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogI("onSizeChanged:" + getPaddingLeft());
        mWidth = w;
        mHeight = h;
        mCenterX = w / 2;
        mCenterY = h / 2;
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		/*mDestRect = new Rect(mCenterX - flowRadius, mCenterY - flowRadius,
				mCenterX + flowRadius, mCenterY + flowRadius);*/
        //这边遮罩在绘制时应该考虑View的padding，
        mDestRect = new Rect(0 + getPaddingLeft(), 0 + getPaddingTop(), mWidth - getPaddingRight(), mHeight - getPaddingBottom());
        //mDestRect = new Rect(mWidth/2-flowRadius, mHeight/2-flowRadius,mWidth/2+flowRadius ,  mHeight/2+flowRadius);
    }

    //为了适配wrap_content，需要针对AT_MOST测量一下自己的宽高，这里是将wrap_content设置成默认的150dp
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogI("onMeasure");
        setMeasuredDimension(measuredWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec));
    }

    private int measuredHeight(int heightMeasureSpec) {
        //初始化weight的默认值为150dp
        int measuredHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        //得到测量模式和测量值
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果是确定的值或Match_parent
        if (MeasureSpec.EXACTLY == specMode) {
            measuredHeight = specSize;
        } else { //AT_MOST：wrap_content  ,UNSPECIFIED 没用过。貌似子View想要多大就多大
            //取默认值和测量值的最小值
            measuredHeight = Math.min(specSize, measuredHeight);
        }
        return measuredHeight;
    }

    private int measuredWidth(int widthMeasureSpec) {
        int measuredWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.EXACTLY == specMode) {
            measuredWidth = specSize;
        } else {
            measuredWidth = Math.min(specSize, measuredWidth);
        }
        return measuredWidth;
    }

    private void LogI(String msg) {
        android.util.Log.i("zhangxutong/BatteryView", "" + msg);
    }
}
