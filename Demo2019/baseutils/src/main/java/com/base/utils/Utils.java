package com.base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.text.DecimalFormat;

/**
 * Created by ChenHui on 2017/7/20.
 */

public class Utils {

    /**
     * 单位换算
     *
     * @param fileS
     * @return
     */
    public static String formatSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * 打印内存使用情况
     */
    public static void printMemory() {
        System.gc();
        long total = Runtime.getRuntime().totalMemory(); // byte
        long free = Runtime.getRuntime().freeMemory();
//        Logger.d("内存占用详情 Memory  total=" + total + "  free=" + free);
        Logger.d(String.format("内存占用详情 Memory   allocated/free/total=%s/%s/%s (%s/%s/%s)", (total - free), free, total, formatSize((total - free)), formatSize(free), formatSize(total)));
    }


    /**
     * 不能使用的context，如果是activity，检查是否destroy
     *
     * @param context
     * @return
     */
    public static boolean checkContextError(Context context) {
        if (context == null) {
            return true;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed()) {
                    return true;
                }
            } else if (activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

}
