package com.caiyi.mycalendar.compant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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
import com.caiyi.mycalendar.Utils.ExtendUtil;
import com.caiyi.mycalendar.Utils.NetworkUtil;
import com.caiyi.mycalendar.Utils.StringUtil;
import com.caiyi.mycalendar.base.BaseActivity;
import com.caiyi.mycalendar.log.Logger;

/**
 * @author Ren ZeQiang
 * @since 2017/7/27
 */
public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";
    private WebView mWebView;
    private ProgressBar mProgressBar;
    /** 浏览器url */
    public static final String WEBPAGE_URL = "WEBPAGE_URL";
    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    protected void initView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        initWebSettings(mWebView);
        mWebView.setWebViewClient(new DefaultWebViewClient());
        mWebView.setWebChromeClient(new DefaultWebChromeClient());

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    protected void initData() {
        String loadUrl = mUrl;
        mWebView.loadUrl(loadUrl);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealIntent(intent);
    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        dealIntent(intent);
    }

    private void dealIntent(Intent intent) {
        if (null == intent) {
            return;
        }
        mUrl = intent.getStringExtra(WEBPAGE_URL);
    }

    @SuppressLint("setJavaScriptEnabled")
    private void initWebSettings(WebView mWebView) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webView的大小
        settings.setLoadWithOverviewMode(true); //缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面的前提。
        settings.setBuiltInZoomControls(true); //设置内容的缩放控件。若为false，则该webView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //解决网页是http但是网页内的图片是https,图片不显示的问题.请求要保持统一
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (NetworkUtil.isNetworkConnected()) { //对Page导航时才有效。比如按返回键回到上一个页面的情况.
            settings.setCacheMode(WebSettings.LOAD_DEFAULT); //默认的使用模式
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不从网络加载数据，只从缓存加载数据。
        }
    }

    protected class DefaultWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.log(Logger.INFO, TAG, "onPageStarted: " + url);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.log(Logger.INFO, TAG, "onPageFinished: " + url);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    protected class DefaultWebChromeClient extends WebChromeClient {
        //点击网页内的链接时才会执行.
        @Override public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 0 && newProgress < 100) {
                mProgressBar.setProgress(newProgress);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            super.onProgressChanged(view, newProgress);
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
                    ExtendUtil.openUrlOutside(mActivity, url);
                    return true;
                }
            });
            return true;
        }
    }

    public static void startWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBPAGE_URL, url);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExtendUtil.destroyWebView(mWebView);
    }
}
