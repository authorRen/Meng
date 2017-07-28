package com.caiyi.mycalendar.compant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.Utils.StringUtil;
import com.caiyi.mycalendar.log.Logger;

/**
 * @author Ren ZeQiang
 * @since 2017/7/27
 */
public class WebActivity extends BaseActivity {

    public static final String TAG = "WebActivity";
    private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper());
    /** 浏览器title部分. */
    public static final String WEBPAGE_TITLE = "WEBPAGE_TITLE";
    /** 浏览器url */
    public static final String WEBPAGE_URL = "WEBPAGE_URL";
    /** postStr */
    public static final String WEBPAGE_POST_STR = "WEBPAGE_POST_STR";
    /** 是否带分享 */
    public static final String WEBPAGE_IS_SHARE = "WEBPAGE_IS_SHARE";
    /** 亲亲小宝订单编号. */
    public static final String WEBPAGE_IS_QQXB = "WEBPAGE_IS_QQXB";
    /** 亲亲小宝成功页面. */
    public static final String QQXB_SUCCESS_PAGE_URL = "http://andgjj.youyuwo.com/user/qqxb_success.go";
    /** 进度条最大值. */
    protected static final int PROGRESS_FINISHED = 100;
    /**
     * 显示网页内容
     */
    protected WebView mWebView;
    /** 地址 */
    private String mUrl;
    /** 网页内容的标题 */
    protected String mTitle;
    /** 如果有post数据，则改为posturl加载页面. */
    private String mPostStr;
    /**
     * 加载网页时显示的progressbar
     */
    protected ProgressBar mProgressBar;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealIntent(intent);
    }

    protected void initContentView() {
        setContentView(R.layout.activity_web);
    }

    @SuppressLint("setJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();

        dealIntent(getIntent());
        initView();

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        // settings.setSupportMultipleWindows(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        // settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.requestFocus();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new DefaultWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == PROGRESS_FINISHED) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    mTitle = title;
                    setTitle(title);
                }
            }

            // 处理类"window.open('http://1.9188.com/apk/adb.apk')" 这类url下载
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(WebActivity.this);
                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } catch (Exception e) {
                            // null
                        }
                        return true;
                    }
                });
                return true;
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        String loadUrl = mUrl;
        if (TextUtils.isEmpty(mPostStr)) {
            mWebView.loadUrl(loadUrl);
        } else {
            mWebView.postUrl(loadUrl, mPostStr.getBytes());
        }
    }


    private void dealIntent(Intent intent) {
        if (null == intent) {
            return;
        }
        mTitle = intent.getStringExtra(WEBPAGE_TITLE);
        mUrl = intent.getStringExtra(WEBPAGE_URL);
        mPostStr = intent.getStringExtra(WEBPAGE_POST_STR);
    }

    /**
     *
     * webview的设置
     *
     * @since 2014年9月5日
     */
    private class DefaultWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.log(Logger.INFO, TAG, "open url: "+url);
            if (!StringUtil.isNullOrEmpty(url) && url.startsWith("tmast://")) {//腾讯应用宝下载链接
                if (!openTencentMarket(WebActivity.this, url)) {
                    showToast("请下载腾讯应用宝");
                }
                return true;
            }
            if (url.startsWith("https") || url.startsWith("http")) {
                try {
                    mWebView.loadUrl(url);
                } catch (Exception e) {
                    //Log.e(TAG, e.toString());
                }
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    // null
                }
            }
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            Logger.log(Logger.INFO, TAG, "finish url: " + url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    /** 打开应用宝。tmast://链接 */
    public boolean openTencentMarket(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private class QQXBWebViewClient extends WebViewClient {

        private int linkJumpCount = 0;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.log(Logger.INFO, TAG, "QQXBWebView page url: "+url);
            try {
                linkJumpCount++;
                if (!TextUtils.isEmpty(url) && linkJumpCount == 1) {
                    if (url.contains("?")) {
                        mWebView.loadUrl(url + "&" + mPostStr);
                    } else {
                        mWebView.loadUrl(url + "?" + mPostStr);
                    }
                } else {
                    mWebView.loadUrl(url);
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (TextUtils.isEmpty(mTitle)) {
            toolbar.setTitle(getString(R.string.app_name));
        } else {
            toolbar.setTitle(mTitle);
        }
        toolbar.setNavigationIcon(R.drawable.gjj_web_close);
        setSupportActionBar(toolbar);

        mWebView = (WebView) findViewById(R.id.alipay_web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @return 是否需要返回到首页.
     */
    private boolean shouldGotoMain() {
        if (!hasActivity("FundHomeActivity")) {
            Intent intent = new Intent(this, AroundServiceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void finish() {
        // ZoomButton 自动隐藏是一个渐变的过程，所以在逐渐消失的过程中如果调用了父容器的destroy方法，就会导致Leaked
        // 在finish掉此activity时，把ZoomButton remove掉
        ((ViewGroup) getWindow().getDecorView()).removeAllViews();
        shouldGotoMain();
        super.finish();
    }

    /**
     * @param isShare true:底部有分享，去掉顶部的下载公积金应用banner
     */
    public static void startWebActivity(Context context, String title, String url, boolean isShare) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBPAGE_TITLE, title);
        intent.putExtra(WEBPAGE_URL, url);
        intent.putExtra(WEBPAGE_IS_SHARE, isShare);
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     */
    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBPAGE_TITLE, title);
        intent.putExtra(WEBPAGE_URL, url);
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     */
    public static void startWebActivity(Context context, String title, String url, String postStr) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBPAGE_TITLE, title);
        intent.putExtra(WEBPAGE_URL, url);
        intent.putExtra(WEBPAGE_POST_STR, postStr);
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     */
    public static void startQQXBWebActivity(Context context, String title, String url, String postStr) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBPAGE_TITLE, title);
        intent.putExtra(WEBPAGE_URL, url);
        intent.putExtra(WEBPAGE_POST_STR, postStr);
        intent.putExtra(WEBPAGE_IS_QQXB, true);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }

}
