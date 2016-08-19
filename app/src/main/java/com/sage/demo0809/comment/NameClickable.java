package com.sage.demo0809.comment;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.sage.demo0809.R;


/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public class NameClickable extends ClickableSpan implements View.OnClickListener {
    private final ISpanClick mListener;
    private int mPosition;
    private Context context;
    public NameClickable(ISpanClick l, int position) {
        mListener = l;
        mPosition = position;
    }

    @Override
    public void onClick(View widget) {
        if(widget!=null){
            context=widget.getContext();
        }
        mListener.onClick(mPosition);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        if(context==null){
            return;
        }
        int colorValue = context.getResources().getColor(
                R.color.color_8290AF);
        ds.setColor(colorValue);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
