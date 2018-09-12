package com.ha.cjy.common.ui.update;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 升级信息的返回实体类
 * Created by willy on 2016/12/16.
 */

public class UpdateVersionResultInfo implements Parcelable{
    public int UpdateType;//'更新类型： 1，强制更新；'(int),
    public String AppName;//": "牛牛电竞",
    public String UpdateTitle;//": "更新标题',(string)"
    public String UpdateContent;//": "更新内容",
    public String UpdateIco;//": "更新图标地址,(string)"
    public String UpdateUrl;//":"更新包地址',(string)"
    public String VersionNumber;//":"版本号',(string)"
    public String InnerVersion;//":"内部版本号',(string)"
    public String PackageName;//":"包名,(string)"
    public int PackageSize;//":"包大小，单位：字节,(int)"
    public long AppUpdateTime;//":123445

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.UpdateType);
        dest.writeString(this.AppName);
        dest.writeString(this.UpdateTitle);
        dest.writeString(this.UpdateContent);
        dest.writeString(this.UpdateIco);
        dest.writeString(this.UpdateUrl);
        dest.writeString(this.VersionNumber);
        dest.writeString(this.InnerVersion);
        dest.writeString(this.PackageName);
        dest.writeInt(this.PackageSize);
        dest.writeLong(this.AppUpdateTime);
    }

    public UpdateVersionResultInfo() {
    }

    protected UpdateVersionResultInfo(Parcel in) {
        this.UpdateType = in.readInt();
        this.AppName = in.readString();
        this.UpdateTitle = in.readString();
        this.UpdateContent = in.readString();
        this.UpdateIco = in.readString();
        this.UpdateUrl = in.readString();
        this.VersionNumber = in.readString();
        this.InnerVersion = in.readString();
        this.PackageName = in.readString();
        this.PackageSize = in.readInt();
        this.AppUpdateTime = in.readLong();
    }

    public static final Creator<UpdateVersionResultInfo> CREATOR = new Creator<UpdateVersionResultInfo>() {
        @Override
        public UpdateVersionResultInfo createFromParcel(Parcel source) {
            return new UpdateVersionResultInfo(source);
        }

        @Override
        public UpdateVersionResultInfo[] newArray(int size) {
            return new UpdateVersionResultInfo[size];
        }
    };
}
