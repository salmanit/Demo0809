package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;

import com.sage.demo0809.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/12/7.
 */

public class ActivityBehaviorTest extends ActivityBase {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_test);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.fab)
    public void onClick() {
        Snackbar.make(fab,"just test click",Snackbar.LENGTH_SHORT)
                .setAction("actions", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("action");
                    }
                }).show();
    }
}
