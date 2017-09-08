package com.sage.demo0809.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.sage.demo0809.R;

/**
 * Created by Sage on 2017/9/4.
 * Description:
 */

public class TestSwipMenuLayout extends ViewGroup {

    public TestSwipMenuLayout(Context context) {
        this(context, null);
    }

    public TestSwipMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestSwipMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private static final String TAG = "zxt/SwipeMenuLayout";
    private int mScaleTouchSlop;
    private int mMaxVelocity;
    private int mPointerId;
    private int mHeight;
    private int mRightMenuWidths;
    private int mLimit;
    private View mContentView;
    private PointF mLastP;
    private boolean isUnMoved;
    private PointF mFirstP;
    private boolean isUserSwiped;
    private static SwipeMenuLayout mViewCache;
    private static boolean isTouching;
    private VelocityTracker mVelocityTracker;
    private Log LogUtils;
    private boolean isSwipeEnable;
    private boolean isIos;
    private boolean iosInterceptFlag;
    private boolean isLeftSwipe;
    private ValueAnimator mExpandAnim;
    private ValueAnimator mCloseAnim;
    private boolean isExpand;
    public boolean isSwipeEnable() {
        return this.isSwipeEnable;
    }

    public void setSwipeEnable(boolean swipeEnable) {
        this.isSwipeEnable = swipeEnable;
    }

    public boolean isIos() {
        return this.isIos;
    }

    public TestSwipMenuLayout setIos(boolean ios) {
        this.isIos = ios;
        return this;
    }

    public boolean isLeftSwipe() {
        return this.isLeftSwipe;
    }

    public TestSwipMenuLayout setLeftSwipe(boolean leftSwipe) {
        this.isLeftSwipe = leftSwipe;
        return this;
    }
    private void init(AttributeSet attrs, int defStyleAttr){
        mLastP=new PointF();
        mFirstP=new PointF();
        isUnMoved=true;
        mScaleTouchSlop= ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMaxVelocity=ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        this.isSwipeEnable = true;
        this.isIos = true;
        this.isLeftSwipe = true;

       TypedArray t= getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout,defStyleAttr,0);
        for(int i=0;i<t.getIndexCount();i++){
            int attr=t.getIndex(i);
            switch (attr){
                case R.styleable.SwipeMenuLayout_swipeEnable:
                    isSwipeEnable=t.getBoolean(attr,true);
                    continue;
                case R.styleable.SwipeMenuLayout_ios:
                    isIos=t.getBoolean(attr,true);
                    continue;
                case R.styleable.SwipeMenuLayout_leftSwipe:
                    isLeftSwipe=t.getBoolean(attr,true);
                    continue;
            }
        }

        t.recycle();
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount=getChildCount();
        int left=getPaddingLeft();

        for(int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            if(childView.getVisibility()==View.GONE){
                continue;
            }
            if(i==0||isLeftSwipe){
                childView.layout(left,getPaddingTop(),left+childView.getMeasuredWidth(),getPaddingTop()+childView.getMeasuredHeight());
                left+=childView.getMeasuredWidth();
            }else {
                childView.layout(left-childView.getMeasuredWidth(),getPaddingTop(),left,getPaddingTop()+childView.getMeasuredHeight());
                left-=childView.getMeasuredWidth();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
