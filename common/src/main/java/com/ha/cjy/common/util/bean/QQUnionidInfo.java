package com.ha.cjy.common.util.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * qqUnionid 专用
 */

public class QQUnionidInfo implements Parcelable {
    public String client_id;
    public String openid;
    public String unionid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.client_id);
        dest.writeString(this.openid);
        dest.writeString(this.unionid);
    }

    public QQUnionidInfo() {
    }

    protected QQUnionidInfo(Parcel in) {
        this.client_id = in.readString();
        this.openid = in.readString();
        this.unionid = in.readString();
    }

    public static final Creator<QQUnionidInfo> CREATOR = new Creator<QQUnionidInfo>() {
        @Override
        public QQUnionidInfo createFromParcel(Parcel source) {
            return new QQUnionidInfo(source);
        }

        @Override
        public QQUnionidInfo[] newArray(int size) {
            return new QQUnionidInfo[size];
        }
    };
}
