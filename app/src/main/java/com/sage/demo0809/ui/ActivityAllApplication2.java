package com.sage.demo0809.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
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

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sage on 2016/11/4.
 */

public class ActivityAllApplication2 extends ActivityBase {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_apps)
    RecyclerView rvApps;

    MySimpleRvAdapter<ApplicationInfo> adapter;
    PackageManager packageManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_application);
        ButterKnife.bind(this);
        packageManager=getPackageManager();
        rvApps.setLayoutManager(new LinearLayoutManager(this));
        rvApps.setAdapter(adapter=new MySimpleRvAdapter<ApplicationInfo>() {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_all_application;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, ApplicationInfo data) {
                holder.setText(R.id.tv_position,""+position);
                holder.setText(R.id.tv_package_name,data.packageName);
                holder.setText(R.id.tv_name,data.loadLabel(packageManager));
                Drawable icon = data.loadIcon(packageManager); // 获得应用程序图标

                holder.setImageResource(R.id.iv_logo,icon);
                holder.setText(R.id.tv_all,data.toString());
            }
        });

        rvApps.addOnItemTouchListener(new OnRecyclerItemClickListener(rvApps) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                ApplicationInfo applicationInfo=adapter.getItem(position);
                if(applicationInfo!=null){
                    try {
                        Intent intent=new Intent();
                        intent.setPackage(applicationInfo.packageName);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    loadingUnder();
    }


    private void loadingUnder(){
        Observable.create(new ObservableOnSubscribe<List<ApplicationInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ApplicationInfo>> e) throws Exception {
                PackageManager packageManager = getPackageManager();

                List<ApplicationInfo> applicationList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                e.onNext(applicationList);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<ApplicationInfo>>() {
                    @Override
                    public void accept(List<ApplicationInfo> applicationInfos) throws Exception {
                        adapter.setLists(applicationInfos);
                    }
                });

    }

}
