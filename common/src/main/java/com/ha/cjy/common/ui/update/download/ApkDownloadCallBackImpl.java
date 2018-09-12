package com.ha.cjy.common.ui.update.download;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.util.IntentUtil;
import com.ha.cjy.common.util.download.BaseDownloadOperate;
import com.ha.cjy.common.util.download.BaseDownloadWorker;

/**
 * Created by willy on 2016/12/17.
 */

public class ApkDownloadCallBackImpl implements BaseDownloadWorker.DownloadCallBack {
    public static final Parcelable.Creator<ApkDownloadCallBackImpl> CREATOR = new Parcelable.Creator<ApkDownloadCallBackImpl>() {
        @Override
        public ApkDownloadCallBackImpl createFromParcel(Parcel source) {
            return new ApkDownloadCallBackImpl(source);
        }

        @Override
        public ApkDownloadCallBackImpl[] newArray(int size) {
            return new ApkDownloadCallBackImpl[size];
        }
    };

    public ApkDownloadCallBackImpl() {
    }

    protected ApkDownloadCallBackImpl(Parcel in) {
    }

    @Override
    public void onDownloadPaused(String url) {
        update(url);
    }

    @Override
    public void onDownloadWorking(String url, long totalSize,
                                  long downloadSize, int progress) {
        update(url);
    }

    @Override
    public void onDownloadCompleted(String url, String file, long totalSize) {
        complete(url);

    }

    @Override
    public void onDownloadStart(String url, long downloadSize) {
        update(url);
    }

    @Override
    public void onDownloadWait(String url) {
        update(url);
    }

    @Override
    public void onDownloadCanceled(String s) {
        del(s);
    }

    @Override
    public void onDownloadPausing(String s) {
        update(s);
    }

    @Override
    public void onDownloadCanceling(String s) {
        update(s);
    }

    @Override
    public void onDownloadFailed(String s) {
        update(s);
    }

    //更新数据
    private void update(String url) {
        ApkDownloadInfo downloadInfo = (ApkDownloadInfo) BaseDownloadOperate.getDownloadInfo
                (BaseApplication.getInstance(), url);
        ApkDownloadDao.getInstance().insertOrUpdate(downloadInfo);
        IntentUtil.toDownloadNotificationReceiver(downloadInfo);

    }

    //删除数据
    private void del(String url) {
        ApkDownloadInfo downloadInfo = (ApkDownloadInfo) BaseDownloadOperate.getDownloadInfo
                (BaseApplication.getInstance(), url);
        ApkDownloadDao.getInstance().delete(downloadInfo);
       IntentUtil.toDownloadNotificationReceiver(downloadInfo, true);
    }

    //下载完成
    private void complete(String url) {
        ApkDownloadInfo downloadInfo = (ApkDownloadInfo) BaseDownloadOperate.getDownloadInfo
                (BaseApplication.getInstance(), url);
        IntentUtil.toDownloadNotificationReceiver(downloadInfo);
        ApkDownloadDao.getInstance().delete(downloadInfo);
        DownloadModel.downloadCompleteApk(downloadInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
