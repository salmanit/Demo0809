package com.sage.demo0809.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;
import com.sage.demo0809.adapter.OnRecyclerItemClickListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sage on 2016/11/4.
 */

public class ActivityAllApplication extends ActivityBase {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_apps)
    RecyclerView rvApps;

    MySimpleRvAdapter<ResolveInfo> adapter;
    PackageManager packageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_application);
        ButterKnife.bind(this);
        packageManager = getPackageManager();
        rvApps.setLayoutManager(new LinearLayoutManager(this));
        rvApps.setAdapter(adapter = new MySimpleRvAdapter<ResolveInfo>() {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_all_application;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, ResolveInfo data) {
                holder.setText(R.id.tv_position, "" + position);
                holder.setText(R.id.tv_package_name, data.activityInfo.packageName);
                holder.setText(R.id.tv_name, data.loadLabel(packageManager));
                Drawable icon = data.loadIcon(packageManager); // 获得应用程序图标

                holder.setImageResource(R.id.iv_logo, icon);
                holder.setText(R.id.tv_all, data.toString());
            }
        });

        rvApps.addOnItemTouchListener(new OnRecyclerItemClickListener(rvApps) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                ResolveInfo resolveInfo = adapter.getItem(position);
                if (resolveInfo != null) {
                    Intent launchIntent = new Intent();
                    launchIntent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name));
                    startActivity(launchIntent);
                }
            }
        });
        loadingUnder();
    }


    private void loadingUnder() {


        Observable.create(new Observable.OnSubscribe<List<ResolveInfo>>() {
            @Override
            public void call(Subscriber<? super List<ResolveInfo>> subscriber) {
                PackageManager pm = getPackageManager(); // 获得PackageManager对象
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 通过查询，获得所有ResolveInfo对象.
                List<ResolveInfo> resolveInfos = pm
                        .queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
                // 调用系统排序 ， 根据name排序
                // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
                subscriber.onNext(resolveInfos);
            }
        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ResolveInfo>>() {
                    @Override
                    public void call(List<ResolveInfo> resolveInfos) {
                        adapter.setLists(resolveInfos);
                    }
                });
    }

}
