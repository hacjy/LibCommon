package com.ha.cjy.common.util.http;

import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * 不解析json
 *
 */
public class NoJsonLoadConverterFactory extends Converter.Factory {

    public static NoJsonLoadConverterFactory create() {
        return new NoJsonLoadConverterFactory();
    }



    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new NoJsonLoadResponseBodyConverter<>(type);
    }
}