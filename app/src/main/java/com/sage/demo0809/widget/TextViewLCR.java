package com.sage.demo0809.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sage.demo0809.R;


/**
 * Created by Sage on 2015/9/28.
 * 不考虑padding的，感觉考虑padding有点麻烦，就不管了，所以这个控件别加padding额
 */
public class TextViewLCR extends AppCompatTextView {
    public TextViewLCR(Context context) {
        super(context);
    }

    public TextViewLCR(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyTextViewLCR_style,0,0);
        words_count=a.getInteger(R.styleable.MyTextViewLCR_style_words_count,-1);
        a.recycle();
    }

    public TextViewLCR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyTextViewLCR_style,0,0);
        words_count=a.getInteger(R.styleable.MyTextViewLCR_style_words_count,-1);
        a.recycle();
    }


    public int words_count=-1;
    public int width;
    public void setWords_count(int words_count) {
        this.words_count = words_count;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text=getText().toString();
        if(TextUtils.isEmpty(text)){
            super.onDraw(canvas);
            return;
        }
        getPaint().setColor(getCurrentTextColor());
        Rect bounds=new Rect();
        getPaint().getTextBounds("您",0,1,bounds);
        int oneWord=bounds.right-bounds.left;
        if(words_count>0){
            width=oneWord*words_count;
            setWidth(width);
        }else{
            width=getWidth();
        }
        float part=width/text.length();
        float y=(getHeight()+bounds.bottom-bounds.top)/2;

        for(int i=0;i<text.length();i++){
            if(i==0){
                canvas.drawText(text.substring(i,i+1),0,y,getPaint());
            }else if(i==text.length()-1){
                canvas.drawText(text.substring(i,i+1),width-oneWord,y,getPaint());
            }else{
                canvas.drawText(text.substring(i,i+1),i*part+(part-oneWord)/2,y,getPaint());
            }
        }
    }


}
