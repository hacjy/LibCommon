package com.ha.cjy.common.util.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求返回结果的统一处理
 * Created by cjy on 2018/7/25.
 */

public class TransformerUtil {
    /**
     * 重试次数
     */
    private final static int RETRY_COUNT = 3;

    /**
     * 默认的处理，有重试机制
     */
    public final static Observable.Transformer defaultTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return getObservable(o,RETRY_COUNT,true);
        }
    };

    /**
     * 默认的处理，无重试机制
     */
    public final static Observable.Transformer defaultNoRetryTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return getObservable(o,0,true);
        }
    };

    /**
     * 不处理
     */
    public final static Observable.Transformer noDealTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return getObservable(o,RETRY_COUNT,false);
        }
    };

    /**
     * 不处理 不重试
     */
    public final static Observable.Transformer noDealNoRetryTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return getObservable(o,0,false);
        }
    };

    /**
     * 获取Observble
     * @param o
     * @param retry 重试次数
     * @param togeterDeal 是否统一处理结果
     * @return
     */
    private static Observable getObservable(Object o, int retry,boolean togeterDeal){
        Observable obv = (Observable) o;
        if (retry != 0){
            obv.retry(retry);
        }
        obv.subscribeOn(Schedulers.io())
           .unsubscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread());
        if(togeterDeal) {
                obv.flatMap(new Func1<BaseResultResponse, Observable<BaseResultResponse>>() {
                @Override
                public Observable<BaseResultResponse> call(BaseResultResponse baseResultWrapper) {
                    if (baseResultWrapper == null || baseResultWrapper.Code == null) {
                        throw new ApiExection(ApiExection.ERROR_CODE);
                    }
                    if (baseResultWrapper.Code != 0) {
                        return Observable.error(new ApiThrowable(baseResultWrapper.Code, baseResultWrapper.Msg));
                    }
                    return Observable.just(baseResultWrapper);
                }
            });
        }
        return obv;

    }


    /**
     * 不验证数据
     * @return
     */
    public static Observable.Transformer applySchedulersNoVerify() {
        return noDealTransformer;
    }


}
