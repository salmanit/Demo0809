package com.sage.demo0809.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.sage.demo0809.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/12/16.
 */

public class ActivityTest2 extends ActivityBase {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_scale)
    ImageView ivScale;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.iv_system)
    ImageView ivSystem;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

        rb1.setTextColor(createColor(Color.RED,Color.GRAY));
        rb2.setTextColor(createColor(Color.BLUE,Color.BLACK));
    }

    private ColorStateList createColor(int colorCheck, int colorNormal) {
        int[] colors = new int[]{colorCheck, colorNormal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        return colorStateList;
    }

    private void showAlert() {
        new AlertDialog.Builder(this).setView(R.layout.bottom_view)
                .setNegativeButton("cancel", null)
                .setPositiveButton("click", null).show();
    }

    boolean show;

    @OnClick(R.id.tv)
    public void onClick() {
        if (show) {
            try {
                showAlert();
                hideImageCircular();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                revealImageCircular();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        show = !show;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void hideImageCircular() {
        int x = (int) ivScale.getWidth();
        int y = (int) ivScale.getHeight();
        int radius = (int) Math.sqrt(x * x / 4 + y * y / 4);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(ivScale, x / 2, y / 2, radius, 0);

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivScale.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
        Animation animation = AnimationUtils.makeOutAnimation(this, false);
        animation.setDuration(2000);
        ivSystem.startAnimation(animation);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealImageCircular() {
        int x = (int) ivScale.getWidth();
        int y = (int) ivScale.getHeight();
        int radius = (int) Math.sqrt(x * x / 4 + y * y / 4);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(ivScale, x / 2, y / 2, 0, radius);

        anim.setDuration(1000);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ivScale.setVisibility(View.VISIBLE);
            }
        });

        anim.start();

        Animation animation = AnimationUtils.makeInAnimation(this, false);
        animation.setDuration(2000);
        ivSystem.startAnimation(animation);
    }



}
