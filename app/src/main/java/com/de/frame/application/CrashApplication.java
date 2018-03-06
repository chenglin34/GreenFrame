package com.de.frame.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.de.frame.Constants;
import com.de.frame.exception.CrashHandler;
import com.de.frame.log.SysLogBiz;
import com.de.frame.widget.pagermanage.PageManager;
import com.lzy.okgo.OkGo;

/**
 * @author chenglin
 */
public class CrashApplication extends Application {
    private static CrashApplication crashApplication = null;
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        crashApplication = this;
        init();
    }

    public void init() {
        //这里必须在super.onCreate方法之后，顺序不能变

        CrashHandler crashHandlerBiz = CrashHandler.getInstance();
        crashHandlerBiz.init(getApplicationContext());
        //初始化联网框架
        initOkGO();
        //初始化加载loading状态页面
        PageManager.initInApp(this);
        //启动后台服务
        initService();
        //注册Activity生命周期监听
        //registerActivityLifecycleCallbacks();
    }

    /**
     * 初始化OK GO
     */
    public void initOkGO() {
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    //.debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(Constants.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(Constants.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(Constants.DEFAULT_MILLISECONDS);   //全局的写入超时时间
            //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
            //.setCacheMode(CacheMode.NO_CACHE)
            //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
            //.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
        } catch (Exception e) {
            SysLogBiz.saveCrashInfoFile(SysLogBiz.getExceptionInfo(e));
        }
    }

    /**
     * 启动服务
     */
    private void initService() {
        //启动常驻服务
//        Intent intent2 = new Intent(this, ForeverService.class);
//        startService(intent2);
    }

    public static CrashApplication getIns() {
        return crashApplication;
    }

    /**
     * 分割 Dex 支持
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
