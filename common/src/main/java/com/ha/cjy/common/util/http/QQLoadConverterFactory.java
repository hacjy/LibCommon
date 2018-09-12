package com.ha.cjy.common.util.http;

import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * QQ json解析
 */
public class QQLoadConverterFactory extends Converter.Factory {

    public static QQLoadConverterFactory create() {
        return new QQLoadConverterFactory();
    }



    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new QQLoadResponseBodyConverter<>(type);
    }
}