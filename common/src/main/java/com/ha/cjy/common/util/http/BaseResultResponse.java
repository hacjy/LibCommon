package com.ha.cjy.common.util.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 请求返回结果基类
 * Created by cjy on 2018/7/25.
 */

public class BaseResultResponse implements Parcelable {
    public String Msg;
    public Integer Code;

    public BaseResultResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Msg);
        dest.writeValue(this.Code);
    }

    protected BaseResultResponse(Parcel in) {
        this.Msg = in.readString();
        this.Code = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<BaseResultResponse> CREATOR = new Creator<BaseResultResponse>() {
        @Override
        public BaseResultResponse createFromParcel(Parcel source) {
            return new BaseResultResponse(source);
        }

        @Override
        public BaseResultResponse[] newArray(int size) {
            return new BaseResultResponse[size];
        }
    };
}
