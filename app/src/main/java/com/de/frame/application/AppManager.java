package com.de.frame.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.de.frame.activity.BaseActivity;

import java.util.Stack;

/**
 * @类名 AppManager
 * @描述  自定义activity管理类
 * @公司  北京海鑫科金高科技股份有限公司
 * @作者  杨培尧
 * @版本 V1.0
 * @创建时间 2016年6月15日
 * @最后修改时间 2016年6月15日
 */
public class AppManager {

	public static Stack<Activity> activityStack;
	public static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		if (!activityStack.contains(activity)) {
			activityStack.add(activity);
		}
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		if(activityStack!=null&&activityStack.size()>0) {
			Activity activity = activityStack.lastElement();
			return activity;
		}else{
			return null;
		}
	}

	/**
	 * 返回到指定的Activity并且清除Stack中其位置以上的所有Activity
	 * @param clz 指定的Activity
	 * @return 该Activity
	 */
	public void getTopWith(Class<?> clz) {
		int postion = 0;
		if (null != clz) {
			if (null != activityStack && activityStack.size() > 0) {
				for (int i = 0; i < activityStack.size(); i++) {
					if (activityStack.get(i).getClass().getName().contains(clz.getName())) {
						postion = i;
					}
				}
				int num = activityStack.size() - postion - 1;
				if (num > 0) {
					for (int i = 0; i < num; i++) {
						if (!activityStack.empty()) {
							finishActivity(activityStack.pop());
						}
					}
				}
			}
		}
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 从栈中删除当前Activity（堆栈中最后一个压入的），并不finish
	 */
	public void removeActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
		}
	}
	
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 判断所有的Activity是否处于onStop状态,常用于应用密码锁
	 */
	public boolean isOnStop() {
		for (int i = 0; i < activityStack.size(); i++) {
			BaseActivity activity = (BaseActivity) activityStack.get(i);
//			if (!activity.isOnStop) {
//				return false;
//			}
		}

		return true;
	}

	/**
	 * 退出应用程序
	 */
	@SuppressLint("MissingPermission")
    @SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			System.exit(0);
		}
	}
	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExitAndRestart(Context mContext) {
		AppManager.getAppManager().finishAllActivity();
		android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
	}

	/**
	 * 退出应用程序不结束IM模块
	 */
	@SuppressLint("MissingPermission")
    @SuppressWarnings("deprecation")
	public void AppExitWithoutIM(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			System.exit(0);
		}
	}
}