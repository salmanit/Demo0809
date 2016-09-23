package com.sage.demo0809.app;

import rx.Subscriber;

/**
 * Created by Sage on 2016/9/5.
 */
public abstract class RxSubscribe<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

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
