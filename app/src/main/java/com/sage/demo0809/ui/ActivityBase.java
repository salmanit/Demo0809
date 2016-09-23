package com.sage.demo0809.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

/**
 * Created by Sage on 2016/9/23.
 */

public class ActivityBase extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置contentFeature,可使用切换动画
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition explode = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
//        getWindow().setEnterTransition(explode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void goNext(Class cla){
        startActivity(new Intent(this,cla));
//        Intent intent = new Intent(this, cla);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
