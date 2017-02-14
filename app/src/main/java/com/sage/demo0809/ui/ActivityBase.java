package com.sage.demo0809.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sage.demo0809.R;
import com.sage.demo0809.util.ThemeHelper;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Sage on 2016/9/23.
 */

public class ActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.statusBarLightMode(this);
        String intentTitle=getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(intentTitle)){
            title=intentTitle;
            TextView toolbar_title= (TextView) findViewById(R.id.toolbar_title);
            if(toolbar_title!=null){
                toolbar_title.setText(title);
            }
        }
        // 设置contentFeature,可使用切换动画
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition explode = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
//        getWindow().setEnterTransition(explode);
    }

    public void initMyToolbar(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
        setTitle("");
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void goNext(Class cla,String title){
        startActivity(new Intent(this,cla).putExtra("title",title));
//        Intent intent = new Intent(this, cla);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
    public String title="默认的";

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onPageStart(title); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(title); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);

    }


    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
