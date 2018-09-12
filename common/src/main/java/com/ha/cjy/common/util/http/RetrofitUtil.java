package com.ha.cjy.common.util.http;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * retrofit请求帮助类
 * Created by cjy on 2018/7/25.
 */

public class RetrofitUtil {
    /**
     * 网络请求超时时间
     */
    private final static long TIME_OUT = 10;

    /**
     * 默认未设置超时
     * @param baseUrl
     * @return
     */
    public static Retrofit.Builder getRetrofitBuilder(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    /**
     * 设置默认超时时间的retrofit
     * @param baseUrl
     * @return
     */
    public static Retrofit getDefaultRetrofit(String baseUrl){
        return getRetrofit(baseUrl,TIME_OUT);
    }

    /**
     * 默认不解析json的retrofit
     * @param baseUrl
     * @return
     */
    public static Retrofit getNoJsonRetrofit(String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
        return  getRetrofitBuilder(baseUrl)
                .addConverterFactory(NoJsonLoadConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 解析QQ json的retrofit
     * @param baseUrl
     * @return
     */
    public static Retrofit getRetrofitQQ(String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
        return  getRetrofitBuilder(baseUrl)
                .addConverterFactory(QQLoadConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 设置超时的retrofit
     * @param baseUrl
     * @param timeout 0 不设置超时
     * @return
     */
    public static Retrofit getRetrofit(String baseUrl,long timeout){
        OkHttpClient okHttpClient = new OkHttpClient();
        if (timeout != 0) {
            okHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
        }
        return getRetrofitBuilder(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
    }


}
