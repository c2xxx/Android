package com.chen.demo2019;

import android.app.Application;

import com.base.AppContext;
import com.base.Describe;
import com.chen.demo2019.test.LocalMp3Player;

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
