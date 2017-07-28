package com.caiyi.mycalendar.Okhttp;


import com.caiyi.mycalendar.BuildConfig;
import com.caiyi.mycalendar.compant.MyApplication;
import com.caiyi.mycalendar.Utils.StringUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * OkHttpClient 提供者
 *
 * @author Ren ZeQiang
 * @since 2017/7/26
 */
public class OkHttpProvider {
    public static final long DEFAULT_CONNECT_TIMEOUT = 15;
    public static final long DEFAULT_WRITE_TIMEOUT = 20;
    public static final long DEFAULT_READ_TIMEOUT = 20;

    private OkHttpClient mOkHttpClient;

    private OkHttpProvider() {
        initOkHttp();
    }

    private static class SingletonHolder {
        private static final OkHttpProvider INSTANCE = new OkHttpProvider();
    }

    public static OkHttpProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(createDir(MyApplication.getAppContext().getCacheDir() + "/okhttp"), 10 * 1024 * 1024L));

        builder.addInterceptor(new CommonInterceptor());
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(new LoggerInterceptor());
        }

        mOkHttpClient = builder.build();
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public static File createDir(String dir) {
        if (StringUtil.isNullOrEmpty(dir)) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null;
            }
        }
        return dirFile;
    }
}
