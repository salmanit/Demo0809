package com.sage.demo0809.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Sage on 2016/4/26.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;
    private boolean withHeader;
    private int left_insert;/**画水平线的时候左偏移量*/
    // 画笔
    private Paint paint;
    public SpaceItemDecoration(int space) {
        this(space,false);
    }
    public SpaceItemDecoration(int space, boolean withHeader) {
        this.space = space;
        this.withHeader = withHeader;
        paint=new Paint();
        paint.setColor(Color.parseColor("#f1f1f1"));
    }
    public SpaceItemDecoration noColor(){
        paint.setColor(Color.TRANSPARENT);
        return this;
    }
    public SpaceItemDecoration setDecorationColor(int color){
        paint.setColor(color);
        return this;
    }

    public SpaceItemDecoration setLeft_insert(int left_insert) {
        this.left_insert = left_insert;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager=parent.getLayoutManager();
        if(manager==null){
            return;
        }
        if(manager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager= (LinearLayoutManager) manager;
            int position=parent.getChildPosition(view);
            if(position==0){
                return;
            }
            if(withHeader){
                if(position==1){
                    return;
                }
            }
            switch (linearLayoutManager.getOrientation()){
                case LinearLayoutManager.HORIZONTAL:
                    outRect.left=space;
                    break;
                case LinearLayoutManager.VERTICAL:
                    outRect.top = space;
                    break;
            }
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager=parent.getLayoutManager();
        if(manager==null){
            return;
        }
        if(manager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager= (LinearLayoutManager) manager;
            switch (linearLayoutManager.getOrientation()){
                case LinearLayoutManager.HORIZONTAL:
                    drawHorizontal(c,parent);
                    break;
                case LinearLayoutManager.VERTICAL:
                    drawVertical(c,parent);
                    break;
            }
        }
    }

    /**
     * 设置分割线颜色
     * @param color 颜色
     */
    public SpaceItemDecoration setColor(int color) {
        paint.setColor(color);
        return this;
    }

    // 绘制垂直分割线
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + space;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    // 绘制水平分割线
    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if(withHeader&&i==0){
                continue;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + space;

            c.drawRect(left+left_insert, top, right, bottom, paint);
        }
    }
}