package com.sage.demo0809.bean;

import com.google.gson.Gson;

/**
 * Created by Sage on 2016/9/5.
 */
public class BaseReply<T> {
    public int info;//1表示成功。
    public String tip;
    public T data;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
