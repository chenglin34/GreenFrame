package com.de.frame.listener;

import okhttp3.Response;

/**
 * 类名：BaseListener
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：huangyu
 * 版本：V1.0
 * 创建时间：2016-12-6
 * 最后修改时间：2016-12-6
 */
public interface BaseListener {
    void onError(Response response, Exception e);
}