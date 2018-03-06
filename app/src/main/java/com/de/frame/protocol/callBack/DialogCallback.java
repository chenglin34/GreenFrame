package com.de.frame.protocol.callBack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.Window;

import com.hisign.mpp.entity.ResponseEntity.BaseResponseBody;
import com.lzy.okgo.request.BaseRequest;

/**
 * 描述：Dialog回调不需要界面处理阻断进度
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：程林
 * 创建时间:2016/12/1
 * 最后修改时间:2016/12/1
 */
public abstract class DialogCallback<T extends BaseResponseBody> extends JsonCallback<T> {

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        //网络请求结束后关闭对话框
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e1) {
//            SysLogBiz.saveCrashInfoFile(SysLogBiz.getExceptionInfo(e1));
        }
    }
}
