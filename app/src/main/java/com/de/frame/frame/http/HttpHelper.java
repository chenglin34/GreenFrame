package com.de.frame.frame.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenglin
 * @date 2018/3/2
 */

public class HttpHelper implements IhttpProcessor {
    private static volatile HttpHelper instance = null;

    private IhttpProcessor mIhttpProcessor = null;

    private Map<String, Object> mParams = null;

    private HttpHelper() {
        mParams = new HashMap<>();
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    public void init(IhttpProcessor httpProcessor) {
        mIhttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        mIhttpProcessor.post(url, params, callBack);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {
        mIhttpProcessor.get(url, params, callBack);
    }
}
