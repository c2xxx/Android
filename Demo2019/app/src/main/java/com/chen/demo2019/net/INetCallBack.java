package com.chen.demo2019.net;


/**
 * Created by ChenHui on 2019/3/31.
 */
public interface INetCallBack<T> {

    public abstract void onError(Throwable e);

    public abstract void onResponse(T response);

    public Class<?> getType();
}
