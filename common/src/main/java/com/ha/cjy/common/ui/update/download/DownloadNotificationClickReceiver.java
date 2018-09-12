package com.ha.cjy.common.ui.update.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ha.cjy.common.util.download.BaseDownloadOperate;
import com.ha.cjy.common.util.download.BaseDownloadStateFactory;


/**
 * Created by linbinghuang on 2017/5/27.
 */

public class DownloadNotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        ApkDownloadInfo info = intent.getParcelableExtra(ApkDownloadInfo.class.getName());
        if (info == null) {
            return;
        }
//        ApkDownloadInfo newInfo = (ApkDownloadInfo) BaseDownloadOperate.getDownloadInfo(context, info.getIdentification());
//       if(newInfo==null){
//           return;
//       }
        if (info.getState().getState().getIntValue() == BaseDownloadStateFactory.getDownloadingState().getState().getIntValue()) {
            BaseDownloadOperate.pauseDownloadTask(context, info);
        } else if (info.getState().getState().getIntValue() == BaseDownloadStateFactory.getDownloadPausedState().getState().getIntValue()) {
            BaseDownloadOperate.addNewDownloadTask(context, info);
        }else if (info.getState().getState().getIntValue() == BaseDownloadStateFactory.getDownloadFailedState().getState().getIntValue()) {
            BaseDownloadOperate.addNewDownloadTask(context, info);
        }
    }
}
