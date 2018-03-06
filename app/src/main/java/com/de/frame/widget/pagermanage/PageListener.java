package com.de.frame.widget.pagermanage;

import android.view.View;

/**
 * 类名：PageListener.java
 * 描述：页面状态监听
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 创建时间：2018/1/1
 * 最后修改时间：2018/1/1
 * @author chenglin
 */
public abstract class PageListener {


    public abstract void setRetryEvent(View retryView);

    public void setLoadingEvent(View loadingView) {
    }

    public void setEmptyEvent(View emptyView) {
    }

    public int generateLoadingLayoutId() {
        return PageManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return PageManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return PageManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateRetryLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != PageManager.NO_LAYOUT_ID || generateLoadingLayout() != null) {
            return true;
        }
        return false;
    }

    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != PageManager.NO_LAYOUT_ID || generateRetryLayout() != null) {
            return true;
        }
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != PageManager.NO_LAYOUT_ID || generateEmptyLayout() != null) {
            return true;
        }
        return false;
    }


}