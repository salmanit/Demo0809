package com.sage.demo0809.ui;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sage.demo0809.R;
import com.sage.demo0809.adapter.MyRvViewHolder;
import com.sage.demo0809.adapter.MySimpleRvAdapter;

import java.util.ArrayList;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        rv.setAdapter(adapter=new MySimpleRvAdapter<String>(urls) {
            @Override
            public int layoutId(int viewType) {
                return R.layout.item_video_list;
            }

            @Override
            public void handleData(MyRvViewHolder holder, int position, String data) {

            }
        });


//        test1();
    }

    @OnClick(R.id.iv_show)
    public void hello_click(View view){
    }
    public void test1(){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("!===1");
                subscriber.onNext(1);
                System.out.println("!===2");
                subscriber.onNext(2);
                System.out.println("!===3");
                subscriber.onNext(3);
                System.out.println("!===complete");
                subscriber.onCompleted();
                System.out.println("!===4");
                subscriber.onNext(4);
            }
        })
        .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {



                    @Override
                    public void onNext(Integer value) {
                        System.out.println("!===onNext"+value);

                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("!===onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("!===onError");
                    }

                });
    }

}
