package com.ha.cjy.common.util.http;

/**
 * 自定义的Throwable
 * Created by cjy on 2018/7/25.
 */

public class ApiThrowable  extends Throwable {
    //错误码
    public int code;
    //错误提示
    public String errorMsg;

    public ApiThrowable(int code) {
        super();
        this.code = code;
    }

    public ApiThrowable(int code,String errorMsg){
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
