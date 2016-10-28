package com.sage.demo0809.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.fragment.FragmentVideoList;

/**
 * Created by Sage on 2016/10/20.
 */

public class Activity7Collapsing extends ActivityBase implements AppBarLayout.OnOffsetChangedListener{
    CollapsingToolbarLayout collaps_toolbar;
    AppBarLayout appBarLayout;
    ImageView backdrop;
    TabLayout tabLayout;
    ViewPager vp;

    ProgressBar pb;
    TextView tv_pb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title="collapsing滚动测试页面";
        setContentView(R.layout.activity_callapsing);
        collaps_toolbar= (CollapsingToolbarLayout) findViewById(R.id.collaps_toolbar);
        backdrop= (ImageView) findViewById(R.id.backdrop);
        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar);

        pb= (ProgressBar) findViewById(R.id.pb);
        tv_pb= (TextView) findViewById(R.id.tv_pb);

        System.out.println("=========");

        appBarLayout.setBackgroundResource(R.mipmap.placeholder_disk_play_song);
//        backdrop.setImageResource(R.mipmap.placeholder_disk_play_song);
        vp= (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new FragmentVideoList();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "title"+position;
            }
        });
        tabLayout.setupWithViewPager(vp);

        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(Math.abs(verticalOffset)>=appBarLayout.getTotalScrollRange()){
            findViewById(R.id.layout_my).setVisibility(View.GONE);
        }else {
            findViewById(R.id.layout_my).setVisibility(View.VISIBLE);
        }
        float alpha=1+verticalOffset*1f/appBarLayout.getTotalScrollRange();
        System.out.println(alpha+"==11======="+appBarLayout.getTotalScrollRange()+"==="+verticalOffset);
        findViewById(R.id.layout_my).setAlpha(alpha);


        if(alpha<0) alpha=0;
        if(alpha>1) alpha=1;
        pb.setProgress((int) (alpha*100));
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) tv_pb.getLayoutParams();
        int left= (int) Math.min(pb.getWidth()*alpha,pb.getWidth()-tv_pb.getWidth());
        params.setMarginStart(left);
        tv_pb.setLayoutParams(params);
    }
}
