package com.caiyi.mycalendar.Okhttp;

import com.caiyi.mycalendar.Utils.AppUtils;
import com.caiyi.mycalendar.Utils.Config;
import com.caiyi.mycalendar.Utils.SPUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共的Okhttp 拦截器，用于统一添加请求参数。
 *
 * @author Ren ZeQiang
 * @since 2017/7/26
 */
public class CommonInterceptor implements Interceptor {

    private static final String VERSION_NAME = "2.8.0";
    private static final String SOURCE = "10001";
    private static final String TOKEN = "TOKEN";
    private static final String APPID = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newRequestBuilder = originalRequest.newBuilder();

        /** 统一添加参数 header */
        newRequestBuilder.header("releaseVersion", VERSION_NAME)
                .header("source", SOURCE)
                .header("appId", APPID)
                .header("token", TOKEN);

        //最好将参数放在请求头中，适用于get,post,delete,put
        // GET参数
        if ("get".equalsIgnoreCase(originalRequest.method())) {
            HttpUrl.Builder httpUrlBuilder = originalRequest.url().newBuilder();

            httpUrlBuilder.addQueryParameter("releaseVersion", VERSION_NAME)
                    .addQueryParameter("source", SOURCE)
                    .addQueryParameter("appId", APPID)
                    .addQueryParameter("token", TOKEN);

            HttpUrl httpUrl = httpUrlBuilder.build();
            Request newRequest = newRequestBuilder.url(httpUrl).build();
            return chain.proceed(newRequest);
        }

        // POST参数
        if (originalRequest.body() instanceof FormBody) {
            FormBody.Builder newFormBuilder = new FormBody.Builder();

            newFormBuilder.add("releaseVersion", VERSION_NAME)
                    .add("source", SOURCE)
                    .add("appId", APPID)
                    .add("token", TOKEN);

            newRequestBuilder.method(originalRequest.method(), newFormBuilder.build());
        }

        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }
}
