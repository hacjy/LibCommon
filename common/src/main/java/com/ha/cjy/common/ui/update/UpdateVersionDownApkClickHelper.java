package com.ha.cjy.common.ui.update;


import com.ha.cjy.common.ui.update.download.ApkDownloadInfo;
import com.ha.cjy.common.ui.update.download.DownloadModel;
import com.ha.cjy.common.util.download.BaseDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadView;

/**
 * Created by willy on 2016/12/16.
 */

public class UpdateVersionDownApkClickHelper extends BaseDownloadClickHelper<ApkDownloadInfo> {

    public UpdateVersionDownApkClickHelper(IDownloadView<ApkDownloadInfo> downloadView) {
        super(downloadView);
    }

    @Override
    public ApkDownloadInfo getDownloadInfo() {
        return super.getDownloadInfo();
    }

    @Override
    public void setDownloadInfo(ApkDownloadInfo downloadInfo) {
        mDownloadInfo = downloadInfo;
    }

    @Override
    public void onDownloadedClick() {
        new DownloadModel().downApk(mContext,mDownloadInfo);
    }

    @Override
    public void onDownloadFailedClick() {
        new DownloadModel().downApk(mContext,mDownloadInfo);
    }

    @Override
    public void onDownloadNewClick() {
        new DownloadModel().downApk(mContext,mDownloadInfo);
    }
}
