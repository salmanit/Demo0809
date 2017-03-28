package com.sage.demo0809.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sage on 2017/3/28.
 */

public class AnchorView extends View {
    public AnchorView(Context context) {
        super(context);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (0.667f * getMeasuredWidth()), getMeasuredHeight());
    }
}
