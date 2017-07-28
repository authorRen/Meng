package com.caiyi.mycalendar.rx;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx 工具类
 *
 * @author HuoGuangXu
 * @since 2017/5/30.
 */

public class RxUtil {

    /** 线程切换，回调在主线程 */
    public static <T> SingleTransformer<T, T> defaultSingleSchedulers() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(@NonNull Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
