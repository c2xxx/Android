package com.base;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.base.utils.Logger;

public class AppContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppContext.context = context;
    }

    static {
        Logger.setJsonParser(new Logger.IJsonParser() {
            @Override
            public String toJSONString(Object object) {
                return JSON.toJSONString(object);
            }

            @Override
            public String toJSONStringFormat(Object object) {
                return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat);
            }
        });
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
