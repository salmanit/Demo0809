package com.sage.demo0809.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sage on 2017/3/21.
 */

public class StepPillarChart extends View {
    public StepPillarChart(Context context) {
        super(context);
        initPaint();
    }

    public StepPillarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StepPillarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private int paddingTop, paddingRight, paddingBottom, paddingLeft;//4个padding
    int padding;//默认的4边界
    private Paint paintPillar;//柱子
    private Paint paintText;//柱子下的文字
    private float[] data;
    private Point[] points;
    private void initPaint(){
        padding = (int) (getResources().getDisplayMetrics().density * 10);
        paddingLeft = paddingRight = paddingTop = paddingBottom = padding;
        paddingBottom=padding*2;
        paddingLeft+=getPaddingLeft();
        paddingRight+=getPaddingRight();
        paddingTop+=getPaddingTop();
        paddingBottom+=getPaddingBottom();
        paintPillar=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPillar.setStyle(Paint.Style.FILL);
        paintPillar.setColor(Color.parseColor("#7fbe25"));
        paintPillar.setStrokeWidth(20);
//        paintPillar.setStrokeCap(Paint.Cap.ROUND);//线的两头是圆弧状的

        paintText=new Paint();
        paintText.setColor(Color.parseColor("#999999"));
        paintText.setTextSize(padding*1.3f);
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

        invalidate();
    }

    private boolean noData(float[] data) {
        return data == null || data.length <= 1;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (noData(data)) {
            return;
        }
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
            LinearGradient gradient=new LinearGradient(points[i].x, axisY,points[i].x,points[i].y,
                    Color.parseColor("#00ABF7"),Color.parseColor("#00E521"),Shader.TileMode.CLAMP);
            paintPillar.setShader(gradient);


            //画下边的文字
            String draw=(int)data[i]+"";
            Rect bound=new Rect();
            paintText.getTextBounds(draw,0,draw.length(),bound);
            canvas.drawText(draw,points[i].x-bound.width()/2,axisY+padding/2+bound.height(),paintText);

            if(factor==0){
                continue;
            }
            //因为需求是上边带圆角，下边不带的，所以这样弄了。
            points[i].y= (int) (axisY-(axisY-points[i].y)*factor);
            Path path=new Path();
            path.addRect(new RectF(points[i].x-10,points[i].y,points[i].x+10,axisY), Path.Direction.CW);
            path.addArc(new RectF(points[i].x-10,points[i].y-10,points[i].x+10,points[i].y+10),180,180);
            canvas.drawPath(path,paintPillar);
            //如果没要求简单画条线最省事。
//            Line(points[i].x, axisY,points[i].x,points[i].y,paintPillar);
        }
    }

    ValueAnimator animator;
    float factor;
    public void startMove(){
        animator=ValueAnimator.ofFloat(0,1);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                factor= (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.start();
    }
}
