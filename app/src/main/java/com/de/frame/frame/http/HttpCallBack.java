package com.de.frame.frame.http;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author chenglin
 * @date 2018/3/2
 */

public abstract class HttpCallBack<Result> implements ICallBack {
    @Override
    public void onSuccess(String result) {
        Class<?> clz = analysisClassInfo(this);
        Result objResult = (Result) JSON.parseObject(result, clz);
        onSuccess(objResult);
    }

    /**
     * 成功通知
     *
     * @param objResult
     */
    protected abstract void onSuccess(Result objResult);

    /**
     * 失败通知
     *
     * @param e
     */
    protected abstract void onError(String e);

    public static Class<?> analysisClassInfo(Object object) {
        Type getType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) getType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    @Override
    public void onFailure(String e) {
        onError(e);
    }
}
