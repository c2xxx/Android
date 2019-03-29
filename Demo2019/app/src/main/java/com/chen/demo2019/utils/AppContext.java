package com.chen.demo2019.utils;

import android.content.Context;


/**
 * 应用初始化的时候设置application的context
 * Created by ChenHui on 2017/6/27.
 */

public class AppContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * app初始化的时候调用
     *
     * @param context
     */
    public static void setContext(Context context) {
        AppContext.context = context;
    }

    /**
     * 有Manifest.permission.READ_PHONE_STATE权限再开启，不然Android6.0+会卡死
     */
    public static void initBlockCanary() {
//        if (true || MyDebugConfig.isDebug()) {
//            BlockCanary.install(context, new AppBlockCanaryContext()).start();
//        }

    }
}
