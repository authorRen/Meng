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

    public interface GLOBAL_URL {
        String PHOTO_URL = "http://img1.touxiang.cn/uploads/20120428/28-051412_566.jpg";
    }

}
