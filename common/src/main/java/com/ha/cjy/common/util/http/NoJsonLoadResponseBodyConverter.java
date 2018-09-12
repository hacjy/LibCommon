package com.ha.cjy.common.util.http;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import okio.BufferedSource;
import okio.Okio;
import retrofit.Converter;

/**
 *
 * 不解析json
 */
public class NoJsonLoadResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    public NoJsonLoadResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return (T) tempStr;
    }

}
