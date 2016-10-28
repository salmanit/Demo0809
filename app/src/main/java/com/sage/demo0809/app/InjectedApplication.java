/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.sage.demo0809.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.sage.demo0809.ui.finger.FingerprintModule;
import com.umeng.analytics.MobclickAgent;
import com.zhy.changeskin.SkinManager;

import dagger.ObjectGraph;

/**
 * The Application class of the sample which holds the ObjectGraph in Dagger and enables
 * dependency injection.
 */
public class InjectedApplication extends MultiDexApplication {

    private static final String TAG = InjectedApplication.class.getSimpleName();

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
        initObjectGraph(new FingerprintModule(this));


        MobclickAgent.openActivityDurationTrack(false);
    }


//    @Override
//       protected void attachBaseContext(Context base) {
//                super.attachBaseContext(base);
//                 MultiDex.install(this) ;
//            }

    /**
     * Initialize the Dagger module. Passing null or mock modules can be used for testing.
     *
     * @param module for Dagger
     */
    public void initObjectGraph(Object module) {
        mObjectGraph = module != null ? ObjectGraph.create(module) : null;
    }

    public void inject(Object object) {
        if (mObjectGraph == null) {
            // This usually happens during tests.
            Log.i(TAG, "Object graph is not initialized.");
            return;
        }
        mObjectGraph.inject(object);
    }

}
