package com.ha.cjy.common.ui.update.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * 下载更新notification的广播接收器
 * Created by willy on 2017/5/19.
 */

public class DownloadNotificationReceiver extends BroadcastReceiver {
    public static final String KEY_IS_REMOVE_DOWNLOAD_NOTIFICATION = "keyIsRemoveDownloadNotification";
    public static final String ACTION_DOWNLOAD_NOTIFICATION = "com.ha.cjy.commonlib.DownloadNotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRemove = intent.getBooleanExtra(KEY_IS_REMOVE_DOWNLOAD_NOTIFICATION,false);
        ApkDownloadInfo info = intent.getParcelableExtra(ApkDownloadInfo.class.getName());
        if (info == null){
            return;
        }
        if (isRemove){
            DownloadNotificationManager.getInstance().removeNotificationByPackName(context,info.packageName);
            return;
        }
        DownloadNotificationManager.getInstance().showNotification(context,info);

    }
}
