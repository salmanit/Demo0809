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
 * 这里代码有点不好，应该移动画布到我们要画的坐标原点最好。懒得改了。。
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
    private String[] toast;//提示的数据
    private Point[] points;
    private int paddingTop, paddingRight, paddingBottom, paddingLeft;//4个padding
    private int dotRadius;//原点半径
    private Bitmap dotbp;//数据点的图片
    int padding;//默认的4边界

    private void initDefaultData() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
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
        stopMove();
        this.data = data;
        points = new Point[data.length];
        toast=new String[data.length];
        for (int i = 0; i < points.length; i++) {
            if (data[i] > maxFloat) {
                maxFloat = data[i];
                maxIndex = i;
            }
            toast[i]=getShowData(i);
        }
        if (maxFloat == 0) {
            maxFloat = 1;
        }
        drawIndex=-1;
        lastClick=-1;
//        if(getWidth()>0){
//            computeData();
//            hasLoadData=true;
//        } else {
//        }
        hasLoadData=false;
        System.out.println("===========first===="+hasLoadData);
//        invalidate();
    }

    private boolean noData(float[] data) {
        return data == null || data.length <= 1;
    }
    private int formatSize=0;//小数点后边保留几位，默认不保留，

    public StepLineChart setFormatSize( int formatSize) {
        this.formatSize = formatSize;
        return this;
    }
    private String getShowData(int position){
        float draw= data[position];
        if(formatSize==0){
            return Math.round(draw)+"";
        }
        int scale= (int) Math.pow(10,formatSize);
        return String.valueOf(((int)(draw*scale))*1.0f/scale);
    }
    private int offsetToast=10;//提示框和左右边界的距离
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        if (noData(data)) {
            return;
        }
        if (!hasLoadData) {
            hasLoadData = true;
            computeData();
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
            if(left<offsetToast){
                left=offsetToast;
            }
            int right=point.x+toastW/2;
            if(right>getWidth()-offsetToast){
                left=getWidth()-toastW-offsetToast;
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
            String drawText=toast[lastClick];
            Rect bounds=new Rect();
            paintText.getTextBounds(drawText,0,drawText.length(),bounds);
            canvas.drawText(drawText,rectToast.left+(toastW-bounds.width())/2
                    ,rectToast.bottom-(toastH-bounds.height())/2,paintText);
            //画三角
        }
    }

    int toastW=200;
    int toastH=80;
    RectF rectToast=new RectF(0,0,toastW,toastH);
    private void computeData() {
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
            System.out.println("=======computeData="+points[i]);
        }
        //画曲线
        pathCurve.reset();
        Point startp = points[0];
        pathCurve.moveTo(startp.x, startp.y);

        //这种没有下边的那种圆滑
//        Point endp = new Point();
//        for (int i = 0; i < points.length - 1; i++) {
//            startp = points[i];
//            endp = points[i + 1];
//            int wt = (startp.x + endp.x) / 2;
//            Point p3 = new Point();
//            Point p4 = new Point();
//            p3.y = startp.y;
//            p3.x = wt;
//            p4.y = endp.y;
//            p4.x = wt;
//            pathCurve.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
//        }

        float thisPointX;
        float thisPointY;
        float nextPointX;
        float nextPointY;
        float startDiffX;
        float startDiffY;
        float endDiffX;
        float endDiffY;
        float firstControlX;
        float firstControlY;
        float secondControlX;
        float secondControlY;
        float x;
        float y;
        for (int i = 0; i < points.length - 1; i++) {

            x = points[i].x;
            y = points[i].y;

            thisPointX = x;
            thisPointY = y;

            nextPointX = points[i+1].x;
            nextPointY = points[i+1].y;

            startDiffX = (nextPointX - points[si(points.length, i - 1)].x);
            startDiffY = (nextPointY - points[si(points.length, i - 1)].y);

            endDiffX = (points[si(points.length, i + 2)].x - thisPointX);
            endDiffY = (points[si(points.length, i + 2)].y - thisPointY);

            firstControlX = thisPointX + (0.15f * startDiffX);
            firstControlY = thisPointY + (0.15f * startDiffY);

            secondControlX = nextPointX - (0.15f * endDiffX);
            secondControlY = nextPointY - (0.15f * endDiffY);

            //Define outline
            pathCurve.cubicTo(firstControlX, firstControlY,
                    secondControlX, secondControlY, nextPointX, nextPointY);

        }
        pathMeasure = new PathMeasure(pathCurve, false);

    }
    /**
     * Credits: http://www.jayway.com/author/andersericsson/
     * Given an index in points, it will make sure the the returned index is
     * within the array.
     */
    private static int si(int setSize, int i) {

        if (i > setSize - 1)
            return setSize - 1;
        else if (i < 0)
            return 0;
        return i;
    }
    private Canvas canvas;
    private boolean hasLoadData = false;
    Path pathCurve = new Path();//曲线路径
    ValueAnimator valueAnimator;
    PathMeasure pathMeasure;
    float[] position = new float[2];//当前曲线的坐标点
    int drawIndex = -1;
    Path pathTemp = new Path();//动画路径
    private long durationTime=2000;

    public StepLineChart setDurationTime( long durationTime) {
        this.durationTime = durationTime;
        return this;
    }
    public void startMove() {
        if (canvas == null) {
            return;
        }

        valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(durationTime);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                pathTemp.reset();
                pathMeasure.getSegment(0, value * pathMeasure.getLength(), pathTemp, true);
                pathMeasure.getPosTan(value * pathMeasure.getLength(), position, null);
//                System.out.println("========" + value + "===" + position[0] + "/" + position[1]);
                if(points==null){
                    return;//某些手机可能这里执行了，points还没初始化完成
                }
                Point p=points[si(points.length,drawIndex + 1)];
                if(p==null){
                    hasLoadData=false;
                    drawIndex=-1;
                }else{
                    if (position[0] >= p.x) {
                        drawIndex++;
                    }
                    drawIndex=si(points.length,drawIndex);
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
        System.out.println("=======valueAnimator.start");
    }
    public void stopMove(){
        if(valueAnimator!=null){
            valueAnimator.cancel();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return ifTouchPoint(event);
    }

    public int lastClick = -1;

    private boolean ifTouchPoint(MotionEvent event) {
        if(data==null||points==null||(valueAnimator!=null&&valueAnimator.isRunning())){
            return false;
        }
        for (int i = 0; i < data.length; i++) {
            Point point = points[i];
            if(point==null){
                return false;
            }
            Region region = new Region(point.x - padding, point.y - padding, point.x + padding, point.y + padding);
            if (region.contains(((int) event.getX()), ((int) event.getY()))) {
                if (lastClick != i) {//刷新界面
                    invalidate();
                }
                lastClick = i;
                return true;
            }
        }
        return false;
    }
}
