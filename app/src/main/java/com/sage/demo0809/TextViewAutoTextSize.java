package com.sage.demo0809;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sage on 2016/8/17.
 */
public class TextViewAutoTextSize extends TextView {
    public TextViewAutoTextSize(Context context) {
        super(context);
    }

    public TextViewAutoTextSize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewAutoTextSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int count;
    @Override
    protected void onDraw(Canvas canvas) {
        String content=getText().toString();
        if(!TextUtils.isEmpty(content))
            count=content.length();
        float paintSize=0;
        if(getWidth()>0&&count>0){
            paintSize=getWidth()/count;
            MyLog.i("paint size=="+paintSize+"=="+getResources().getDisplayMetrics().density);
            setTextSize(paintSize/getResources().getDisplayMetrics().density);
        }

        super.onDraw(canvas);
    }


}
