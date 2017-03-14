package com.sage.demo0809.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRxTest extends ActivityBase {

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.iv_show)
    ImageView iv_show;
    String url="http://sunroam.imgup.cn/aerospace/bjc/19.mp4";
    MySimpleRvAdapter<String> adapter;
    ArrayList<String>  urls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);
        ButterKnife.bind(this);
        for(int i=0;i<22;i++){
            urls.add(url);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter=new MySimpleRvAdapter<String>() {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_video_list;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, String data) {

            }
        });
    }


}
