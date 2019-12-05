package com.base.utils

import android.text.TextUtils
import android.util.Log


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by hui on 2015/11/20.
 */
object Logger {

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


    private val TAG = "LoggerAPP"
    private val TAG_NET = "LoggerNET"
    var log_level = LOG_LEVEL.ALL
    private var jsonParser: IJsonParser = object : IJsonParser {
        override fun toJSONString(`object`: Any): String {
            return "" + `object`
        }

        override fun toJSONStringFormat(`object`: Any): String {
            return "" + `object`
        }
    }

    /**
     * 要排除，不打印的位置
     */
    private val noPrintClass = HashSet<String>()

    private var timeStart: Long = 0


    /**
     * 获取调用位置
     *
     * @return
     */
    private//                if ("OkHttpUtils.java".equals(newElement.getFileName())
    //                        || "Method.java".equals(newElement.getFileName())) {
    ////                    trackInfo();
    //                    break;
    //                }
    val position: String?
        get() {
            if (log_level != LOG_LEVEL.NONE) {
                val buffer = StringBuilder()

                var printTrackIndex = 4
                var stackTraceElement = Thread.currentThread().stackTrace[printTrackIndex]


                val maxSize = Thread.currentThread().stackTrace.size
                while (noPrintClass.contains(stackTraceElement.fileName) && printTrackIndex < maxSize) {
                    printTrackIndex++
                    val newElement = Thread.currentThread().stackTrace[printTrackIndex]
                    stackTraceElement = newElement
                }
                buffer.append("(")
                buffer.append(stackTraceElement.fileName)
                buffer.append(":")
                buffer.append(stackTraceElement.lineNumber)
                buffer.append(")")
                return buffer.toString()
            }
            return null

        }

    /**
     * 打印程序调用堆栈信息
     */
    fun trackInfo() {
        if (log_level != LOG_LEVEL.NONE) {
            val tracks = Thread.currentThread().stackTrace
            val trace = " TRACE  "
            Log.i(TAG, "$trace-----------start---------------")
            for (track in tracks) {
                val buffer = StringBuilder()
                buffer.append(track.className + ":" + track.methodName)
                buffer.append("(")
                buffer.append(track.fileName)
                buffer.append(":")
                buffer.append(track.lineNumber)
                buffer.append(")")
                Log.i(TAG, trace + buffer.toString())
            }
            Log.i(TAG, "$trace-----------end---------------")
        }
    }

    init {
        val array = ArrayList<String>()
        array.add("Logger.java")
        array.add("BaseRequest.java")
        array.add("ToastUtil.java")
        initNoPrintPlace(array)

    }

    fun initNoPrintPlace(array: List<String>) {
        noPrintClass.clear()
        noPrintClass.addAll(array)
    }

    fun setJsonParser(jsonParser1: IJsonParser) {
        jsonParser = jsonParser1
    }

    /**
     * 打印异常信息
     *
     * @param e
     */
    fun e(e: Throwable?) {
        if (e == null) {
            return
        }
        val msg = e.message
        if (!TextUtils.isEmpty(msg)) {
            Logger.e("e.getMessage=" + msg + " className=" + e.javaClass.getSimpleName())
        }
        e(Log.getStackTraceString(e))
    }

    fun time1() {
        timeStart = System.currentTimeMillis()
    }


    fun time2(vararg str: String): Long {
        var s = ""
        if (str != null && str.size > 0) {
            s = str[0] + "  "
        }
        val cosTime = System.currentTimeMillis() - timeStart
        Logger.d(s + "Cost Time=" + cosTime)
        return cosTime
    }

    fun json(str: String, `object`: Any) {
        if (log_level == LOG_LEVEL.NONE) {
            return
        }
        d(str, jsonParser.toJSONString(`object`))
    }

    fun jsonFormat(tag: String, `object`: Any) {
        if (log_level == LOG_LEVEL.NONE) {
            return
        }
        logLongStr(tag, jsonParser.toJSONStringFormat(`object`))
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    fun netLog(msg: String) {
        log(LOG_LEVEL.DEBUG, TAG_NET, msg)
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    fun netLogErr(msg: String) {
        log(LOG_LEVEL.ERROR, TAG_NET, msg)
    }


    fun d(msg: String) {
        log(LOG_LEVEL.DEBUG, null, msg)
    }


    fun e(msg: String) {
        log(LOG_LEVEL.ERROR, null, msg)
    }

    fun d(tag: String, msg: String) {
        log(LOG_LEVEL.DEBUG, tag, msg)
    }

    fun e(tag: String, msg: String) {
        log(LOG_LEVEL.ERROR, tag, msg)
    }

    fun logLongStr(tag: String, msg: String) {
        val level = LOG_LEVEL.DEBUG
        val lineMax = 1000
        if (TextUtils.isEmpty(msg) || msg.length <= lineMax) {
            log(level, tag, msg)
            return
        }

        val strs = msg.split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val lineTag = "line"
        val lenTag = lineTag.length + 8
        log(level, tag, "-------print long string-------------")
        var i = 0
        val strsSize = strs.size
        while (i < strsSize) {
            val str = strs[i]
            var lineTag1 = if (strsSize == 1) lineTag else lineTag + "" + String.format("%02d", i)
            while (lineTag1.length < lenTag) {
                lineTag1 = "$lineTag1 "
            }
            if (str.length <= lineMax) {
                log(level, tag, "$lineTag1 $str", false)
            } else {
                val lenStr = str.length
                val size = Math.ceil(lenStr * 1.0 / lineMax).toInt()
                for (j in 0 until size) {
                    var lineTag2 = lineTag1.trim { it <= ' ' } + "_" + String.format("%02d", j)
                    while (lineTag2.length < lenTag) {
                        lineTag2 = lineTag2 + " "
                    }
                    val indexStart = j * lineMax
                    var indexEnd = indexStart + lineMax
                    indexEnd = Math.min(indexEnd, lenStr)
                    val line = str.substring(indexStart, indexEnd)
                    log(level, tag, lineTag2 + " " + line, false)
                }
            }
            i++
        }
    }

    @JvmOverloads
    fun log(level: LOG_LEVEL, tag: String?, msg: String, needPosition: Boolean = true) {
        var tag = tag
        var msg = msg
        if (level.ordinal < log_level.ordinal) {
            return
        }
        if (tag == null) {
            tag = TAG
        } else if (!tag.contains(TAG) && !tag.contains(TAG_NET)) {
            tag = TAG + "_" + tag
        }
        if (needPosition) {
            msg = msgWithPosition(msg)
        }
        when (level) {
            Logger.LOG_LEVEL.ALL, Logger.LOG_LEVEL.DEBUG -> Log.d(tag, msg)
            Logger.LOG_LEVEL.INFO -> Log.i(tag, msg)
            Logger.LOG_LEVEL.WARN -> Log.w(tag, msg)
            Logger.LOG_LEVEL.ERROR -> Log.e(tag, msg)
            Logger.LOG_LEVEL.NONE -> {
            }
        }
    }


    /**
     * 日志打印级别
     */
    enum class LOG_LEVEL {
        ALL,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        NONE
    }

    private fun msgWithPosition(msg: String?): String {
        var msg = msg
        val position = position
        if (msg != null && msg.length < 100) {
            msg = "$msg $position"
        } else {
            msg = position + "\n" + msg
        }
        return msg
    }

    interface IJsonParser {
        fun toJSONString(`object`: Any): String

        fun toJSONStringFormat(`object`: Any): String
    }
}
