package com.sage.demo0809.app;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.bean.BaseError;
import com.sage.demo0809.bean.BaseReply;

import rx.Observable;
import rx.Subscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Sage on 2016/9/5.
 */
public class RxHelper<T> {

    public  Observable.Transformer<T,T> handleResult(){
        return  new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.flatMap(new Func1<T, Observable<T>>() {
                    @Override
                    public Observable<T> call(final T t) {
//                        MyLog.i("1111======="+t.toString());
                        String result=t.toString();
                        for(int i=0;i<result.length();i+=3500){
                            MyLog.i("result=" + result.substring(i,Math.min(i+3500,result.length())));
                        }
                        if(t instanceof BaseError){
                            BaseError temp= (BaseError) t;
                            if(temp.getErrcode()!=0){
                                return Observable.error(new RxException(temp.getErrmsg()));
                            }else{
                                Observable.create(new Observable.OnSubscribe<T>() {
                                    @Override
                                    public void call(Subscriber<? super T> subscriber) {
                                        try {
                                            subscriber.onNext(t);
                                            subscriber.onCompleted();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            subscriber.onError(e);
                                        }
                                    }
                                });
                            }
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io());
            }
        };
    }


    public Observable.Transformer<BaseReply<T>,T> handleBase(){
        return  new Observable.Transformer<BaseReply<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseReply<T>> baseReplyObservable) {
                return baseReplyObservable.flatMap(new Func1<BaseReply<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final BaseReply<T> tBaseReply) {
                        MyLog.i("22222=========="+tBaseReply.toString());

                        if(tBaseReply.info==0){
                            return Observable.error(new RxException(tBaseReply.tip));
                        }
                        return Observable.create(new Observable.OnSubscribe<T>() {
                            @Override
                            public void call(Subscriber<? super T> subscriber) {
                                try {
                                    subscriber.onNext(tBaseReply.data);
                                    subscriber.onCompleted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        ;
            }
        };
    }
}
