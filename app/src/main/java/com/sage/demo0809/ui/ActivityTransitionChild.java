package com.sage.demo0809.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.custom.MyTransition;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/12/2.
 */

public class ActivityTransitionChild extends ActivityBase {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_child);
        ButterKnife.bind(this);
        initMyToolbar();
        tvContent.setText(getIntent().getStringExtra("msg"));

//        if (Build.VERSION.SDK_INT >= 21)
//            executeTransition();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void executeTransition() {
        MyTransition transition = new MyTransition();
//        transition.setPositionDuration(300);
//        transition.setSizeDuration(300);
//        transition.setPositionInterpolator(new FastOutLinearInInterpolator());
//        transition.setSizeInterpolator(new FastOutSlowInInterpolator());
        transition.addTarget("hello");
        getWindow().setSharedElementEnterTransition(transition);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
