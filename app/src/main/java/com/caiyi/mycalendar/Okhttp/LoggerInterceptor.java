package com.caiyi.mycalendar.Okhttp;

import android.support.annotation.IntDef;
import android.util.Log;


import com.caiyi.mycalendar.compant.MyApplication;
import com.caiyi.mycalendar.Utils.GlobalConstants;
import com.caiyi.mycalendar.Utils.SPUtils;
import com.caiyi.mycalendar.log.Logger;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static java.lang.String.format;

/**
 * OkHttp3日志拦截器
 *
 * @author Renzeqiang
 * @since 2017/6/1.
 */
public class LoggerInterceptor implements Interceptor {
    private static final String F_BREAK = "%n";
    private static final String F_URL = " %s";
    private static final String F_TIME = " %s";
    private static final String F_HEADERS = "%s";
    private static final String F_RESPONSE = F_BREAK + "Response: %s";
    private static final String F_BODY = "body: %s";

    private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
    private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAK;
    private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
    private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAK;

    public static final int NONE = 0;//不打印log
    public static final int BASAL = 1;//不包含headers
    public static final int ALL = 2;//包含headers
    @LogLevel
    private static int mLevel = BASAL;

    @IntDef({NONE, BASAL, ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String bodyString = null;
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        if (SPUtils.getBoolean(GlobalConstants.SP_PARAMS_KEY.INTERCEPTOR_LOG_DISABLE)) {
            return response;
        }
        if (isUploadRequest(request)){
            return response;
        }
        
        String time = String.format(Locale.getDefault(), "%.1fms", (System.nanoTime() - t1) / 1e6d);
        try {
            MediaType contentType = null;
            ResponseBody responseBody = response.body();

            if (responseBody != null) {
                contentType = responseBody.contentType();

                // responseBody只能被消费一次。否则执行回调时会出错：IllegalStateException: closed.
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                bodyString = buffer.clone().readString(Charset.defaultCharset());
            }

            if (!isImageMimeType(contentType)) {
                printLog(request, response, time, contentType, bodyString);
            }

        } catch (SocketTimeoutException e) {
            printLog(request, null, "超时。" + Log.getStackTraceString(e), null, null);
            return chain.proceed(chain.request());
        } catch (ConnectException e) {
            printLog(request, null, "网络连接异常。" + Log.getStackTraceString(e), null, null);
            return chain.proceed(chain.request());
        } catch (UnknownHostException e) {
            printLog(request, null, "无法访问该域名。" + Log.getStackTraceString(e), null, null);
            return chain.proceed(chain.request());
        } catch (Exception e) {
            printLog(request, null, "失败.可能断网、接口挂了。" + Log.getStackTraceString(e), null, null);
            return chain.proceed(chain.request());
        }
        return response;
    }

    private void printLog(Request request, Response response, String time, MediaType mediaType, String bodyString) {
        String url = request.url().toString();
        String requestHeaders = stringifyRequestHeaders(request);
        String responseCode = response != null ? response.code() + "" : "";
        String responseHeaders = stringifyResponseHeaders(response);
        String responseBody = stringifyResponseBody(mediaType, bodyString);

        switch (request.method().toUpperCase()) {
            case "GET":
                Logger.log(Logger.INFO, MyApplication.TAG, format("GET " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITH_BODY,
                        url, time, requestHeaders, responseCode, responseHeaders, responseBody));
                break;
            case "POST":
                Logger.log(Logger.INFO, MyApplication.TAG, format("POST " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY,
                        url, time, requestHeaders, stringifyRequestBody(request), responseCode, responseHeaders, responseBody));
                break;
            case "PUT":
                Logger.log(Logger.INFO, MyApplication.TAG, format("PUT " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY,
                        url, time, requestHeaders, request.body().toString(), responseCode, responseHeaders, responseBody));
                break;
            case "DELETE":
                Logger.log(Logger.INFO, MyApplication.TAG, format("DELETE " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITHOUT_BODY,
                        url, time, requestHeaders, responseCode, responseHeaders));
                break;
            default:
                break;
        }
    }

    private String stringifyRequestHeaders(Request request) {
        return SPUtils.getBoolean(GlobalConstants.SP_PARAMS_KEY.PRINT_REQUEST_HEADER) && request != null ? request.headers().toString() : "";
    }

    private String stringifyResponseHeaders(Response response) {
        return SPUtils.getBoolean(GlobalConstants.SP_PARAMS_KEY.PRINT_RESPONSE_HEADER) && response != null ? response.headers().toString() : "";
    }

    private String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            String requestBody = buffer.readUtf8();
            return URLDecoder.decode(requestBody, "utf-8");
        } catch (IOException e) {
            return "stringify request occur IOException";
        }
    }

    private String stringifyResponseBody(MediaType MediaType, String responseBody) {
        if (responseBody == null) {
            return "";
        }
        if (!isPlaintext(MediaType)) {
            return "MIME类型是：" + MediaType + ",不打印log.";
        }
        return responseBody;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && "text".equals(mediaType.type())) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html"))
                return true;
        }
        return false;
    }

    private boolean isImageMimeType(MediaType mediaType) {
        return mediaType != null && mediaType.type() != null && "image".equalsIgnoreCase(mediaType.type());
    }

    public static void setLogLevel(@LogLevel int level) {
        mLevel = level;
    }

    private boolean isUploadRequest(Request request) {
        try {
            final MediaType mediaType = request.body().contentType();
            return "multipart".equalsIgnoreCase(mediaType.type()) && "form-data".equalsIgnoreCase(mediaType.subtype());
        } catch (Exception e) {
            return false;
        }
    }
}
