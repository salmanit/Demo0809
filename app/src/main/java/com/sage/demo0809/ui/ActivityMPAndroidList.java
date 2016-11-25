package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;
import com.sage.demo0809.bean.TwitterItems;
import com.sage.demo0809.bean.TwitterOptions;
import com.sage.demo0809.widget.MyBarChart;
import com.sage.demo0809.widget.MyPieChart;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/11/23.
 */

public class ActivityMPAndroidList extends ActivityBase {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_list);
        ButterKnife.bind(this);
        initMyToolbar();

        ArrayList<TwitterItems> lists=new ArrayList<>();
        for(int i=0;i<15;i++){
            TwitterItems temp=new TwitterItems();
            temp.content="content"+i;
            int size=new Random().nextInt(8)+1;
            for(int j=0;j<size;j++){
                TwitterOptions options=new TwitterOptions();
                options.setCount(new Random().nextInt(5));
                options.setText("options"+i);
                temp.optionses.add(options);
            }
            lists.add(temp);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MySimpleRvAdapter<TwitterItems>(lists) {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_mpandroid_list;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, TwitterItems data) {

                holder.setText(R.id.tv_content,data.content);
                MyBarChart myBarChart=holder.getView(R.id.mybarchart);
                MyPieChart myPieChart=holder.getView(R.id.mypiechart);

                myBarChart.setData(data.optionses,"11");
                myPieChart.setData(data.optionses,"22");
            }
        });
    }



}
