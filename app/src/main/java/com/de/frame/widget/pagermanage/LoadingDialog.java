package com.de.frame.widget.pagermanage;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.de.frame.R;
import com.de.frame.application.CrashApplication;


/**
 * 类名：LoadingDialog.java
 * 描述：弹窗浮动加载进度条
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 创建时间：2016/12/1
 * 最后修改时间：2016/12/1
 *
 * @author chenglin
 */

public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     *
     * @param activity 上下文
     * @param msg      对话框显示内容
     */
    public static Dialog showLoadingDialog(Activity activity, String msg) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            }
        }
        mLoadingDialog = new Dialog(activity, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    public static Dialog showLoadingDialog(Activity activity) {
        showLoadingDialog(activity, CrashApplication.getIns().getString(R.string.str_loading));
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void cancelLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }
}
