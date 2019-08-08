package com.base.utils;

import android.content.pm.ApplicationInfo;

import com.base.AppContext;

/**
 * 控制（Release版本）程序是否打印LOG
 * Created by ChenHui on 2017/5/25.
 */

public class MyDebugConfig {
    private static boolean isOpenDebug = false;

    static {
        isOpenDebug = true;
    }

    public static boolean isRelease() {
        return !getIsDebug();
    }

    public static boolean isDebug() {
        return getIsDebug() || isOpenDebug;
    }

    private static boolean getIsDebug() {
        try {
            ApplicationInfo info = AppContext.getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void openDebug() {
        isOpenDebug = true;
        Logger.log_level = Logger.LOG_LEVEL.ALL;
        Logger.d("openDebug");
    }

    public static void closeDebug() {
        Logger.d("closeDebug");
        isOpenDebug = false;
        Logger.log_level = Logger.LOG_LEVEL.NONE;
    }
}
