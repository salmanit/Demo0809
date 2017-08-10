package com.sage.demo0809.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.sage.demo0809.R;

/**
 * Created by Sage on 2017/3/29.
 */

public class RelativeLayoutCustomState extends RelativeLayout{
    public RelativeLayoutCustomState(Context context) {
        super(context);
    }

    public RelativeLayoutCustomState(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLayoutCustomState(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final int[] STATE_MESSAGE_READED = { R.attr.state_message_read };
    private boolean mMessgeReaded = false;

    public void setMessageReaded(boolean readed)
    {
        if (this.mMessgeReaded != readed)
        {
            mMessgeReaded = readed;
            refreshDrawableState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mMessgeReaded)
        {
            final int[] drawableState = super
                    .onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_READED);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
