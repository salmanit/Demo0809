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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println("!===1");
                e.onNext(1);
                System.out.println("!===2");
                e.onNext(2);
                System.out.println("!===3");
                e.onNext(3);
                System.out.println("!===complete");
                e.onComplete();
                System.out.println("!===4");
                e.onNext(4);
            }
        })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io())

                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Integer integer) {
                        System.out.println("!===onNext"+integer);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        System.out.println("!===onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("!===onComplete");
                    }
                });

    }

}
