package com.sage.demo0809.app;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.bean.BaseError;
import com.sage.demo0809.bean.BaseReply;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sage on 2016/9/5.
 */
public class RxHelper<T> {


    public ObservableTransformer<T,T> handleResult(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final T t) throws Exception {
                        String result=t.toString();
                        for(int i=0;i<result.length();i+=3500){
                            MyLog.i("result=" + result.substring(i,Math.min(i+3500,result.length())));
                        }
                        if(t instanceof BaseError){
                            BaseError temp= (BaseError) t;
                            if(temp.getErrcode()!=0){
                                return Observable.error(new RxException(temp.getErrmsg()));
                            }else{
                                Observable.create(new ObservableOnSubscribe<T>() {
                                    @Override
                                    public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                                        try {
                                            e.onNext(t);
                                            e.onComplete();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            e.onError(ex);
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

    public ObservableTransformer<BaseReply<T>,T> handleBase(){
        return new ObservableTransformer<BaseReply<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseReply<T>> upstream) {
                return upstream.flatMap(new Function<BaseReply<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseReply<T> tBaseReply) throws Exception {
                        MyLog.i("22222=========="+tBaseReply.toString());

                        if(tBaseReply.info==0){
                            return Observable.error(new RxException(tBaseReply.tip));
                        }
                        return Observable.create(new ObservableOnSubscribe<T>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                                try {
                                    e.onNext(tBaseReply.data);
                                    e.onComplete();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    e.onError(ex);
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
