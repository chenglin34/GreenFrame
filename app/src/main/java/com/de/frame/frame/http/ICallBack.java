package com.de.frame.frame.http;

/**
 * @author chenglin
 * @date 2018/3/2
 */

public interface ICallBack {
    /**
     * 成功方法
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 失败方法
     *
     * @param e
     */
    void onFailure(String e);
}
