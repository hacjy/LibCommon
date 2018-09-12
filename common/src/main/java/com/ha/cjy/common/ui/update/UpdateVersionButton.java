package com.ha.cjy.common.ui.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.constants.Constants;
import com.ha.cjy.common.ui.update.download.ApkDownloadInfo;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.BaseDownloadWorker;
import com.ha.cjy.common.util.download.abst.ADownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadView;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 下载的点击按钮
 * Created by willy on 2016/12/16.
 */

public class UpdateVersionButton extends android.support.v7.widget.AppCompatTextView implements IDownloadView<ApkDownloadInfo>, View.OnClickListener {
    protected ApkDownloadInfo apkDownloadInfo;
    protected ADownloadDisplayHelper displayHelper;
    protected UpdateVersionDownApkClickHelper clickHelper;

    public UpdateVersionButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        displayHelper = new DownloadButtonDisplayHelper(this);
        clickHelper = new UpdateVersionDownApkClickHelper(this);
    }

    @Override
    public ApkDownloadInfo getDownloadInfo() {
        return apkDownloadInfo;
    }

    @Override
    public void setDownloadInfo(ApkDownloadInfo info) {
        setOnClickListener(this);
        apkDownloadInfo = info;
        displayHelper.setDownloadInfo(info);
        clickHelper.setDownloadInfo(info);
        apkDownloadInfo.display(displayHelper);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        displayHelper.registerDownloadReceiver();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        displayHelper.unregisterDownloadReceiver();
    }

    @Override
    public boolean checkDownloadState(ApkDownloadInfo info) {
        try {
            if (apkDownloadInfo == null) {
                return false;
            }
            if (apkDownloadInfo.getIdentification() == null) {
                return false;
            }
            if (apkDownloadInfo.getIdentification().equals(info.getIdentification())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void pause() {

    }

    @Override
    public BaseDownloadStateFactory.State getState() {
        return apkDownloadInfo.getState().getState();
    }

    @Override
    public void onClick(View v) {
        if (apkDownloadInfo == null) {
            ToastUtil.showToast(getContext(), getContext().getString(R.string.update_version_empty_url));
        } else {
            apkDownloadInfo.onClick(clickHelper);
        }
    }

    //发送广播
    private void sendUpdateBroadcast(int status, int progressNum) {
        Intent intent = new Intent(Constants.UPDATE_VERSION_BROADCAST_FILTER);
        intent.putExtra(Constants.UPDATE_VERSION_BROADCAST_FILTER_STATUS_FLAG, status);
        intent.putExtra(Constants.UPDATE_VERSION_BROADCAST_FILTER_PROGRESS_NUM, progressNum);
        getContext().sendBroadcast(intent);
    }

    private class DownloadButtonDisplayHelper extends ADownloadDisplayHelper<ApkDownloadInfo> {
        private BroadcastReceiver receiver;

        public DownloadButtonDisplayHelper(IDownloadView<ApkDownloadInfo> downloadView) {
            super(downloadView);
        }

        @Override
        public ApkDownloadInfo getDownloadInfo() {
            return apkDownloadInfo;
        }

        @Override
        public void setDownloadInfo(ApkDownloadInfo downloadInfo) {
            apkDownloadInfo = downloadInfo;
        }

        @Override
        public void onDownloadNewDisplay() {
//            CLog.e(getClass().getName(),"onDownloadNewDisplay");
        }

        @Override
        public void onDownloadWaitDisplay() {
            setText(getContext().getString(R.string.update_version_download_status_waiting));
            sendUpdateBroadcast(UpdateView.STATUS_PREPARE_DOWNLOAD, 0);
        }

        @Override
        public void onDownloadingDisplay() {
            setText("下载中");
            int progressNum = (int) (((float) apkDownloadInfo.getdSize() / apkDownloadInfo.getfSize()) * 100);
            sendUpdateBroadcast(UpdateView.STATUS_DOWNLOADING, progressNum);
        }

        @Override
        public void onDownloadedDisplay() {
            setText(getContext().getString(R.string.update_version_download_status_install));
            sendUpdateBroadcast(UpdateView.STATUS_INSTALL_APP, 0);
        }

        @Override
        public void onDownloadPausingDisplay() {
            setText("暂停");
        }

        @Override
        public void onDownloadPausedDisplay() {

        }

        @Override
        public void onDownloadCancelingDisplay() {

        }

        @Override
        public void onDownloadFailedDisplay() {
            setText(getContext().getString(R.string.update_version_download_status_retry));
            sendUpdateBroadcast(UpdateView.STATUS_FAILED_DOWNLOAD, 0);
        }

        @Override
        public void onDownloadNoneDisplay() {

        }

        @Override
        public void onDownloadConnectDisplay() {
            setText(getContext().getString(R.string.update_version_download_status_connecting));
            sendUpdateBroadcast(UpdateView.STATUS_PREPARE_DOWNLOAD, 0);
        }

        @Override
        public void registerDownloadReceiver() {
            IntentFilter intentFilter = new IntentFilter(BaseDownloadWorker.NOTIFY_VIEW_ACTION);
            if (this.receiver == null) {
                receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        BaseDownloadInfo info = intent.getParcelableExtra(BaseDownloadWorker.NOTIFY_VIEW_ACTION_EXTRA_INFO_KEY);
                        if (info instanceof ApkDownloadInfo) {
                            ApkDownloadInfo apkDownloadInfo1 = (ApkDownloadInfo) info;
                            if (checkDownloadState(apkDownloadInfo1)) {
                                UpdateVersionButton.this.setDownloadInfo(apkDownloadInfo1);
                                //更新进度的监听在这里直接处理了
                                apkDownloadInfo1.getState().getState();
                            }
                        }
                    }
                };
            }
            getContext().registerReceiver(receiver, intentFilter);
        }

        @Override
        public void unregisterDownloadReceiver() {
            if (receiver != null) {
                getContext().unregisterReceiver(receiver);
            }
        }
    }
}
