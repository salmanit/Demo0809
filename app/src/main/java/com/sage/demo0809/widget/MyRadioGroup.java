package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.sage.demo0809.R;

/**
 * Created by Sage on 2016/9/21.
 */

public class MyRadioGroup extends RadioGroup {
    private Paint paintBorder;

    public MyRadioGroup(Context context) {
        super(context);
        initPaint();
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    int dp10;
    int dp24;
    private void initPaint(){
        dp10=getResources().getDimensionPixelSize(R.dimen.dp10);
        dp24=getResources().getDimensionPixelSize(R.dimen.dp24);
        paintBorder = new Paint();
        paintBorder.setColor(Color.BLACK);
        paintBorder.setAntiAlias(true);
        this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 5.0f, Color.BLACK);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(0,0,getWidth(),9,paintBorder);
        canvas.drawCircle(getWidth()/2,dp24,dp10*3,paintBorder);
    }
}
