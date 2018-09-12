package com.ha.cjy.common.util.http;

import com.ha.cjy.common.util.bean.QQUnionidInfo;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * 基础的请求接口
 * Created by cjy on 2018/8/6.
 */

public interface BaseApiService {
    //获取QQ unionid
    @GET("/oauth2.0/me")
    Observable<QQUnionidInfo> getQQUnionid(@Query("access_token") String access_token, @Query("unionid") int unionid);
}
