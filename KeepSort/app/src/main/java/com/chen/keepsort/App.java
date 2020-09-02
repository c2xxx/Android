package com.chen.keepsort;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class App extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=565807f9");//讯飞语音注册

    }
}
