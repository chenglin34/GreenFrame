package com.de.frame.presenter;

import com.de.frame.contract.view.IBaseView;

/**
 * 类名：BaseNoModelPresenter
 * 描述：针对Presenter持有多个Model的情况
 * 公司：北京海鑫科金高科技股份有限公司
 * 版本：V1.0
 * 创建时间：2016-12-16
 * 最后修改时间：2016-12-16
 *
 * @author chenglin
 */
public abstract class BasePresenter<V extends IBaseView> {

    protected V mView;

    /**
     * 绑定view
     *
     * @param view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 反绑定view
     */
    public void detachView() {
        mView = null;
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 销毁（清除、线程、网络请求等）
     */
    public abstract void destroy();

}
