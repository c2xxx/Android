package com.base.utils;

import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hui on 2015/11/20.
 */
public class Logger {

//    DEMO:
//    static {
//        Logger.setJsonParser(new Logger.IJsonParser() {
//            @Override
//            public String toJSONString(Object object) {
//                return JSON.toJSONString(object);
//            }
//
//            @Override
//            public String toJSONStringFormat(Object object) {
//                return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                        SerializerFeature.WriteDateUseDateFormat);
//            }
//        });
//    }


    private static final String TAG = "LoggerAPP";
    private static final String TAG_NET = "LoggerNET";
    public static LOG_LEVEL log_level = LOG_LEVEL.ALL;
    private static IJsonParser jsonParser = new IJsonParser() {
        @Override
        public String toJSONString(Object object) {
            return "" + object;
        }

        @Override
        public String toJSONStringFormat(Object object) {
            return "" + object;
        }
    };

    /**
     * 打印程序调用堆栈信息
     */
    public static void trackInfo() {
        if (log_level != LOG_LEVEL.NONE) {
            StackTraceElement[] tracks = Thread.currentThread().getStackTrace();
            String trace = " TRACE  ";
            Log.i(TAG, trace + "-----------start---------------");
            for (StackTraceElement track : tracks) {
                StringBuilder buffer = new StringBuilder();
                buffer.append(track.getClassName() + ":" + track.getMethodName());
                buffer.append("(");
                buffer.append(track.getFileName());
                buffer.append(":");
                buffer.append(track.getLineNumber());
                buffer.append(")");
                Log.i(TAG, trace + buffer.toString());
            }
            Log.i(TAG, trace + "-----------end---------------");
        }
    }

    /**
     * 要排除，不打印的位置
     */
    private static final Set<String> noPrintClass = new HashSet<>();

    static {
        List<String> array = new ArrayList<>();
        array.add("Logger.java");
        array.add("BaseRequest.java");
        array.add("ToastUtil.java");
        initNoPrintPlace(array);

    }

    public static void initNoPrintPlace(List<String> array) {
        noPrintClass.clear();
        noPrintClass.addAll(array);
    }

    public static void setJsonParser(IJsonParser jsonParser1) {
        jsonParser = jsonParser1;
    }

    /**
     * 打印异常信息
     *
     * @param e
     */
    public static void e(Throwable e) {
        if (e == null) {
            return;
        }
        String msg = e.getMessage();
        if (!TextUtils.isEmpty(msg)) {
            Logger.e("e.getMessage=" + msg + " className=" + e.getClass().getSimpleName());
        }
        e(Log.getStackTraceString(e));
    }

    private static long timeStart;

    public static void time1() {
        timeStart = System.currentTimeMillis();
    }


    public static long time2(String... str) {
        String s = "";
        if (str != null && str.length > 0) {
            s = str[0] + "  ";
        }
        long cosTime = (System.currentTimeMillis() - timeStart);
        Logger.d(s + "Cost Time=" + cosTime);
        return cosTime;
    }

    public static void json(String str, Object object) {
        if (log_level == LOG_LEVEL.NONE) {
            return;
        }
        d(str, jsonParser.toJSONString(object));
    }

    public static void jsonFormat(String tag, Object object) {
        if (log_level == LOG_LEVEL.NONE) {
            return;
        }
        logLongStr(tag, jsonParser.toJSONStringFormat(object));
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    public static void netLog(String msg) {
        log(LOG_LEVEL.DEBUG, TAG_NET, msg);
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    public static void netLogErr(String msg) {
        log(LOG_LEVEL.ERROR, TAG_NET, msg);
    }


    public static void d(String msg) {
        log(LOG_LEVEL.DEBUG, null, msg);
    }


    public static void e(String msg) {
        log(LOG_LEVEL.ERROR, null, msg);
    }

    public static void d(String tag, String msg) {
        log(LOG_LEVEL.DEBUG, tag, msg);
    }

    public static void e(String tag, String msg) {
        log(LOG_LEVEL.ERROR, tag, msg);
    }

    public static void logLongStr(String tag, String msg) {
        LOG_LEVEL level = LOG_LEVEL.DEBUG;
        int lineMax = 1000;
        if (TextUtils.isEmpty(msg) || msg.length() <= lineMax) {
            log(level, tag, msg);
            return;
        }

        String[] strs = msg.split("\n");
        String lineTag = "line";
        int lenTag = lineTag.length() + 8;
        log(level, tag, "-------print long string-------------");
        for (int i = 0, strsSize = strs.length; i < strsSize; i++) {
            String str = strs[i];
            String lineTag1 = strsSize == 1 ? lineTag : lineTag + "" + String.format("%02d", i);
            while (lineTag1.length() < lenTag) {
                lineTag1 = lineTag1 + " ";
            }
            if (str.length() <= lineMax) {
                log(level, tag, lineTag1 + " " + str, false);
            } else {
                int lenStr = str.length();
                int size = (int) Math.ceil(lenStr * 1.0 / lineMax);
                for (int j = 0; j < size; j++) {
                    String lineTag2 = lineTag1.trim() + "_" + String.format("%02d", j);
                    while (lineTag2.length() < lenTag) {
                        lineTag2 = lineTag2 + " ";
                    }
                    int indexStart = j * lineMax;
                    int indexEnd = indexStart + lineMax;
                    indexEnd = Math.min(indexEnd, lenStr);
                    String line = str.substring(indexStart, indexEnd);
                    log(level, tag, lineTag2 + " " + line, false);
                }
            }
        }
    }

    public static void log(LOG_LEVEL level, String tag, String msg) {
        log(level, tag, msg, true);
    }

    public static void log(LOG_LEVEL level, String tag, String msg, boolean needPosition) {
        if (level.ordinal() < log_level.ordinal()) {
            return;
        }
        if (tag == null) {
            tag = TAG;
        } else if (!tag.contains(TAG) && !tag.contains(TAG_NET)) {
            tag = TAG + "_" + tag;
        }
        if (needPosition) {
            msg = msgWithPosition(msg);
        }
        switch (level) {
            case ALL:
            case DEBUG:
                Log.d(tag, msg);
                break;
            case INFO:
                Log.i(tag, msg);
                break;
            case WARN:
                Log.w(tag, msg);
                break;
            case ERROR:
                Log.e(tag, msg);
                break;
            case NONE:
                break;
        }
    }


    /**
     * 日志打印级别
     */
    public enum LOG_LEVEL {
        ALL,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        NONE
    }

    private static String msgWithPosition(String msg) {
        String position = getPosition();
        if (msg != null && msg.length() < 100) {
            msg = msg + " " + position;
        } else {
            msg = position + "\n" + msg;
        }
        return msg;
    }


    /**
     * 获取调用位置
     *
     * @return
     */
    private static String getPosition() {
        if (log_level != LOG_LEVEL.NONE) {
            StringBuilder buffer = new StringBuilder();

            int printTrackIndex = 4;
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[printTrackIndex];


            int maxSize = Thread.currentThread().getStackTrace().length;
            while (noPrintClass.contains(stackTraceElement.getFileName())
                    && printTrackIndex < maxSize) {
                printTrackIndex++;
                StackTraceElement newElement = Thread.currentThread().getStackTrace()[printTrackIndex];
//                if ("OkHttpUtils.java".equals(newElement.getFileName())
//                        || "Method.java".equals(newElement.getFileName())) {
////                    trackInfo();
//                    break;
//                }
                stackTraceElement = newElement;
            }
            buffer.append("(");
            buffer.append(stackTraceElement.getFileName());
            buffer.append(":");
            buffer.append(stackTraceElement.getLineNumber());
            buffer.append(")");
            return buffer.toString();
        }
        return null;

    }

    public interface IJsonParser {
        String toJSONString(Object object);

        String toJSONStringFormat(Object object);
    }
}
