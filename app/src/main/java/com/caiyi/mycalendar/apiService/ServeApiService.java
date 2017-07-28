package com.caiyi.mycalendar.apiService;

import com.caiyi.mycalendar.model.DetailServeModel;
import com.caiyi.mycalendar.retrofit.model.HttpResults;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by RZQ on 2017/6/23.
 */

public interface ServeApiService {

    /** 周边服务详细页面 */
    @FormUrlEncoded
    @POST("/service/getAllAroundService.go")
    Single<HttpResults<DetailServeModel>> getAllAroundService(@Field("existenceType") String existenceType, @Field("addressCode") String addressCode);

}
