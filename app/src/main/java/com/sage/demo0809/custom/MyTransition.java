package com.sage.demo0809.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

import com.sage.demo0809.MyLog;

/**
 * Created by Sage on 2016/12/2.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyTransition extends Transition {

    public static final String TOP = "top";
    public static final String HEIGHT = "height";
    private long mPositionDuration=1000;
    private long mSizeDuration=1000;
    private TimeInterpolator mPositionInterpolator=new TimeInterpolator() {
        @Override
        public float getInterpolation(float input) {
            return input;
        }
    };
    private TimeInterpolator mSizeInterpolator=new TimeInterpolator() {
        @Override
        public float getInterpolation(float input) {
            return input;
        }
    };

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);
        transitionValues.values.put(TOP, rect.top);
        transitionValues.values.put(HEIGHT, rect.height());
        MyLog.i("captureStartValues===" + rect.toString());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(TOP, 0);
        transitionValues.values.put(HEIGHT, transitionValues.view.getHeight());

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        final View endView = endValues.view;

        final int startTop = (int) startValues.values.get(TOP);
        final int startHeight = (int) startValues.values.get(HEIGHT);
        final int endTop = (int) endValues.values.get(TOP);
        final int endHeight = (int) endValues.values.get(HEIGHT);

        ViewCompat.setTranslationY(endView, startTop);
        endView.getLayoutParams().height = startHeight;
        endView.requestLayout();

        ValueAnimator positionAnimator = ValueAnimator.ofInt(startTop, endTop);
        if (mPositionDuration > 0) {
            positionAnimator.setDuration(mPositionDuration);
        }
        positionAnimator.setInterpolator(mPositionInterpolator);

        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationY(endView, current);
            }
        });

        ValueAnimator sizeAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        if (mSizeDuration > 0) {
            sizeAnimator.setDuration(mSizeDuration);
        }
        sizeAnimator.setInterpolator(mSizeInterpolator);

        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                endView.getLayoutParams().height = current;
                endView.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(sizeAnimator).after(positionAnimator);

        return set;
    }
}
