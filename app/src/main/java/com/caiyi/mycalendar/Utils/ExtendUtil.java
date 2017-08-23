package com.caiyi.mycalendar.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

import java.util.List;

/**
 * @author Ren ZeQiang.
 * @since 2017/8/23.
 */

public class ExtendUtil {
    /**
     * 判断List集合是否为null/empty
     */
    public static boolean isListNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断List集合是否为null/empty
     */
    public static boolean isListNotNullOrEmpty(List list) {
        return !isListNullOrEmpty(list);
    }

    /**
     * 销毁WebView，避免内存泄露.
     */
    public static void destroyWebView(WebView webView) {
        destroyWebView(webView, false);
    }

    /**
     * 销毁webView，避免内存泄露.
     *
     * @param webView
     * @param clearCache 是否清空缓存。注意：所有WebView公用缓存，应用最后显示的WebView才可以使用.
     */
    public static void destroyWebView(WebView webView, boolean clearCache) {
        if (webView != null) {
            final ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.clearHistory();
            // NOTE: clears RAM cache, if you pass true, it will also clear the disk cache.
            // Probably not a great idea to pass true if you have other WebViews still alive.
            webView.clearCache(clearCache);

            webView.onPause();
            webView.removeAllViews();
            webView.destroyDrawingCache();

            webView.destroy();
            webView = null;
        }
    }

    /** 应用外打开链接 */
    public static boolean openUrlOutside(Activity activity, String url) {
        if (StringUtil.isNullOrEmpty(url)) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
