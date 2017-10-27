package com.sage.demo0809.app;


import org.reactivestreams.Subscriber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sage on 2016/9/5.
 */
public abstract class RxSubscribe<T> implements Observer<T> {


    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

//    @Override
//    public void onNext(@NonNull T t) {
//
//    }

    @Override
    public void onError(Throwable e) {
        if( e instanceof RxException){
            _onError(e.getMessage());
        }else{
            _onError("连接服务器异常，请稍后重试");
        }

    }


    public abstract  void _onError(String msg);
}
