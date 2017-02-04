package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;
import com.sage.demo0809.adapter.OnRecyclerItemClickListener;
import com.sage.demo0809.app.InjectedApplication;
import com.sage.demo0809.bean.BeanActivity;
import com.sage.demo0809.service.OtherAccessibilityService;
import com.sage.demo0809.service.RedAccessibilityService;
import com.sage.demo0809.ui.finger.SettingsActivity;
import com.sage.demo0809.ui.guard.ActivityGuard;
import com.sage.demo0809.util.MyUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/10/28.
 */

public class ActivityLists extends ActivityBase {

    @BindView(R.id.rv)
    RecyclerView rv;
    public ArrayList<BeanActivity>  lists=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

//        MyUtils.isAccessibilitySettingsOn(this, RedAccessibilityService.class);
//        MyUtils.isAccessibilitySettingsOn(this, OtherAccessibilityService.class);


        int year=Calendar.getInstance().get(Calendar.YEAR);
        MyUtils.enabled(this,"");

        System.err.println("7.1.9".compareTo("7.1.90")+"================"+year);
        ButterKnife.bind(this);
        lists.add(new BeanActivity("vector的path动画",ActivityPathAnima.class));
        lists.add(new BeanActivity("collapsing滚动测试1036",Activity7Collapsing.class));
        lists.add(new BeanActivity("MPandroid图表库1036",ActivityChart.class));
        lists.add(new BeanActivity("MPandroid图表库列表里的测试",ActivityMPAndroidList.class));
        lists.add(new BeanActivity("hellocharts图表库",ActivityChartDemo.class));
        lists.add(new BeanActivity("心情选择库",ActivityChooseMood.class));
        lists.add(new BeanActivity("视频预览图",ActivityD.class));
        lists.add(new BeanActivity("输入法表情使用",ActivityDrawerLayout.class));
        lists.add(new BeanActivity("本地网页",ActivityLocalWeb.class));
        lists.add(new BeanActivity("s健康",ActivityStep.class));
        lists.add(new BeanActivity("视频列表~全屏",ActivityVideoList.class));
        lists.add(new BeanActivity("默认登陆页",LoginActivity.class));
        lists.add(new BeanActivity("Retrofit测",MainActivity.class));
        lists.add(new BeanActivity("BottomSheetBehavior",ScrollingActivity.class));
        lists.add(new BeanActivity("BottomSheetDialogFragment",ScrollingActivity2.class));
        lists.add(new BeanActivity("设置页面xml写的",SettingsActivity.class));
        lists.add(new BeanActivity("锁屏页面",ActivityGuard.class));
        lists.add(new BeanActivity("手机所有的app",ActivityAllApplication.class));
        lists.add(new BeanActivity("手机所有的app2",ActivityAllApplication2.class));
        lists.add(new BeanActivity("悬浮窗测试",ActivitySuspension.class));
        lists.add(new BeanActivity("tablayout测试",ActivityTabLayout.class));
        lists.add(new BeanActivity("normal webview测试",ActivityWebTest.class));
        lists.add(new BeanActivity("自定义转场动画",ActivityTransitionParent.class));
        lists.add(new BeanActivity("behavior学习",ActivityBehaviorTest.class));
        lists.add(new BeanActivity("test1",ActivityTest1.class));
        lists.add(new BeanActivity("test2",ActivityTest2.class));
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setAdapter(new MySimpleRvAdapter<BeanActivity>(lists) {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_activity_lists;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, BeanActivity data) {
                holder.setText(R.id.tv_position,""+position);
                holder.setText(R.id.tv_name,data.name);
                holder.setText(R.id.tv_class,data.cla.getSimpleName());
            }
        });

        rv.addOnItemTouchListener(new OnRecyclerItemClickListener(rv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {

//                if(position%2==0){
//                    InjectedApplication.app.changeUMappKey("5811ce78310c931cf7001229");//测试用demo
//                }else{
//                    InjectedApplication.app.changeUMappKey("5819a8f2c62dca269700218f");//test test
//                }
                goNext(lists.get(position).cla,lists.get(position).name);
            }
        });

    }


}
