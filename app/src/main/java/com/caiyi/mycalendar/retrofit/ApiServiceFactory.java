package com.caiyi.mycalendar.retrofit;



import com.caiyi.mycalendar.BuildConfig;
import com.caiyi.mycalendar.Okhttp.OkHttpProvider;
import com.caiyi.mycalendar.Utils.Config;
import com.caiyi.mycalendar.Utils.SPUtils;
import com.caiyi.mycalendar.Utils.StringUtil;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口服务工厂类.
 *
 * @author HuoGuangXu
 * @since 2017/5/30.
 */

public class ApiServiceFactory {

    //缓存Retrofit
    private final HashMap<String, Retrofit> mRetrofits;

    private ApiServiceFactory() {
        mRetrofits = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final ApiServiceFactory INSTANCE = new ApiServiceFactory();
    }

    public static ApiServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        return getRetrofit().create(serviceClass);
    }

    public <S> S createService(final String baseUrl, Class<S> serviceClass) {
        return getRetrofit(baseUrl).create(serviceClass);
    }

    public Retrofit getRetrofit() {
        return getRetrofit(getApiUrl());
    }

    public Retrofit getRetrofit(final String baseUrl) {
        if (StringUtil.isNullOrEmpty(baseUrl)) {
            throw new RuntimeException("Base URL required.");
        }
        if (mRetrofits.containsKey(baseUrl)) {
            return mRetrofits.get(baseUrl);
        } else {
            Retrofit retrofit = buildRetrofit(baseUrl);
            mRetrofits.put(baseUrl, retrofit);
            return retrofit;
        }
    }

    private Retrofit buildRetrofit(final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpProvider.getInstance().getmOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static String getApiUrl() {
        if (BuildConfig.DEBUG) {
            return StringUtil.isNotNullOrEmpty(Config.DOMAIN) ? Config.DOMAIN : Config.DEBUG_DOMAIN;
        } else {
            final String updatedDomain = SPUtils.getString(Config.SP_DOMAIN);//域名下发
            return StringUtil.isNotNullOrEmpty(updatedDomain) ? updatedDomain : Config.RELEASE_DOMAIN;
        }
    }
}
