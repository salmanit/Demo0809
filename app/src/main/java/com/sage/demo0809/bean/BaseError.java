package com.sage.demo0809.bean;

import com.google.gson.Gson;

/**
 * Created by Sage on 2016/9/6.
 */
public class BaseError {
    private int errcode = 0;
    private String errmsg="";

    public boolean isOkay(){
        return errcode ==0;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
