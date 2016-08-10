package com.sage.demo0809;

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

import com.sage.highlight.HighLight;


public class ActivityChooseMood extends AppCompatActivity {
    HighLight highLight;
//    private TextView tv_light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mood);
//        tv_light= (TextView) findViewById(R.id.tv_light);
        MyLog.i("layout_textview=========="+R.layout.layout_textview);
        try {
            highLight=new HighLight(this)//
                    .intercept(false)
    //                .anchor(findViewById(R.id.id_container))//指你需要在哪个view上加一层透明的蒙版，如果不设置，默认为android.R.id.content
                    .addHighLight(R.id.iv1,1, R.layout.temp_layout,callback)
                    .addHighLight(R.id.iv2, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.iv3, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.iv4, 1,R.layout.temp_layout,callback)
                    .addHighLight(R.id.iv5,1, R.layout.temp_layout,callback)
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
                        highLight.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    findViewById(R.id.layout_i_know).setVisibility(View.VISIBLE);

                    ObjectAnimator animator=ObjectAnimator.ofFloat(findViewById(R.id.tv_light),"alpha", 1f,0.2f);
                    animator.setRepeatMode(ValueAnimator.REVERSE);
                    animator.setRepeatCount(ValueAnimator.INFINITE);
                    animator.setDuration(2000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
            },2000);
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
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    View tv=findViewById(R.id.tv_light);
                    if(tv==null){
                        MyLog.i("tv_light=============null");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },1);

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
