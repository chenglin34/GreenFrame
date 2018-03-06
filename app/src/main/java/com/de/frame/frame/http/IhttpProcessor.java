package com.de.frame.frame.http;

import java.util.Map;

/**
 * @author chenglin
 * @date 2018/3/2
 */

public interface IhttpProcessor {
    /**
     * @param url
     * @param params
     * @param callBack
     */
    void post(String url, Map<String, Object> params, ICallBack callBack);

    void get(String url, Map<String, Object> params, ICallBack callBack);

}
