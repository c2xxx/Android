package com.base.net;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ChenHui on 2019/3/31.
 */
public class BaseRequest implements IBaseRequest {
    @Override
    public void get(String url, Map<String, String> params, INetCallBack callback) {
        GetBuilder builder = OkHttpUtils.get().params(params).url(url);
        executeRequest(callback, builder.build());
    }

    @Override
    public void post(String url, Map<String, String> params, INetCallBack callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        builder.params(params);
        executeRequest(callback, builder.build());
    }

    private void executeRequest(INetCallBack callback, RequestCall build) {
        RequestCall requestCall = build;

        requestCall.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String content = response.body().string();
                Class rspClazz = callback.getType();
                if ("String".equals(rspClazz.getSimpleName())) {
                    return content;
                } else {
                    return JSON.parseObject(content, rspClazz);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                callback.onResponse(response);
            }
        });
    }
}
