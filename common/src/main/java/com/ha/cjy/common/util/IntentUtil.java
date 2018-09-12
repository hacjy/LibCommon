package com.ha.cjy.common.util;

import android.content.Intent;

import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.update.download.ApkDownloadInfo;
import com.ha.cjy.common.ui.update.download.DownloadNotificationReceiver;

public class IntentUtil {


    /**
     * 发送广播更新通知栏的下载状态
     *
     * @param info
     */
    public static void toDownloadNotificationReceiver(ApkDownloadInfo info) {
        Intent intent = new Intent(DownloadNotificationReceiver.ACTION_DOWNLOAD_NOTIFICATION);
        intent.putExtra(ApkDownloadInfo.class.getName(), info);
        BaseApplication.getInstance().sendBroadcast(intent);
    }

    /**
     * 发送广播更新通知栏的下载状态
     *
     * @param info
     * @param isRemove 是否移除通知栏 true 移除 false 不移除
     */
    public static void toDownloadNotificationReceiver(ApkDownloadInfo info, boolean isRemove) {
        Intent intent = new Intent(DownloadNotificationReceiver.ACTION_DOWNLOAD_NOTIFICATION);
        intent.putExtra(DownloadNotificationReceiver.KEY_IS_REMOVE_DOWNLOAD_NOTIFICATION, isRemove);
        intent.putExtra(ApkDownloadInfo.class.getName(), info);
        BaseApplication.getInstance().sendBroadcast(intent);
    }
}
