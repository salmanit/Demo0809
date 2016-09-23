package com.sage.demo0809.widget;

/**
 * Created by Sage on 2016/9/13.
 */
public class TestSingleton {


    public static TestSingleton getInstance() {
        return TestSingletonHolder.TESTSINGLETON;
    }

    private TestSingleton() {
    }

    private static class TestSingletonHolder{
        public static TestSingleton TESTSINGLETON=new TestSingleton();
    }
}
