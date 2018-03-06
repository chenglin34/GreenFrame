package com.de.frame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisign.mpp.presenter.BaseNoModelPresenter;
import com.hisign.mpp.view.IBaseView;
import com.hisign.mpp.widget.LoadingDialog;
import com.hisign.mpp.widget.pagermanage.PageManager;

import java.lang.reflect.ParameterizedType;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：张绪飞
 * 创建时间:2016/11/22
 * 最后修改时间:2016/11/22
 */
public abstract class BaseFragment<V extends IBaseView, P extends BaseNoModelPresenter<V>> extends Fragment {

    protected P mPresenter;
    /**
     * 状态页面管理
     */
    protected PageManager pageManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        //通过泛型，依赖倒置获取具体的View对象本身
        V view = initAttachView();
        if (view != null) {
            // 反射获取具体的Presenter对象
            P presenter = getT(this, 1);
            if (presenter != null) {
                mPresenter = presenter;
                mPresenter.attachView(view);
            }
        }
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.initData();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter.detachView();
            mPresenter.unregisterEventBus();
            mPresenter = null;
        }
        if (pageManager != null) {
            pageManager = null;
        }
        super.onDestroy();
    }

    /**
     * 获取泛型类型
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (java.lang.InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    /**
     * 初始化变量，包括Intent带的变量和Activity里使用的变量
     */
    protected abstract void initVariables();

    /**
     * 返回要绑定的View本身，如果当前View（即 Activity 或 Fragment） 不需要 V 和 P ，则直接返回 null
     *
     * @return this
     */
    protected abstract V initAttachView();

    /**
     * 初始化控件，布局文件，加载事件方法
     *
     * @param inflater          布局渲染器
     * @param container         父容器
     * @param saveInstanceState 保存的状态
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState);


    /**
     * 初始化页面状态切换
     *
     * @param object                 activity或者fragment或者View
     * @param isShowLoadingOrContent
     */
    public void initPagerManager(Object object, boolean isShowLoadingOrContent, final PageManager.ReloadListener reloadListener) {
        pageManager = PageManager.init(object, isShowLoadingOrContent, reloadListener);
    }

    /**
     * 设置状态页面为loading
     *
     * @param emptyMsg
     */
    public void showPagerManagerLoading(CharSequence emptyMsg) {
        if (pageManager != null)
            pageManager.showLoading(emptyMsg);
    }

    public void showPagerManagerLoading() {
        if (pageManager != null)
            pageManager.showLoading();
    }

    /**
     * 设置状态页面为error
     *
     * @param errorMsg
     */
    public void showPagerManagerError(CharSequence errorMsg) {
        if (pageManager != null)
            pageManager.showError(errorMsg);
    }

    public void showPagerManagerError() {
        if (pageManager != null)
            pageManager.showError();
    }

    /**
     * 设置状态页面为显示内容
     */
    public void showPagerManagerContent() {
        if (pageManager != null)
            pageManager.showContent();
    }

    /**
     * 设置状态页面为空
     */
    public void showPagerManagerEmpty() {
        if (pageManager != null)
            pageManager.showEmpty();
    }

    /**
     * 开启浮动加载进度条
     */
    public void showProgressDialog() {
        LoadingDialog.showLoadingDialog(getActivity());
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        LoadingDialog.showLoadingDialog(getActivity(), msg);
    }

    /**
     * 停止浮动加载进度条
     */
    public void cancelLoadingDialog() {
        LoadingDialog.cancelLoadingDialog();
    }
    
    /**
     * 页面跳转到指定位置
     */
    public void reachLine(int line) {
    }
}
