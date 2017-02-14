package com.sage.demo0809.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.app.BaseBuildInfo;
import com.sage.demo0809.app.BuildInfo;
import com.sage.demo0809.fragment.FragmentVideoList;
import com.sage.demo0809.util.Utils;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/10/20.
 */

public class Activity7Collapsing extends ActivityBase implements AppBarLayout.OnOffsetChangedListener {
    CollapsingToolbarLayout collaps_toolbar;
    AppBarLayout appBarLayout;
    ImageView backdrop;
    TabLayout tabLayout;
    ViewPager vp;

    ProgressBar pb;
    TextView tv_pb;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = "collapsing滚动测试页面";
        setContentView(R.layout.activity_callapsing);
        ButterKnife.bind(this);
        collaps_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collaps_toolbar);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        pb = (ProgressBar) findViewById(R.id.pb);
        tv_pb = (TextView) findViewById(R.id.tv_pb);
        System.out.println("=========");

        appBarLayout.setBackgroundResource(R.mipmap.placeholder_disk_play_song);
//        backdrop.setImageResource(R.mipmap.placeholder_disk_play_song);
        vp = (ViewPager) findViewById(R.id.vp);
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
                return "title" + position;
            }
        });
        tabLayout.setupWithViewPager(vp);

        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("开始更新,新版本0214");
                File file = new File(Environment.getExternalStorageDirectory(), "/p.apk");
                MyLog.i("p.apk path=======" + file.getAbsolutePath());
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), file.getAbsolutePath());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setBackground(false);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            findViewById(R.id.layout_my).setVisibility(View.GONE);
        } else {
            findViewById(R.id.layout_my).setVisibility(View.VISIBLE);
        }
        float alpha = 1 + verticalOffset * 1f / appBarLayout.getTotalScrollRange();
//        System.out.println(alpha + "==11=======" + appBarLayout.getTotalScrollRange() + "===" + verticalOffset);
        findViewById(R.id.layout_my).setAlpha(alpha);


        if (alpha < 0) alpha = 0;
        if (alpha > 1) alpha = 1;
        pb.setProgress((int) (alpha * 100));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tv_pb.getLayoutParams();
        int left = (int) Math.min(pb.getWidth() * alpha, pb.getWidth() - tv_pb.getWidth());
        params.setMarginStart(left);
        tv_pb.setLayoutParams(params);
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1://load
                File patch=new File(Environment.getExternalStorageDirectory(),"/apk/patch_signed_7zip.apk");
                if(patch.exists()){
                    TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), patch.getAbsolutePath());
                }

                break;
            case R.id.tv2://show
                showInfo(this);
                break;
            case R.id.tv3://kill
                ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.tv4://clean
                Tinker.with(getApplicationContext()).cleanPatch();
                break;
        }
    }

    public boolean showInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[220000220009999990000patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[2200002200099999990000patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

}
