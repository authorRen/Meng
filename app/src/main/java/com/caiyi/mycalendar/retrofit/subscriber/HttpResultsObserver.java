package com.caiyi.mycalendar.retrofit.subscriber;


import com.caiyi.mycalendar.data.RequestMsg;
import com.caiyi.mycalendar.log.Logger;
import com.caiyi.mycalendar.retrofit.model.HttpResults;
import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.CancellationException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * 接口数据观察者.
 *
 * @author HuoGuangXu
 * @since 2017/5/30.
 */

public abstract class HttpResultsObserver<T> implements SingleObserver<HttpResults<T>> {
    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        ResultsObserverManger.addDisposable(d);
    }

    @Override
    public void onSuccess(HttpResults<T> httpResults) {
        if (httpResults != null) {
            if (httpResults.code == RequestMsg.RESULT_OK) {
                onSuccess(httpResults.results, httpResults.desc != null ? httpResults.desc : "");
            } else {
                onFailure(httpResults.code, httpResults.desc);
            }
        } else {
            onFailure(RequestMsg.CODE_ERROR_DEFAULT, "");
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            if (!(e instanceof CancellationException)) {
                if (e instanceof SocketTimeoutException) {
                    onFailure(RequestMsg.CODE_ERROR_TIMEOUT, RequestMsg.MSG_ERROR_SOCKET_TIMEOUT);
                } else if (e instanceof ConnectException) {
                    onFailure(RequestMsg.CODE_ERROR_UNCONNECTED, RequestMsg.MSG_ERROR_CONNECT);
                } else if (e instanceof UnknownHostException) {
                    onFailure(RequestMsg.CODE_ERROR_UNKNOWN_HOST, RequestMsg.MSG_ERROR_CONNECT);
                } else if (e instanceof MalformedJsonException) {
                    onFailure(RequestMsg.CODE_ERROR_MALFORMED_JSON, RequestMsg.MSG_ERROR_MALFORMED_JSON);
                } else if (e instanceof SSLHandshakeException) {
                    onFailure(RequestMsg.CODE_ERROR_SSL, RequestMsg.MSG_ERROR_SSL);
                } else {
                    onFailure(RequestMsg.CODE_ERROR_DEFAULT, e.getMessage());
                }
            }
        } else {
            onFailure(RequestMsg.CODE_ERROR_DEFAULT, "");
        }
    }

    public abstract void onSuccess(T t, String msg);

    public void onFailure(int errorCode, String msg) {
        Logger.e("errorCode: " + errorCode + ", errorMsg: " + msg);
    }

    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
