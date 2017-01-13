package com.sage.demo0809.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.sage.demo0809.R;

/**
 * Created by Sage on 2017/1/3.
 */

public class RelativeLayoutWithBg extends RelativeLayout {
    public RelativeLayoutWithBg(Context context) {
        super(context);
        initPaint(context, null);
    }

    public RelativeLayoutWithBg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    public RelativeLayoutWithBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context, attrs);
    }


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int arrowStart = 40;//距离起始点的位置
    private int arrowHeight = 20;//箭头的高度
    private int arrowWidth = 20;//箭头的宽度
    private int colorBG = Color.parseColor("#f4f4f4");
    private int radiusTopLeft;
    private int radiusTopRight;
    private int radiusBottomLeft;
    private int radiusBottomRight;
    private float[] radius4=new float[8];
    private int direction=1;
    private Rect defaultRect=new Rect();
    public int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RelativeLayout_with_bg_my);
            colorBG = typedArray.getColor(R.styleable.RelativeLayout_with_bg_my_sage_bg_color, Color.parseColor("#f4f4f4"));
            arrowStart = typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_arrow_start, dp2px(12));
            arrowWidth = typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_arrow_width, dp2px(10));
            arrowHeight = typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_arrow_height, dp2px(6));
            int radius=typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_radius_same,0);
            if(radius>0){
                radiusTopLeft=radiusTopRight=radiusBottomLeft=radiusBottomRight=radius;
            }else{
                radiusTopLeft=typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_radius_top_left,0);
                radiusTopRight=typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_radius_top_right,0);
                radiusBottomLeft=typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_radius_bottom_left,0);
                radiusBottomRight=typedArray.getDimensionPixelSize(R.styleable.RelativeLayout_with_bg_my_sage_radius_bottom_right,0);
            }

            direction=typedArray.getInt(R.styleable.RelativeLayout_with_bg_my_sage_arrow_direction,1);
            typedArray.recycle();
        }
        defaultRect=new Rect(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
        resetPadding();
        paint.setColor(colorBG);
        paint.setStyle(Paint.Style.FILL);
    }

    private void resetPadding(){
        switch (direction){
            case 0:
                setPadding(defaultRect.left+ arrowHeight, defaultRect.top , defaultRect.right, defaultRect.bottom);
                break;
            case 1:
                setPadding(defaultRect.left, defaultRect.top+ arrowHeight , defaultRect.right, defaultRect.bottom);
                break;
            case 2:
                setPadding(defaultRect.left, defaultRect.top , defaultRect.right+ arrowHeight, defaultRect.bottom);
                break;
            case 3:
                setPadding(defaultRect.left, defaultRect.top , defaultRect.right, defaultRect.bottom+ arrowHeight);
                break;
        }
    }

    public RelativeLayoutWithBg setDirection(int direction) {
        this.direction = direction;
        resetPadding();
        return this;
    }

    public RelativeLayoutWithBg setArrowStart(int arrowStart) {
        this.arrowStart = arrowStart;
        return this;
    }

    public RelativeLayoutWithBg setArrowHeight(int arrowHeight) {
        this.arrowHeight = arrowHeight;
        resetPadding();
        return this;
    }

    public RelativeLayoutWithBg setArrowWidth(int arrowWidth) {
        this.arrowWidth = arrowWidth;
        return this;
    }

    public RelativeLayoutWithBg setColorBG(int colorBG) {
        this.colorBG = colorBG;
        paint.setColor(colorBG);
        return this;
    }

    public RelativeLayoutWithBg setRadiusSame(int radius){
        radiusTopLeft=radiusTopRight=radiusBottomLeft=radiusBottomRight=radius;
        return this;
    }

    public RelativeLayoutWithBg setRadiusTopLeft(int radiusTopLeft) {
        this.radiusTopLeft = radiusTopLeft;
        return this;
    }

    public RelativeLayoutWithBg setRadiusTopRight(int radiusTopRight) {
        this.radiusTopRight = radiusTopRight;
        return this;
    }

    public RelativeLayoutWithBg setRadiusBottomLeft(int radiusBottomLeft) {
        this.radiusBottomLeft = radiusBottomLeft;
        return this;
    }

    public RelativeLayoutWithBg setRadiusBottomRight(int radiusBottomRight) {
        this.radiusBottomRight = radiusBottomRight;
        return this;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        radius4[0]=radiusTopLeft;
        radius4[1]=radiusTopLeft;
        radius4[2]=radiusTopRight;
        radius4[3]=radiusTopRight;
        radius4[4]=radiusBottomLeft;
        radius4[5]=radiusBottomLeft;
        radius4[6]=radiusBottomRight;
        radius4[7]=radiusBottomRight;
        if(radiusTopLeft> arrowStart){
            arrowStart =radiusTopLeft;
        }
        Path path;
        switch (direction){
            case 0:
                path=createPathLeft();
                break;
            case 1:
                path=createPathTop();
                break;
            case 2:
                path=createPathRight();
                break;
            case 3:
                path=createPathBottom();
                break;
            default:
                path=createPathTop();
                break;
        }
        canvas.drawPath(path,paint);
        super.dispatchDraw(canvas);
    }


    //箭头在左侧
    private Path createPathLeft(){
        if(arrowStart>=getHeight()){
            arrowStart=radiusTopLeft;
        }
        Path path = new Path();
        path.moveTo(arrowHeight,arrowStart);
        path.lineTo(0,arrowStart + arrowWidth / 2);
        path.lineTo(arrowHeight,arrowStart + arrowWidth);
        RectF rectF=new RectF(arrowHeight,0,getWidth(),getHeight());
        path.addRoundRect(rectF,radius4, Path.Direction.CW);
        return path;
    }
    private Path createPathTop(){
        if(arrowStart>=getWidth()){
            arrowStart=radiusTopLeft;
        }
        Path path = new Path();
        path.moveTo(arrowStart,arrowHeight);
        path.lineTo(arrowStart + arrowWidth / 2, 0);
        path.lineTo(arrowStart + arrowWidth, arrowHeight);
        RectF rectF=new RectF(0,arrowHeight,getWidth(),getHeight());
        path.addRoundRect(rectF,radius4, Path.Direction.CW);
        return path;
    }
    private Path createPathRight(){
        if(arrowStart>=getHeight()){
            arrowStart=radiusTopRight;
        }
        Path path = new Path();
        path.moveTo(getWidth()-arrowHeight,arrowStart);
        path.lineTo(getWidth(),arrowStart + arrowWidth / 2);
        path.lineTo(getWidth()-arrowHeight, arrowStart+arrowWidth);
        RectF rectF=new RectF(0,0,getWidth()-arrowHeight,getHeight());
        path.addRoundRect(rectF,radius4, Path.Direction.CW);
        return path;
    }
    private Path createPathBottom(){
        if(arrowStart>=getWidth()){
            arrowStart=radiusBottomLeft;
        }
        Path path = new Path();
        path.moveTo(arrowStart,getHeight()-arrowHeight);
        path.lineTo(arrowStart + arrowWidth / 2, getHeight());
        path.lineTo(arrowStart + arrowWidth, getHeight()-arrowHeight);
        RectF rectF=new RectF(0,0,getWidth(),getHeight()-arrowHeight);
        path.addRoundRect(rectF,radius4, Path.Direction.CW);
        return path;
    }
}
