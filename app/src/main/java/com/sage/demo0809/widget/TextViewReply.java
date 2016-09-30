package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.bean.SimpleUser;

/**
 * Created by Sage on 2016/9/27.
 */

public class TextViewReply extends TextView {
    public TextViewReply(Context context) {
        super(context);
    }

    public TextViewReply(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextViewReply(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setContent(final SimpleUser userFrom, final SimpleUser userTo, String content){
        ClickableSpan clickableSpanFrom=new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(replyClickListener!=null){
                    replyClickListener.spanClick(userFrom);
                }
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.RED);
            }
        };
        ClickableSpan clickableSpanTo=new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(replyClickListener!=null){
                    replyClickListener.spanClick(userTo);
                }
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };

        SpannableString spanFrom=new SpannableString(userFrom.userName);
        spanFrom.setSpan(clickableSpanFrom,0,userFrom.userName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString spanTo=new SpannableString(userTo.userName);
        spanTo.setSpan(clickableSpanTo,0,userTo.userName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        setText("");
        append(spanFrom);
        append("回复");
        append(spanTo);
        append(":"+content);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public TextViewReplyClickListener replyClickListener;

    public void setReplyClickListener(TextViewReplyClickListener replyClickListener) {
        this.replyClickListener = replyClickListener;
    }

    public interface  TextViewReplyClickListener{
        public void spanClick(SimpleUser simpleUser);
    }
}
