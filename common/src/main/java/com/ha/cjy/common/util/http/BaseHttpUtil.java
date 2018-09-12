package com.ha.cjy.common.util.http;

import com.ha.cjy.common.ui.constants.BaseUrl;

import rx.Subscriber;
import rx.Subscription;

/**
 * 基础的http请求管理
 * Created by cjy on 2018/8/6.
 */

public class BaseHttpUtil {
    /**
     * qq获取Unionid
     *
     * @param subscriber
     * @param token
     * @param unionid
     * @return
     */
    public static Subscription loadQQUnionid(Subscriber subscriber, String token, int unionid) {
        return RetrofitUtil
                .getRetrofitQQ(BaseUrl.API_QQ_UNIONID)
                .create(BaseApiService.class)
                .getQQUnionid(token, unionid)
                .compose(TransformerUtil.applySchedulersNoVerify())
                .subscribe(subscriber);
    }
}
