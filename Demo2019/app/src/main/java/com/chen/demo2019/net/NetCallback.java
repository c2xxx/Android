package com.chen.demo2019.net;


import java.lang.reflect.ParameterizedType;

/**
 * Created by chenhui on 2016/4/15.
 * 网络层的回调
 */
public abstract class NetCallback<T> implements INetCallBack<T> {

    private Class<T> clazz;

    public NetCallback() {
        ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public void onError(Throwable e) {

    }

    public abstract void onResponse(T response);

    public Class<?> getType() {
        return this.clazz;
    }


    /**
     * 是否打印LOG
     *
     * @return
     */
    public boolean isPrintLog() {
        return true;
    }
}
