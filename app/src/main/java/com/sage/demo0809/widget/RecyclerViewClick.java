package com.sage.demo0809.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Sage on 2016/12/27.
 */

public class RecyclerViewClick extends RecyclerView {
    public RecyclerViewClick(Context context) {
        super(context);
    }

    public RecyclerViewClick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewClick(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        return false;
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return false;
//    }
}
