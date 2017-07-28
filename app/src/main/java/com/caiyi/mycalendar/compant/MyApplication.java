package com.caiyi.mycalendar.compant;

import android.app.Application;
import android.content.Context;
import android.util.Config;

import com.caiyi.mycalendar.BuildConfig;
import com.caiyi.mycalendar.Okhttp.OkHttpProvider;
import com.caiyi.mycalendar.Utils.AppUtils;
import com.caiyi.mycalendar.log.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @author Ren ZeQiang
 * @since 2017/7/26
 */
public class MyApplication extends Application {
    public static final String TAG = "MyApplication";
    /**
     * 全局Context
     */
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //解决多进程下Application#onCreate()执行多次的问题。
        if (!isMainProgress()) {
            return;
        }
        mAppContext = getApplicationContext();
        initFresco();
        Logger.init(TAG).showLog(BuildConfig.DEBUG).hideThreadInfo().methodCount(1);
        Logger.i("CaiyiFund#onCreate");
    }

    private void initFresco() {
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, OkHttpProvider.getInstance().getmOkHttpClient()).build();
        Fresco.initialize(this, config);
    }

    /** 判断是否是主进程.当前子进程有:push、remote(百度定位)、LeakCanary */
    private boolean isMainProgress() {
        return AppUtils.getCurrentProgressName(this).equals(getPackageName());
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
