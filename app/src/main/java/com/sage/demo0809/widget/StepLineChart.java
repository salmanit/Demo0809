package com.sage.demo0809.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.sage.demo0809.R;

/**
 * Created by Sage on 2017/3/6.
 */

public class StepLineChart extends View implements View.OnTouchListener {
    public StepLineChart(Context context) {
        super(context);
        initDefaultData();
    }

    public StepLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultData();
    }

    public StepLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultData();
    }
    public StepLineChart setToastTextColor(@ColorInt int color){
        paintText.setColor(color);
        return this;
    }
    public StepLineChart setAxisLineColor(@ColorInt int color){
        paintAxis.setColor(color);
        return this;
    }
    public StepLineChart setCurveLineColor(@ColorInt int color){
        paintCurve.setColor(color);
        return this;
    }
    public StepLineChart setDotPic(@DrawableRes int drawRes){
        dotbp = BitmapFactory.decodeResource(getResources(), drawRes);
        return this;
    }
    private Paint paintCurve;//曲线
    private Paint paintAxis;//坐标轴，竖线和横线
    private Paint paintPoint;//数据点
    private Paint paintText;//选中点弹框里文字
    private Paint paintToast;//选重点弹框背景
    private float[] data;
    private Point[] points;
    private int paddingTop, paddingRight, paddingBottom, paddingLeft;//4个padding
    private int dotRadius;//原点半径
    private Bitmap dotbp;//数据点的图片
    int padding;//默认的4边界

    private void initDefaultData() {
        padding = (int) (getResources().getDisplayMetrics().density * 10);
        paddingLeft = paddingRight = paddingTop = paddingBottom = padding;
        dotRadius = padding / 3;
        paddingLeft+=getPaddingLeft();
        paddingRight+=getPaddingRight();
        paddingTop+=getPaddingTop();
        paddingBottom+=getPaddingBottom();
        paintAxis = new Paint();
        paintAxis.setPathEffect(new DashPathEffect(new float[]{20, 5, 20, 5}, 1));
        paintAxis.setColor(Color.WHITE);

        paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotbp = BitmapFactory.decodeResource(getResources(), R.mipmap.dot_white);

        paintCurve = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCurve.setColor(Color.parseColor("#f4511e"));
        paintCurve.setStrokeWidth(5);
        paintCurve.setStyle(Paint.Style.STROKE);

        paintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.RED);
        paintText.setTextSize(40);
        paintToast=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintToast.setColor(Color.WHITE);
        paintToast.setStyle(Paint.Style.FILL_AND_STROKE);

        setOnTouchListener(this);
    }

    int maxIndex = 0;
    float maxFloat = 0;

    public void setData(float[] data) {
        if (noData(data)) {
            return;
        }
        this.data = data;
        points = new Point[data.length];
        for (int i = 0; i < points.length; i++) {
            if (data[i] > maxFloat) {
                maxFloat = data[i];
                maxIndex = i;
            }
        }
        if (maxFloat == 0) {
            maxFloat = 1;
        }

        lastClick=-1;
        hasLoadData = false;
        invalidate();
    }

    private boolean noData(float[] data) {
        return data == null || data.length <= 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        if (noData(data)) {
            return;
        }
        if (!hasLoadData) {
            hasLoadData = true;
            computeData(canvas);
        }
        //画竖线
        int intervalX = (getWidth() - paddingLeft - paddingRight) / (data.length - 1);
        for (int i = 0; i < data.length; i++) {
            canvas.drawLine(paddingLeft + intervalX * i, getHeight() - paddingBottom, paddingLeft + intervalX * i, paddingTop, paintAxis);
        }
        canvas.drawPath(pathTemp, paintCurve);
        for (int i = 0; i <= drawIndex; i++) {
            canvas.drawBitmap(dotbp, points[i].x - dotbp.getWidth() / 2,
                    points[i].y - dotbp.getHeight() / 2, paintPoint);
        }

        if(lastClick>=0&&lastClick<points.length){
            Path pathArrow=new Path();
            Point point=points[lastClick];
            int left=point.x-toastW/2;
            if(left<0){
                left=1;
            }
            int right=point.x+toastW/2;
            if(right>getWidth()){
                left=getWidth()-toastW-1;
            }
            int top=point.y-dotbp.getHeight()-toastH;
            if(top<0){//点的下方
                top=point.y+dotbp.getHeight();

                pathArrow.moveTo(point.x,point.y+dotbp.getHeight()/2);
                pathArrow.lineTo(point.x+10,top);
                pathArrow.lineTo(point.x-10,top);
                pathArrow.close();
            }else{
                //点的上方
                pathArrow.moveTo(point.x,point.y-dotbp.getHeight()/2);
                pathArrow.lineTo(point.x+10,top+toastH);
                pathArrow.lineTo(point.x-10,top+toastH);
                pathArrow.close();
            }
            rectToast.offsetTo(left,top);
            canvas.drawRoundRect(rectToast,10,10,paintToast);
            canvas.drawPath(pathArrow,paintToast);
            //画文字
            String drawText=""+data[lastClick];
            Rect bounds=new Rect();
            paintText.getTextBounds(drawText,0,drawText.length(),bounds);
            canvas.drawText(""+data[lastClick],rectToast.left+(toastW-bounds.width())/2
                    ,rectToast.bottom-(toastH-bounds.height())/2,paintText);
            //画三角

        }


    }
    int toastW=200;
    int toastH=80;
    RectF rectToast=new RectF(0,0,toastW,toastH);
    private void computeData(Canvas canvas) {

        int widthTotal = getWidth();
        int heightTotal = getHeight();
        int widthDraw = widthTotal - paddingLeft - paddingRight;
        int heightDraw = heightTotal - paddingTop - paddingBottom;
        int axisX = paddingLeft;
        int axisY = heightTotal - paddingBottom;
        int size = data.length;
        int intervalX = widthDraw / (size - 1);


        //画数据点
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(axisX + intervalX * i, (int) (axisY - heightDraw * data[i] / maxFloat));
//            canvas.drawBitmap(dotbp, points[i].x - dotbp.getWidth() / 2, points[i].y - dotbp.getHeight() / 2, paintPoint);
//            System.out.println("position=" + i + "===" + points[i].toString());
        }

        //画曲线
        pathCurve.reset();
        Point startp = points[0];
        Point endp = new Point();
        pathCurve.moveTo(startp.x, startp.y);
        for (int i = 0; i < points.length - 1; i++) {
            startp = points[i];
            endp = points[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            pathCurve.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }
//            canvas.drawPath(pathCurve, paintCurve);
    }

    private Canvas canvas;
    private boolean hasLoadData = false;
    Path pathCurve = new Path();//曲线路径
    ValueAnimator valueAnimator;
    PathMeasure pathMeasure;
    float[] position = new float[2];//当前曲线的坐标点
    int drawIndex = -1;
    Path pathTemp = new Path();//动画路径

    public void startMove() {
        if (canvas == null) {
            return;
        }
        pathMeasure = new PathMeasure(pathCurve, false);
        valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(2500);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                pathTemp.reset();
                pathMeasure.getSegment(0, value * pathMeasure.getLength(), pathTemp, true);
                pathMeasure.getPosTan(value * pathMeasure.getLength(), position, null);
//                System.out.println("========" + value + "===" + position[0] + "/" + position[1]);
                if (position[0] >= points[drawIndex + 1].x) {
                    drawIndex++;
                    System.out.println("index==" + drawIndex);
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                drawIndex = data.length - 1;
                lastClick=maxIndex;
                invalidate();
            }
        });

        valueAnimator.start();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return ifTouchPoint(event);
    }

    public int lastClick = -1;

    private boolean ifTouchPoint(MotionEvent event) {
        System.out.println("====" + event.getX() + ";;;" + event.getY());
        for (int i = 0; i < data.length; i++) {
            Point point = points[i];
            Region region = new Region(point.x - padding, point.y - padding, point.x + padding, point.y + padding);
            if (region.contains(((int) event.getX()), ((int) event.getY()))) {
                if (lastClick != i) {//刷新界面
                    invalidate();
                }
                System.out.println("contain==="+i);
                lastClick = i;
                return true;
            }
        }
        return false;
    }
}
