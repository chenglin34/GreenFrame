package com.de.frame.protocol.callBack;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hisign.csipadminifree.utils.hisignUtil.HisignStringUtil;
import com.hisign.csipadminifree.utils.hisignUtil.HisignUUIDUtil;
import com.hisign.mpp.Constants;
import com.hisign.mpp.application.CrashApplication;
import com.hisign.mpp.log.SysLogBiz;
import com.hisign.mpp.encrypt.aes.AESCoder;
import com.hisign.mpp.encrypt.rsa.RSACoderForAndroid;
import com.hisign.mpp.entity.ResponseEntity.BaseResponseBody;
import com.hisign.mpp.entity.ResponseEntity.MyErrorResponse;
import com.hisign.mpp.entity.ResponseEntity.UploadResponseBody;
import com.hisign.mpp.model.DeviceInfoModel;
import com.hisign.mpp.model.UserModel;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * 描述：Json回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：程林
 * 创建时间:2016/12/1
 * 最后修改时间:2016/12/1
 */
public abstract class JsonCallback<T extends BaseResponseBody> extends AbsCallback<T> {

    protected Type type;

    /**
     * 指定公钥存放文件
     */
    private static String PUBLIC_KEY_FILE = "PublicKey.txt";

    /**
     * 缓存的公钥
     */
    byte[] publicKey = new byte[0];

    /**
     * AES加密密钥
     */
    private String enKey = "";

    /**
     * 初始化AES密钥
     */
    public void createKey() {
        this.enKey = AESCoder.getKey();
    }

    /**
     * 获取AES密钥
     *
     * @return
     */
    public String getEnKey() {
        return enKey;
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //主要用于在所有请求之前添加公共的请求头或请求参数，例如登录授权的 token,使用的设备信息等,可以随意添加,也可以什么都不传
        String RsaString = RSAEncypt(enKey);
        if(null!= DeviceInfoModel.getInstance().getDeviceInfo()&&null!=UserModel.getInstance().getCurrentUser()){
            if(!TextUtils.isEmpty(DeviceInfoModel.getInstance().getDeviceInfo().getToken())){
                request.headers("Token", DeviceInfoModel.getInstance().getDeviceInfo().getToken());
            }
            if(!TextUtils.isEmpty(DeviceInfoModel.getInstance().getDeviceInfo().getHisignPn())){
                request.headers("HisignPn", DeviceInfoModel.getInstance().getDeviceInfo().getHisignPn());
            }
            if(!TextUtils.isEmpty(UserModel.getInstance().getCurrentUser().getUserId())){
                request.headers("UserId", UserModel.getInstance().getCurrentUser().getUserId());
            }
        }

        request.headers("secretKey", RsaString)//秘钥
                .headers("systemId", Constants.SYSTEM_ID)//类型
                .headers("SerialNumber",  HisignUUIDUtil.simpleHex());//类型
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertSuccess(Response response) throws Exception {
        // 以下代码是通过泛型解析实际参数,泛型必须传
        if (type == null) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            type = params[0];
        }
        // 无数据类型
        if (type == Void.class) {
            throw new IllegalStateException("没有填写泛型参数!");
        }

        if(response.code() == 200){
            String status = response.headers().get("Status");
            String data = "";
            if(!HisignStringUtil.isEmpty(enKey)){
                data = AESCoder.decrypt(response.body().string(), enKey);
            }else{
                data = response.body().string();
            }
            if ("200".equals(status)) {
                //为适应服务器协议格式不统一，特此拼接，暂时方案
                if(data.startsWith("[")){
                    data="{\"data\":"+data+"}";
                }
                BaseResponseBody baseResponseBody = JSON.parseObject(data, type);
                return (T) baseResponseBody;
            }else if(type.toString().endsWith("UploadResponseBody")){
                //特殊处理如果是上传操作，服务器没有返回status，而是直接返回body里面包含的Flag标记的成功或者失败
                UploadResponseBody baseResponseBody = JSON.parseObject(data, type);
                if(baseResponseBody != null){
                   if("1".equals(baseResponseBody.getFlag())){
                       return (T) baseResponseBody;
                   }else{
                       throw new IllegalStateException("错误代码：99" + "，错误信息：" + baseResponseBody.getMessage());
                   }
                }
            } else {
                MyErrorResponse myResponse = JSON.parseObject(data, MyErrorResponse.class);
//                throw new IllegalStateException("错误代码：" + status + "，错误信息：" + myResponse.getMessage());
                throw new IllegalStateException(myResponse.getMessage());
            }
        }
        throw new IllegalStateException("错误代码：" + response.code() + "，错误信息：" +response.message());
    }

    /**
     * RSA加密方法
     *
     * @param srcData
     * @return
     */
    private String RSAEncypt(String srcData) {
        String result = "-1";
        try {
            if (publicKey.length == 0) {
                publicKey = RSACoderForAndroid.getPublicKeyForAndroid(PUBLIC_KEY_FILE, CrashApplication.getIns());
            }

            result = RSACoderForAndroid.encryptByPublicKey(srcData, publicKey);

        } catch (Exception e) {
            SysLogBiz.saveCrashInfoFile(SysLogBiz.getExceptionInfo(e));
        }
        return result;
    }
}