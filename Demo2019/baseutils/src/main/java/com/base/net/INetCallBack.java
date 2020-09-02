package com.base.net;


/**
 * Created by ChenHui on 2019/3/31.
 */
interface INetCallBack<T> {

    void onError(Throwable e);

    void onResponse(T response);

    Class<?> getType();
}
