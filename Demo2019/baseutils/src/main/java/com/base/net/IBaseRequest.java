package com.base.net;

import java.util.Map;

/**
 * Created by ChenHui on 2019/3/31.
 */
public interface IBaseRequest {
    /**
     * get 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    void get(String url, Map<String, String> params, INetCallBack callback);

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    void post(String url, Map<String, String> params, INetCallBack callback);
}
