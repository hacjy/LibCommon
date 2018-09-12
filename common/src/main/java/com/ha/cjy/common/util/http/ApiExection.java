package com.ha.cjy.common.util.http;

/**
 * 接口异常自定义类
 * Created by cjy on 2018/7/25.
 */

public class ApiExection extends RuntimeException{
    //错误参数
    public static final int ERROR_CODE = -1001;
    public static final int ERROR_CODE2 = -1002;

    //错误码
    public int code;
    //错误但有对象可以传输
    public Object object;
    //rx异常
    public Throwable throwable;
    //一般异常
    public Exception e;

    public ApiExection() {
    }

    public ApiExection(int code) {
        this.code = code;
    }

    public ApiExection(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public ApiExection(int code, Object object, Exception e) {
        this.code = code;
        this.e = e;
        this.object = object;
    }

    public ApiExection(int code, Object object, Throwable throwable) {
        this.code = code;
        this.object = object;
        this.throwable = throwable;
    }
}
