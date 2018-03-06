package com.de.frame.log;

import android.util.Log;

/**
* 类名:HsLog
* 描述:log日志输出类
* 公司:北京海鑫科金高科技股份有限公司 
* 作者:chenglin
* 版本:V1.0
* 创建时间:2016年3月2日
* 最后修改时间:2016年3月2日
*/
public class HsLog {

	private static boolean printLog = false;
    /**
     * 得到Exception所在代码的行数 如果没有行信息,返回-1
     */
    public static int d(Exception e, String message) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0 || !printLog) {
            return -1;
        }
        if(printLog) {
            Log.d(trace[0].getMethodName(), "[ "
                    + trace[0].getFileName() + " ]," + "[ line " + trace[0].getLineNumber() + " ]," + "[ " + message + " ]");
        }
        return trace[0].getLineNumber();
    }

    public static int w(Exception e, String message) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0 || !printLog ) {
            return -1;
        }
        Log.w(trace[0].getMethodName(), "[ "
                + trace[0].getFileName() + " ]," + "[ line " + trace[0].getLineNumber() + " ]," + "[ " + message + " ]");
        return trace[0].getLineNumber();
    }

    public static int e(Exception e, String message) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0 || !printLog) {
            return -1;
        }
        if(printLog) {
            Log.e(trace[0].getMethodName(), "[ "
                    + trace[0].getFileName() + " ]," + "[ line " + trace[0].getLineNumber() + " ]," + "[ " + message + " ]");
        }
        return trace[0].getLineNumber();
    }

    public static void d(String message) {
        if (printLog) {
            Log.d("HiSign", message);
        }
    }

    public static void d(String tag, String msg){
        if (printLog) {
            Log.d(tag, msg);
        }
    }
    
    public static void w(String message) {
        if (printLog) {
            Log.w("HiSign", message);
        }
    }
    
    public static void w(String tag, String msg){
        if (printLog) {
            Log.w(tag, msg);
        }
    }

}
