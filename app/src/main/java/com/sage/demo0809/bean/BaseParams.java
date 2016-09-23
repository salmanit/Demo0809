package com.sage.demo0809.bean;

import com.google.gson.Gson;

/**
 * Created by Sage on 2016/9/5.
 */
public class BaseParams {
    public String mobile_phone;
    public String password;
    public String version;
    public String channel;

    public BaseParams() {
        this("13795492180","888888","1.0","360");
    }

    public BaseParams(String mobile_phone, String password, String version, String channel) {
        this.mobile_phone = mobile_phone;
        this.password = password;
        this.version = version;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
