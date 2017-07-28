package com.caiyi.mycalendar.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.caiyi.mycalendar.compant.MyApplication;

/**
 * 网络相关工具类.
 * Created by HuoGuangxu on 2016/10/21.
 */

public class NetworkUtil {
    private NetworkUtil() {
    }

    /**
     * 判断网络是否可用.
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 判断是否是wifi
     */
    public static boolean isWifiAvailable() {
        return GlobalConstants.NETWORK_TYPE.TYPE_WIFI == getNetWorkType();
    }

    /**
     * 获得当前网络类型.
     */
    public static int getNetWorkType() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return GlobalConstants.NETWORK_TYPE.TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return GlobalConstants.NETWORK_TYPE.TYPE_MOBILE;
            }
        }
        return GlobalConstants.NETWORK_TYPE.TYPE_UNKNOWN;
    }
}
