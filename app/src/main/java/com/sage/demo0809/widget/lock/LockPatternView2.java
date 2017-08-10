package com.sage.demo0809.widget.lock;

/**
 * Created by Sage on 2017/8/10.
 * Description:
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sage.demo0809.R;

import java.util.ArrayList;
import java.util.List;


public class LockPatternView2 extends View {
    //判断线的状态
    private static boolean isLineState = true;
    //判断点是否被实例化了
    private static boolean isInitPoint = false;
    //判断手指是否离开屏幕
    private static boolean isFinish = false;
    //判断手指点击屏幕时是否选中了九宫格中的点
    private static boolean isSelect = false;
    // 创建MyPoint的数组
    // 声明屏幕的宽和高
    private int mScreenHeight;
    private int mScreenWidth;
    // 声明点线的图片的半径
    private float mPointRadius;
    // 声明鼠标移动的x，y坐标
    private float mMoveX, mMoveY;
    // 声明屏幕上的宽和高的偏移量
    private int mScreenHeightOffSet = 0;
    private int mScreenWidthOffSet = 0;
    // 创建一个画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);//选中后中心的填充点
    // 创建MyPoint的列表
    private List<PointLock> mPointList = new ArrayList<PointLock>();
    // 实例化鼠标点
    private PointLock mMousePoint = new PointLock();
    // 用获取从activity中传过来的密码字符串
    private String mPassword = "";

    private OnLockListener mListener;

    public LockPatternView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LockPatternView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockPatternView2(Context context) {
        super(context);
    }

    PointLock[] pointLocks = new PointLock[9];

    /**
     * 实例化九宫格中所有点和所有的资源图片
     */
    private void initPoint() {
        // 获取View的宽高
        mScreenWidth = getWidth();
        mScreenHeight = getHeight();
        if (mScreenHeight > mScreenWidth) {
            // 获取y轴上的偏移量
            mScreenHeightOffSet = (mScreenHeight - mScreenWidth) / 2;
            // 将屏幕高的变量设置成与宽相等，目的是为了new Point(x,y)时方便操作
            mScreenHeight = mScreenWidth;
        } else {
            // 获取x轴上的偏移量
            mScreenWidthOffSet = (mScreenWidth - mScreenHeight) / 2;
            // 将屏幕宽的变量设置成与高相等，目的是为了new Point(x,y)时方便操作
            mScreenWidth = mScreenHeight;
        }

        mPointRadius = mScreenWidth / 12;//宽或高四等分，去中间的为中心点，完事两点之间为3个半径

        mPaintFill.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.STROKE);
        /**
         * 开始实例化九宫格中点
         */
        for (int i = 0; i < pointLocks.length; i++) {
            pointLocks[i] = new PointLock(mScreenWidthOffSet + mScreenWidth * (i % 3 + 1) / 4,
                    mScreenHeightOffSet + mScreenHeight * (i / 3 + 1) / 4);
            pointLocks[i].setIndex("" + (i + 1));
            pointLocks[i].setState(PointLock.BITMAP_NORMAL);
        }

        // 将isInitPoint设置为true
        isInitPoint = true;
    }

    /**
     * 画点和画线
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitPoint) {
            initPoint(); // 先初始化
        }
        // 开始画线
        if (mPointList.size() > 0) {
            PointLock b = null;
            PointLock a = mPointList.get(0);
            for (int i = 1; i < mPointList.size(); i++) {
                b = mPointList.get(i);
                canvasLine(a, b, canvas);
                a = b;
            }
            if (!isFinish) {
                canvasLine(a, mMousePoint, canvas);
            }

            for (int i = 0; i < mPointList.size(); i++) {
                PointLock p = mPointList.get(i);
                int color = isLineState ? colorPress : colorError;
                mPaintFill.setColor(color);
                canvas.drawCircle(p.getX(), p.getY(), mPointRadius / 3f, mPaintFill);
            }

        }

        canvasPoint(canvas); // 开始画点
    }

    float strokeWidth = 4;//圆圈的外围线条宽度
    int colorNormal = Color.parseColor("#ff5802");
    int colorPress = Color.parseColor("#ff5802");
    int colorError = Color.parseColor("#44ff5802");

    /**
     * 画点
     */
    private void canvasPoint(Canvas canvas) {

        for (int i = 0; i < pointLocks.length; i++) {
            PointLock p = pointLocks[i];
            int color = colorNormal;
            if (p.getState() == PointLock.BITMAP_PRESS) {
                color = colorPress;
            } else if (p.getState() == PointLock.BITMAP_ERROR) {
                color = colorError;
            }
            mPaint.setColor(color);
            mPaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(p.getX(), p.getY(), mPointRadius, mPaint);
        }
    }

    /**
     * 画连接线
     *
     * @param a      起始点
     * @param b      目的点
     * @param canvas 画布
     */
    private void canvasLine(PointLock a, PointLock b, Canvas canvas) {
        float abInstance = (float) Math.sqrt(
                (a.getX() - b.getX()) * (a.getX() - b.getX())
                        + (a.getY() - b.getY()) * (a.getY() - b.getY())

        );
        float degree = getDegrees(a, b);
        canvas.save();
        canvas.translate(a.getX(), a.getY());
        canvas.rotate(degree, 0, 0);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(isLineState ? colorPress : colorError);
        canvas.drawLine(mPointRadius, 0, abInstance - mPointRadius, 0, mPaint);
        canvas.restore();
        //save()和restore()的作用就是将save之后画布进行了平移缩放等操作之后通过restore还原，而不用在反向平移缩放
    }

    /**
     * 手指点击手机屏幕
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMoveX = event.getX();
        mMoveY = event.getY();
        // 设置移动点的坐标
        mMousePoint.setX(mMoveX);
        mMousePoint.setY(mMoveY);
        PointLock mPoint = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLineState = true;
                isFinish = false;
                // 每次点击时就会将pointList中元素设置转化成正常状态
                for (int i = 0; i < mPointList.size(); i++) {
                    mPointList.get(i).setState(PointLock.BITMAP_NORMAL);
                }
                // 将pointList中的元素清除掉
                mPointList.clear();
                // 判断是否点中了九宫格中的点
                mPoint = getIsSelectedPoint(mMoveX, mMoveY);
                if (mPoint != null) {
                    isSelect = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelect) {
                    mPoint = getIsSelectedPoint(mMoveX, mMoveY);
                }

                break;
            case MotionEvent.ACTION_UP:
                isFinish = true;
                isSelect = false;
                // 规定至少要有4个点被连线才有可能是正确
                // 其他种情况都是错误的
                if (mPointList.size() >= 4) {// 正确情况
                    for (int j = 0; j < mPointList.size(); j++) {
                        mPassword += mPointList.get(j).getIndex();
                    }
                    if (mListener != null) {
                        //将连线后得到的密码传给activity
                        mListener.getStringPassword(mPassword);
                        mPassword = "";
                        //经过activity判断传过来是否正确
                        if (mListener.isPassword()) {
                            for (int i = 0; i < mPointList.size(); i++) {
                                mPointList.get(i).setState(PointLock.BITMAP_PRESS);
                            }
                        } else {
                            for (int i = 0; i < mPointList.size(); i++) {
                                mPointList.get(i).setState(PointLock.BITMAP_ERROR);
                            }
                            isLineState = false;
                        }
                    }
                    // 错误情况
                } else if (mPointList.size() < 4 && mPointList.size() > 1) {
                    for (int i = 0; i < mPointList.size(); i++) {
                        mPointList.get(i).setState(PointLock.BITMAP_ERROR);
                    }
                    isLineState = false;
                    // 如果只有一个点被点中时为正常情况
                } else if (mPointList.size() == 1) {
                    for (int i = 0; i < mPointList.size(); i++) {
                        mPointList.get(i).setState(PointLock.BITMAP_NORMAL);
                    }
                }
                break;

        }
        // 将mPoint添加到pointList中
        if (isSelect && mPoint != null) {
            if (mPoint.getState() == PointLock.BITMAP_NORMAL) {
                mPoint.setState(PointLock.BITMAP_PRESS);
                mPointList.add(mPoint);
            }
        }
        // 每次发生OnTouchEvent()后都刷新View
        postInvalidate();
        return true;
    }

    /**
     * 判断九宫格中的某个点是否被点中了，或者某个点能否被连线
     */
    private PointLock getIsSelectedPoint(float moveX, float moveY) {
        PointLock myPoint = null;
        for (int i = 0; i < pointLocks.length; i++) {
            if (pointLocks[i].isWith(pointLocks[i], moveX, moveY, mPointRadius)) {
                myPoint = pointLocks[i];
            }
        }
        return myPoint;
    }

    public  float getDegrees(PointLock a, PointLock b) {
        float degrees = 0;
        float ax = a.getX();
        float ay = a.getY();
        float bx = b.getX();
        float by = b.getY();

        if (ax == bx) {
            if (by > ay) {
                degrees = 90;
            } else {
                degrees = 270;
            }
        } else if (by == ay) {
            if (ax > bx) {
                degrees = 180;
            } else {
                degrees = 0;
            }
        } else {
            if (ax > bx) {
                if (ay > by) { // 第三象限
                    degrees = 180 + (float) (Math.atan2(ay - by, ax - bx) * 180 / Math.PI);
                } else { // 第二象限
                    degrees = 180 - (float) (Math.atan2(by - ay, ax - bx) * 180 / Math.PI);
                }
            } else {
                if (ay > by) { // 第四象限
                    degrees = 360 - (float) (Math.atan2(ay - by, bx - ax) * 180 / Math.PI);
                } else { // 第一象限
                    degrees = (float) (Math.atan2(by - ay, bx - ax) * 180 / Math.PI);
                }
            }
        }
        return degrees;
    }



    public interface OnLockListener {
        public void getStringPassword(String password);

        public boolean isPassword();
    }

    public void setLockListener(OnLockListener listener) {
        this.mListener = listener;
    }

}