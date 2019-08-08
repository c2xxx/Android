package com.chen.demo2019;

import android.app.Application;

import com.base.AppContext;
import com.base.Describe;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        test();
        AppContext.setContext(this);
    }

    public void test() {
        Describe.describe();
    }

}
