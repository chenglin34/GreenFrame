package com.de.frame.frame.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author chenglin
 * @date 2018/3/2
 */

public class OkGoProcessor implements IhttpProcessor {
    @Override
    public void post(String url, Map<String, Object> params, final ICallBack callBack) {
        OkGo.getInstance().cancelTag(this);
        OkGo.post(url).tag(this).upJson("").execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                callBack.onSuccess(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.onFailure(e.toString());
            }
        });
    }


    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {

    }
}
