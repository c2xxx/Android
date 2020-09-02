package com.chen.utils

import android.util.Log

class Logger {


    companion object {
        val PRINT_LOG = true;
        val TAG = "LOGGER_2020";


        fun d(tag: String?, msg: String?) {
            Log.d(TAG + '_' + tag, msg)
        }

        fun d(msg: String?) {
            Log.d(TAG, msg)

        }
    }

}