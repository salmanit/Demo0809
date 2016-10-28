package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;
import com.sage.demo0809.adapter.OnRecyclerItemClickListener;
import com.sage.demo0809.bean.BeanActivity;
import com.sage.demo0809.ui.finger.SettingsActivity;
import com.sage.demo0809.ui.guard.ActivityGuard;

import java.util.ArrayList;

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
        ButterKnife.bind(this);
        lists.add(new BeanActivity("collapsing滚动测试",Activity7Collapsing.class));
        lists.add(new BeanActivity("MPandroid图表库",ActivityChart.class));
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
                goNext(lists.get(position).cla);
            }
        });

    }


}
