package com.ha.cjy.common.ui.update.download;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;
import com.j256.ormlite.field.DatabaseField;

/**
 * apk下载
 * Created by willy on 2016/12/16.
 */

public class ApkDownloadInfo extends BaseDownloadInfo implements Parcelable{
    @DatabaseField
    public String packageName;
    @DatabaseField
    public String appName;
    @DatabaseField
    public String appId;
    @DatabaseField
    public String versionName;

    public ApkDownloadInfo(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.packageName);
        dest.writeString(this.appName);
        dest.writeString(this.appId);
        dest.writeString(this.versionName);
    }

    protected ApkDownloadInfo(Parcel in) {
        super(in);
        this.packageName = in.readString();
        this.appName = in.readString();
        this.appId = in.readString();
        this.versionName = in.readString();
    }

    public static final Creator<ApkDownloadInfo> CREATOR = new Creator<ApkDownloadInfo>() {
        @Override
        public ApkDownloadInfo createFromParcel(Parcel source) {
            return new ApkDownloadInfo(source);
        }

        @Override
        public ApkDownloadInfo[] newArray(int size) {
            return new ApkDownloadInfo[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        ApkDownloadInfo info = (ApkDownloadInfo) o;
        if(info.identification.equals(this.identification)){
            return true;
        }
        return super.equals(o);
    }
}
