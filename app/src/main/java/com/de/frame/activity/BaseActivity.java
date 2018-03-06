package com.de.frame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.de.frame.application.AppManager;
import com.de.frame.presenter.BasePresenter;
import com.de.frame.contract.view.IBaseView;
import com.de.frame.widget.pagermanage.LoadingDialog;
import com.de.frame.widget.pagermanage.PageManager;

import java.lang.reflect.ParameterizedType;

/**
 * 描述：activity基类使用范型和反射获取Presenter对象
 * 实现自己的Activity时需要复写void initVariables();
 * initAttachView();initView(Bundle saveInstanceState);三个方法
 * 公司：北京海鑫科金高科技股份有限公司
 * 创建时间:2018/1/21
 * 最后修改时间:2018/1/21
 *
 * @author chenglin
 */
public abstract class BaseActivity<V extends IBaseView, P extends BasePresenter<V>> extends AppCompatActivity {

    /**
     * activity和service管理类
     */
    protected AppManager appmanager;

    protected P mPresenter;
    /**
     * 状态页面管理
     */
    protected PageManager pageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appmanager = AppManager.getAppManager();
        appmanager.addActivity(this);
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
        initView(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.initData();
        }
        initListener();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter.detachView();
            mPresenter = null;
        }
        if (null != appmanager) {
            appmanager.removeActivity(this);
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
        } catch (InstantiationException e) {
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
     * 返回要绑定的View本身，如果当前View（即 Activity 或 Fragment 不需要 V 和 P ，则直接返回 null）
     *
     * @return this
     */
    protected abstract V initAttachView();

    /**
     * 初始化控件，布局文件，加载事件方法(初始化数据前执行)
     *
     * @param saveInstanceState
     */
    protected abstract void initView(Bundle saveInstanceState);

    /**
     * 初始化listener(初始化数据后执行)
     */
    protected abstract void initListener();

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
        if (pageManager != null) {
            pageManager.showLoading(emptyMsg);
        }
    }

    public void showPagerManagerLoading() {
        if (pageManager != null) {
            pageManager.showLoading();
        }
    }

    /**
     * 设置状态页面为error
     *
     * @param errorMsg
     */
    public void showPagerManagerError(CharSequence errorMsg) {
        if (pageManager != null) {
            pageManager.showError(errorMsg);
        }
    }

    public void showPagerManagerError() {
        if (pageManager != null) {
            pageManager.showError();
        }
    }

    /**
     * 设置状态页面为显示内容
     */
    public void showPagerManagerContent() {
        if (pageManager != null) {
            pageManager.showContent();
        }
    }


    /**
     * 设置状态页面为空
     */
    public void showPagerManagerEmpty() {
        if (pageManager != null) {
            pageManager.showEmpty();
        }
    }

    /**
     * 设置状态页面为空
     */
    public void showPagerManagerEmpty(String str) {
        if (pageManager != null) {
            pageManager.showEmpty(str);
        }
    }

    /**
     * 开启浮动加载进度条
     */
    public void showProgressDialog() {
        LoadingDialog.showLoadingDialog(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        LoadingDialog.showLoadingDialog(this, msg);
    }

    /**
     * 停止浮动加载进度条
     */
    public void cancelLoadingDialog() {
        LoadingDialog.cancelLoadingDialog();
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        appmanager.AppExitWithoutIM(this);
    }

}
