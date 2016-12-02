package com.sage.demo0809.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;
import com.sage.demo0809.adapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/12/2.
 */

public class ActivityTransitionParent extends ActivityBase {

    @BindView(R.id.rv)
    RecyclerView rv;
    MySimpleRvAdapter<String> adapter;
    ArrayList<String>  lists=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_parent);
        ButterKnife.bind(this);
        initMyToolbar();
        for (int i=0;i<30;i++){
            lists.add(i+"aaaaaaaaaaaaaaaaadfsdf\na;dfja;sdfjka;sdfjas;dfjs;adfj\nadfkjasdfjsdfadsf\n\n\n\nn\nadfadsfasdf\n\n\nadfadsfadsfsadfn");
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter=new MySimpleRvAdapter<String>(lists) {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_transition_parent;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, String data) {
                holder.setText(R.id.tv_content,data);
            }
        });
        rv.addOnItemTouchListener(new OnRecyclerItemClickListener(rv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                MyRvViewHolder holder= (MyRvViewHolder) vh;
                startActivity(holder.getView(R.id.tv_content),lists.get(position));
            }
        });
    }

    public void startActivity(View view, String content) {
        Intent intent = new Intent(this, ActivityTransitionChild.class);
        intent.putExtra("msg", content);

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, "hello");
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }
}
