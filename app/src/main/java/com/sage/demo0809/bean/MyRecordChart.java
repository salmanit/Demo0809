package com.sage.demo0809.bean;

import java.util.ArrayList;

/**
 * Created by Sage on 2016/9/1.
 */
public class MyRecordChart {
    /**
     * name : rmssd
     * list : [{"date_time":"1472918400","value":"0"},{"date_time":"1472832000","value":"0"},{"date_time":"1472745600","value":"0"},{"date_time":"1472659200","value":"2.4"},{"date_time":"1472572800","value":"6.2"},{"date_time":"1472486400","value":"2.3"},{"date_time":"1472400000","value":"0"}]
     * weekAverage : 2.7
     * allAverage : 10.4
     * current_page : 1
     */

    public String name;
    public String weekAverage;
    public String allAverage;
    public String current_page;
    public ArrayList<HrvDayValueEntity> list;



}
