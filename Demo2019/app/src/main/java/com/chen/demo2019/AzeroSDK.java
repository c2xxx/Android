package com.chen.demo2019;

import android.content.Context;

import com.base.utils.Logger;
import com.soundai.azero_sdk.constant.Cmd;
import com.soundai.azero_sdk.constant.Env;
import com.soundai.azero_sdk.util.AzeroSdkManager;
import com.zhy.http.okhttp.callback.StringCallback;

public class AzeroSDK {
    public void init(Context context) {
//        AzeroSdkManager.getInstance().initSdkConfig();

        final String appId = "2bf8eac8a9f18e62";
        final String secretKey = "196e330485d85fe96b450991aa83d4cb";
        AzeroSdkManager sdkManager = AzeroSdkManager.getInstance();
        sdkManager.setEnv(Env.FAT);
        sdkManager.initSdkConfig(appId, secretKey);
    }

    public void post(Cmd cmd, String argJson, StringCallback callback) {
        Logger.d("post cmd=" + cmd.name() + "    params=" + argJson);
        AzeroSdkManager.getInstance().postMessage(cmd, argJson, callback);
    }
}
