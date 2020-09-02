package demo.playerlib;

import android.util.Log;

/**
 * Created by ChenHui on 2017/4/25.
 */

class Logger {
    public static void d(String msg) {
        Log.d("Logger", msg);
    }
    public static void e(String msg) {
        Log.e("Logger", msg);
    }
}
