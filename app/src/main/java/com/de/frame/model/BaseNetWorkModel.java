package com.de.frame.model;

import com.alibaba.fastjson.JSON;
import com.hisign.mpp.encrypt.aes.AESCoder;
import com.hisign.mpp.entity.requestEntity.BaseRequestBody;
import com.hisign.mpp.listener.BaseListener;
import com.hisign.mpp.log.SysLogBiz;
import com.hisign.mpp.protocol.callBack.DialogCallback;
import com.hisign.mpp.protocol.callBack.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * 描述：联网model基类
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：程林
 * 创建时间:2016/11/29
 * 最后修改时间:2016/11/29
 */
public abstract class BaseNetWorkModel<REQ extends BaseRequestBody, L extends BaseListener> extends BaseModel {
    
    public REQ mRequestBody;
    public L mListener;
    
    public BaseNetWorkModel() {
        mRequestBody = getT(this, 0);
    }
    public void setListener(L listener) {
        this.mListener = listener;
    }
    /**
     * 发送json的POST请求，自带系统阻断对话框
     *
     * @param url         协议地址
     * @param requestBody 请求体
     * @param callback    回调实体
     */
    protected void postJson(String url, REQ requestBody, DialogCallback callback) {
        callback.createKey();//生成初始化密钥
        OkGo.getInstance().cancelTag(this);
        OkGo.post(url).tag(this).upJson(assembleRequest(requestBody, callback.getEnKey())).execute(callback);
    }

    /**
     * 发送json的POST请求，自带系统阻断对话框
     *
     * @param url         协议地址
     * @param requestBody 请求体
     * @param callback    回调实体
     */
    protected void postJson(String url, ArrayList<String> requestBody, DialogCallback callback) {
        callback.createKey();//生成初始化密钥
        OkGo.getInstance().cancelTag(this);
        OkGo.post(url).tag(this).upJson(assembleRequest(requestBody, callback.getEnKey())).execute(callback);
    }
    
    protected void postJson(String url, ArrayList<String> requestBody, JsonCallback callback) {
        callback.createKey();//生成初始化密钥
        OkGo.getInstance().cancelTag(this);
        OkGo.post(url).tag(this).upJson(assembleRequest(requestBody, callback.getEnKey())).execute(callback);
    }

    /**
     * 发送json的POST请求
     *
     * @param url         协议地址
     * @param requestBody 请求体
     * @param callback    回调实体
     */
    protected void postJson(String url, REQ requestBody, JsonCallback callback) {
        callback.createKey();//生成初始化密钥
        OkGo.getInstance().cancelTag(this);
        OkGo.post(url).tag(this).upJson(assembleRequest(requestBody, callback.getEnKey())).execute(callback);
    }
    
    /**
     * 同步发送json的POST请求
     *
     * @param url         协议地址
     * @param requestBody 请求体
     */
    protected Response postJson(String url, REQ requestBody) throws IOException {
        OkGo.getInstance().cancelTag(this);
        return OkGo.post(url).tag(this).upJson(assembleRequest(requestBody, AESCoder.getKey())).execute();
    }

    /**
     * 发送下载文件请求
     *
     * @param url      协议地址
     * @param callback 回调实体
     */
    protected void file(String url, AbsCallback callback,Object tag) {
        OkGo.get(url).tag(tag).execute(callback);
    }

    /**
     * 发送上传文件请求
     *
     * @param url      协议地址
     * @param callback 回调实体
     */
    protected void upload(String url, String key, File file , JsonCallback callback) {
        OkGo.post(url).tag(this).params("file",file).execute(callback);
    }

    /**
     * 取消请求
     *
     */
    public void cancel() {
        OkGo.getInstance().cancelTag(this);
    }

    /**
     * 取消请求
     *
     */
    public void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 封装请求体
     *
     * @param requestBody 请求体
     * @return JsonString 加密后
     */
    private String assembleRequest(REQ requestBody, String key) {
        String srcData = JSON.toJSON(requestBody).toString();
        String EnString = aesEncrypt(srcData, key);
        return EnString;
    }

    /**
     * 封装请求体
     *
     * @param requestBody 请求体
     * @return JsonString 加密后
     */
    private String assembleRequest(ArrayList<String> requestBody, String key) {
        String srcData = JSON.toJSON(requestBody).toString();
        String EnString = aesEncrypt(srcData, key);
        return EnString;
    }

    /**
     * 加密数据方法
     *
     * @param srcData 数据
     * @param key     密钥
     * @return 加密后数据
     */
    private String aesEncrypt(String srcData, String key) {
        String result = "";
        try {
            result = AESCoder.encrypt(srcData, key);
        } catch (Exception e) {
            SysLogBiz.saveCrashInfoFile(SysLogBiz.getExceptionInfo(e));
        }
        return result;
    }



    /**
     * 获取泛型类型
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    protected static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }
}
