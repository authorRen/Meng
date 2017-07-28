package com.caiyi.mycalendar.Utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 全局常量
 * Created by HuoGuangxu on 2016/9/19.
 */

public class GlobalConstants {
    /** 公积金 */
    public static final int FUNDS = 0;
    /** 社保 */
    public static final int SS = 1;

    @IntDef({FUNDS, SS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BusinessType {
    }

    /** 养老保险 */
    public static final int ENDOWMENT = 1;
    /** 医疗保险 */
    public static final int MEDICAL = 2;
    /** 失业保险 */
    public static final int UNEMPLOYMENT = 3;
    /** 生育保险 */
    public static final int MATERNITY = 4;
    /** 工伤保险 */
    public static final int WORK_INJURY = 5;
    @IntDef({ENDOWMENT, MEDICAL, UNEMPLOYMENT, MATERNITY, WORK_INJURY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SS {
    }

    public interface HTTP_RESPONSE_CODE {
        /** 请求范围不符合要求 */
        int NON_CONFORMANCE = 416;
        /** 文件未修改 */
        int UNMODIFIED = 304;
        /** 部分内容 */
        int PARTIAL_CONTENT = 206;
    }

    public interface INTENT_PARAMS_KEY {
        /** 通过接口跳转本地需要的参数 */
        String API_PARAMS = "API_PARAMS";
        
        String CITY_CODE = "CITY_CODE";
        String GJJ_LOC_NAME = "GJJ_LOC_NAME";
        String APP_DOWNLOAD_UPDATE = "APP_DOWNLOAD_UPDATE";
        String CITY_LIST = "CITY_LIST";
        String CITY_SELECT = "CITY_SELECT";
        String CITY_SEARCH_FROM = "CITY_SEARCH_FROM";
        String REFRESH = "REFRESH";
        String GJJ_REMIND = "GJJ_REMIND";
    }

    //在线客服使用标志.0:websocket;1:友盟
    public interface OnlineServiceType {
        int SERVICE_SOCKET = 0;
        int SERVICE_UMENG = 1;
    }

    //网络状态
    public interface NETWORK_TYPE {
        /** 未知网络 */
        int TYPE_UNKNOWN = -1;
        /** See:{@link android.net.ConnectivityManager#TYPE_WIFI wifi网络} */
        int TYPE_WIFI = 0;
        /** See:{@link android.net.ConnectivityManager#TYPE_MOBILE 手机网络} */
        int TYPE_MOBILE = 1;
    }

    //SharedPreference的key
    public interface SP_PARAMS_KEY {
        /** app更新是否下载完成 */
        String APK_DOWNLOAD_SUCCESS = "APK_DOWNLOAD_SUCCESS";
        /** app下载的版本号 */
        String APK_DOWNLOAD_VERSION = "APK_DOWNLOAD_VERSION";
        //是否显示过刷新账户向导 统计次数，第二次再弹出
        String REFRESH_ACCOUNT_GUIDE_COUNT = "REFRESH_ACCOUNT_GUIDE_COUNT";
        /** 社区搜索历史 */
        String FORUM_SEARCH_HISTORY = "FORUM_SEARCH_HISTORY";
        /** Splash版本 */
        String SPLASH_VERSION = "SPLASH_VERSION";
        /**
         * 每个手机号的登录形式
         * {@link com.caiyi.utils.LoginHelper.LoginState LoginState}
         */
        String LOGIN_STATE = "LOGIN_STATE";

        /** 登录框,记录每次输入的手机号 */
        String LOCAL_LOGIN_BOX_PHONE_KEY = "LOCAL_LOGIN_BOX_PHONE_KEY";

        /** 登录成功用户的手机号. */
        String LOCAL_USER_PHONE_KEY = "LOCAL_USER_PHONE_KEY";

        /** 保存测试域名 */
        String DOMAIN_FOR_DEBUG = "DOMAIN_FOR_DEBUG";
        
        String PRINT_REQUEST_HEADER = "PRINT_REQUEST_HEADER";
        
        String PRINT_RESPONSE_HEADER = "PRINT_RESPONSE_HEADER";

        String INTERCEPTOR_LOG_DISABLE = "INTERCEPTOR_LOG_DISABLE";

        /** 默认账户,最多两个(一个公积金和一个社保) */
        String ACCOUNT_INFO = "ACCOUNT_INFO";

        /** app版本更新信息 */
        String APP_UPDATE_INFO = "APP_UPDATE_INFO";
        /** 保存提醒的日期 */
        String REMIND_DAY = "REMIND_DAY";
        /** 保存提醒的时刻 */
        String REMIND_HOUR = "REMIND_HOUR";
        /** 保存提醒的开关状态 */
        String IS_OPEN = "IS_OPEN";

    }

    //网络缓存的key
    public interface CACHE_KEY {
        String SPLASH = "SPLASH";
        String FORUM_TAGS = "FORUM_TAGS";
        String HOME_ENTRY = "HOME_ENTRY";
        String HOME_BANNER = "HOME_BANNER";
        String HOME_LOAN_CREDIT = "HOME_LOAN_CREDIT";
        String HOME_NOTICES = "HOME_NOTICES";
        String ACCOUNT_INFO = "ACCOUNT_INFO";
        String FORUM_MSG = "FORUM_MSG";
        String CITY_LIST = "CITY_LIST";
        String CITY_CODE_LIST = "CITY_CODE_LIST";
        String SERVICE_POLICY = "SERVICE_POLICY";
        String SERVICE_AROUND = "SERVICE_AROUND";
        String SERVICE_ENTRY = "SERVICE_ENTRY";
        String SERVICE_BANNER = "SERVICE_BANNER";
    }

    public interface APP_UPDATE_TYPE {
        int NON_FORCE = 0;
        int FORCE = 1;
    }
}
