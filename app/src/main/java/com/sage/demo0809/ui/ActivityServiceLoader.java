package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.custom.loader.PeopleIml;

import java.util.ServiceLoader;

/**
 * Created by Sage on 2017/5/12.
 */

public class ActivityServiceLoader extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_loader);

        ServiceLoader<PeopleIml>  serviceLoader=ServiceLoader.load(PeopleIml.class);
        for (PeopleIml peopleIml:serviceLoader){
            MyLog.i("====="+peopleIml.sayHello()+"=="+peopleIml.sayDes());
        }
    }
}
