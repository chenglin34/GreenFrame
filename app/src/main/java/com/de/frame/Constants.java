package com.de.frame;

import android.os.Environment;

import java.io.File;

/**
 * 常量类
 *
 * @author chenglin
 */
public class Constants {
    /**
     * SD卡目录
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 根目录
     */
    public static final String ROOT_PATH = SD_PATH + "/HisignMpp/";

    /**
     * 数据库目录
     */
    public static String DB_ROOT_PATH = ROOT_PATH + "dbs/";

    /**
     * 下载目录
     */
    public static String DL_ROOT_PATH = ROOT_PATH + "download/";

    /**
     * 缓存目录
     */
    public static String CACHE_ROOT_PATH = ROOT_PATH + "cache/";

    /**
     * 头像更改照片缓存目录
     */
    public static String CACHE_AVATAR = CACHE_ROOT_PATH + "avatar/";
    /**
     * 安装包存储目录
     */
    public static String APKS_PATH = ROOT_PATH + "apks/";

    /**
     * 文档目录
     */
    public static String DOCUMENTS_ROOT_PATH = ROOT_PATH + "documents/";

    /**
     * 应用名称
     */
    public static String APP_PRO_NAME = "移动警务";

    /**
     * 数据库名称
     */
    public static String DB_NAME = "HisignMpp.db";

    /**
     * 数据库全路径
     */
    public static String DB_PATH = DB_ROOT_PATH + DB_NAME;

    /**
     * 使用帮助文档文件名称
     */
    public static final String HELP_DOC_NAME = "微察e勘查V1.0使用手册.pdf";

    /**
     * 使用帮助文档路径
     */
    public static final String HELP_DOC_PATH = DOCUMENTS_ROOT_PATH + File.separator + HELP_DOC_NAME;

    /**
     * 此文件如果存在,就开启被捕获异常的日志输出
     */
    public static String CAUGHT_EXCEPTION_FILE = ROOT_PATH + "LogForCaughtException/";

    /**
     * 联网超时时间
     */
    public static long DEFAULT_MILLISECONDS = 30000;

    /**
     * 平台内各应用apk存放地址
     */
    public interface ApksPlatform {
        public final static String mainAPK = "mpp/";
    }

    public static final String SYSTEM_ID = "MPP";

}
