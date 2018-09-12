package com.ha.cjy.common.util.http;

import com.ha.cjy.common.util.JsonUtil;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import okio.BufferedSource;
import okio.Okio;
import retrofit.Converter;

/**
 * 获取qq
 */
public class QQLoadResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    public QQLoadResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        String json = "";
        try {
          int start =   tempStr.indexOf("(");
            int end =    tempStr.lastIndexOf(")");
           json =  tempStr.substring(start+1,end);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) JsonUtil.parseData(json, type);
    }

}
