package com.ha.cjy.common.util.download.kernel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadWorker;
import com.ha.cjy.common.util.download.abst.ADownloadWorker;
import com.ha.cjy.common.util.download.db.JsonPersister;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.state.DownloadNewState;
import com.j256.ormlite.field.DatabaseField;

/**
 * 下载对象
 *
 * @author linxin
 */
public class BaseDownloadInfo implements Parcelable {

    /**
     * 下载唯一标识
     */
    @DatabaseField(id = true)
    protected String identification;
    /**
     * 下载链接
     */
    @DatabaseField
    private String url;
    /**
     * 保存目录
     */
    @DatabaseField
    private String saveDir;
    /**
     * 保存文件名
     */
    @DatabaseField
    private String saveName;
    /**
     * 文件大小
     */
    @DatabaseField
    private long fSize;
    /**
     * 已下载大小
     */
    @DatabaseField
    private long dSize;
    /**
     * 下载状态
     */
    @DatabaseField(persisterClass = JsonPersister.class)
    private IDownloadState state = new DownloadNewState();
    /**
     * 下载速度
     */
    @DatabaseField
    private long speed;
    /**
     * 创建时间
     */
    @DatabaseField
    private long createTime;
    /**
     * 下载完成时间
     */
    @DatabaseField
    private long downloadedTime = 0;
    /**
     * 下载回调
     */
    private BaseDownloadWorker.DownloadCallBack mCallBack;
    /**
     * 下载对象类
     */
    private String downloadWorkerClassName;


    public BaseDownloadInfo() {

    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getfSize() {
        return fSize;
    }

    public void setfSize(long fSize) {
        this.fSize = fSize;
    }

    public long getdSize() {
        return dSize;
    }

    public void setdSize(long dSize) {
        this.dSize = dSize;
    }

    public IDownloadState getState() {
        return state;
    }

    public void setState(IDownloadState state) {
        this.state = state;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getDownloadedTime() {
        return downloadedTime;
    }

    public void setDownloadedTime(long downloadedTime) {
        this.downloadedTime = downloadedTime;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public BaseDownloadWorker.DownloadCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(BaseDownloadWorker.DownloadCallBack callBack) {
        this.mCallBack = callBack;
    }

    public String getDownloadWorkerClassName() {
        return downloadWorkerClassName;
    }

    public void setDownloadWorkerClassName(String downloadWorkerClassName) {
        this.downloadWorkerClassName = downloadWorkerClassName;
    }

    public void setDownloadWorkerClassName(Class<? extends ADownloadWorker<? extends BaseDownloadInfo>> clazz) {
        this.downloadWorkerClassName = clazz.getName();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * 点击事件
     *
     * @param helper
     */
    public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
        state.onClick(helper);
    }

    public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
        state.display(helper);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof BaseDownloadInfo))
            return false;
        BaseDownloadInfo other = (BaseDownloadInfo) o;
        return other.getIdentification().equals(this.getIdentification());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeLong(fSize);
        dest.writeLong(dSize);
        dest.writeParcelable(state, 0);
        dest.writeLong(speed);
        dest.writeLong(createTime);
        dest.writeLong(downloadedTime);
        dest.writeString(saveDir);
        dest.writeString(saveName);
        dest.writeParcelable(mCallBack, 0);
        dest.writeString(downloadWorkerClassName);
        dest.writeString(identification);
    }

    public BaseDownloadInfo(Parcel p) {
        readFromParcel(p);
    }

    public void readFromParcel(Parcel p) {
        setUrl(p.readString());
        setfSize(p.readLong());
        setdSize(p.readLong());
        setState((IDownloadState) p.readParcelable(IDownloadState.class.getClassLoader()));
        setSpeed(p.readLong());
        setCreateTime(p.readLong());
        setDownloadedTime(p.readLong());
        setSaveDir(p.readString());
        setSaveName(p.readString());
        setCallBack((BaseDownloadWorker.DownloadCallBack) p.readParcelable(BaseDownloadWorker.DownloadCallBack.class.getClassLoader()));
        setDownloadWorkerClassName(p.readString());
        setIdentification(p.readString());
    }

}
