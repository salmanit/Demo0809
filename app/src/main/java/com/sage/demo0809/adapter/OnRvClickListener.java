package com.sage.demo0809.adapter;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.sage.demo0809.MyLog;

/**
 * Created by Sage on 2016/8/7.
 */
public abstract class OnRvClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private View view;
    public OnRvClickListener(View view) {
        this.view=view;
        mGestureDetector = new GestureDetectorCompat(view.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        boolean result=mGestureDetector.onTouchEvent(e);
        System.out.println("===========onInterceptTouchEvent==="+result);
        return result;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        System.out.println("===========onTouchEvent");
        mGestureDetector.onTouchEvent(e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){
        System.out.println("===========onRequestDisallowInterceptTouchEvent="+disallowIntercept);
    }
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            System.out.println("=========onSingleTapUp");
            onClick();
//            view.onTouchEvent(e);
            return true;
        }

        //长点击事件
        @Override
        public void onLongPress(MotionEvent e) {
            onLongClick();
        }
    }

    public abstract void onClick();

    public void onLongClick() {
    }
}
