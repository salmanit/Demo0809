package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sage on 2016/11/27.
 */

public class CenterTransCircle extends View {
    public CenterTransCircle(Context context) {
        super(context);
        initDefaultPaint();
    }

    public CenterTransCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultPaint();
    }

    public CenterTransCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultPaint();
    }

    Paint paintCircle;
    private int colorCircle= Color.parseColor("#ff0000");
    private int colorBG= Color.parseColor("#ffffff");
    private void initDefaultPaint(){
        paintCircle=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setColor(colorCircle);
        paintCircle.setStrokeWidth(8);
        paintCircle.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorBG);
        RectF rect=new RectF(4,4,getWidth()-4,getHeight()-4);
        canvas.drawOval(rect,paintCircle);
    }

    @Override
    public void draw(Canvas canvas) {
        Path path=new Path();
//        path.addCircle(getWidth()/2,getHeight()/2, getWidth()/2-8, Path.Direction.CW);
        RectF rect=new RectF(8,8,getWidth()-8,getHeight()-8);
        path.addArc(rect,0,100);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        super.draw(canvas);
    }
}
