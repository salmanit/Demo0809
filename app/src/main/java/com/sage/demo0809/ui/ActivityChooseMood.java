package com.sage.demo0809.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.widget.BubbleLayout;
import com.sage.highlight.HighLight;

import pl.droidsonroids.gif.GifImageView;


public class ActivityChooseMood extends AppCompatActivity {
    HighLight highLight;
//    private TextView tv_light;
 BubbleLayout bubbleLayout;
    GifImageView iv_box;
    GifImageView iv_bubble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mood);
//        iv_box= (GifImageView) findViewById(R.id.iv_box);
//        iv_bubble= (GifImageView) findViewById(R.id.iv_bubble);

        bubbleLayout = (BubbleLayout) findViewById(R.id.bubble_layout);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!Thread.currentThread().isInterrupted()) {
//                    try {
//                        Thread.sleep(15);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    bubbleLayout.postInvalidate();
//                }
//            }
//        }).start();

//        tv_light= (TextView) findViewById(R.id.tv_light);
        MyLog.i("layout_textview=========="+R.layout.layout_textview);
        try {
            highLight=new HighLight(this)//
                    .intercept(false)
    //                .anchor(findViewById(R.id.id_container))//指你需要在哪个view上加一层透明的蒙版，如果不设置，默认为android.R.id.content
                    .addHighLight(R.id.tv1,1, R.layout.temp_layout,callback)
                    .addHighLight(R.id.tv2, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.tv3, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.tv4, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.tv5,1, R.layout.temp_layout,callback)
                    .addHighLight(R.id.btn_know,R.layout.layout_textview,callback)
            .setClickCallback(new HighLight.OnClickCallback() {
                @Override
                public void onClick() {
                    MyLog.i("=================");
                }
            })
            ;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    highLight.show();
                    findViewById(R.id.layout_i_know).setVisibility(View.VISIBLE);

                    ObjectAnimator animator=ObjectAnimator.ofFloat(findViewById(R.id.tv_light),"alpha", 1f,0.2f);
                    animator.setRepeatMode(ValueAnimator.REVERSE);
                    animator.setRepeatCount(ValueAnimator.INFINITE);
                    animator.setDuration(2000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
            },600000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF
                ,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        translateAnimation.setDuration(2000);
        findViewById(R.id.layout_word).startAnimation(translateAnimation);
//        ObjectAnimator animator=ObjectAnimator.ofFloat(findViewById(R.id.layout_word),"alpha", 1f,0f);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setDuration(2000);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.start();

        findViewById(R.id.btn_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highLight.remove();

            }
        });

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleLayout.reset();
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleLayout.stop();
            }
        });
    }



    private HighLight.OnPosCallback callback= new HighLight.OnPosCallback(){


        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.rightMargin = 0;
            marginInfo.topMargin = rectF.top -200;

            MyLog.i(rightMargin+"==="+bottomMargin+"===="+rectF.toShortString()+"======"+rectF.height()+"/"+rectF.width());
        }
    };

}
