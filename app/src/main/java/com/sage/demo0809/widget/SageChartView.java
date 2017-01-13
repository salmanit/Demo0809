package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sage on 2016/12/29.
 */

public class SageChartView extends View {
    public SageChartView(Context context) {
        super(context);
        initDefault();
    }

    public SageChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
    }

    public SageChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    private Paint paint;
    private void initDefault(){

    }

}
