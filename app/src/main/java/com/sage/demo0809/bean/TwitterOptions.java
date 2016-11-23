package com.sage.demo0809.bean;

import java.io.Serializable;

/**
 * Created by Sage on 2016/9/26.
 */

public class TwitterOptions implements Serializable{
    private String _id;
    private String text;
    private String img_option;
    private int count;
    public float value;
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_option() {
        return img_option;
    }

    public void setImg_option(String img_option) {
        this.img_option = img_option;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
