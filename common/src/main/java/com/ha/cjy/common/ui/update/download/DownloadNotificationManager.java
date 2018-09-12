package com.ha.cjy.common.ui.update.download;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.NotificationTarget;
import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.util.MD5Util;
import com.ha.cjy.common.util.SharedPreUtil;
import com.ha.cjy.common.util.StringUtil;
import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by linbinghuang
 * Date 2016/2/15
 */
public class DownloadNotificationManager {

    private static DownloadNotificationManager manager;
    public SparseArray<ApkDownloadInfo> infoSparseArray = new SparseArray<>();
    private NotificationManager mNotificationManager;
    private Map<String, NotificationInfo> mNotifiMap = new ConcurrentHashMap<>();
    private int keyId = 2016;

    public static DownloadNotificationManager getInstance() {
        if (manager == null) {
            synchronized (DownloadNotificationManager.class) {
                if (manager == null) {
                    manager = new DownloadNotificationManager();
                }
            }
        }
        return manager;
    }

    public synchronized void showNotification(Context context, ApkDownloadInfo info) {
        try {
            if (info == null) {
                return;
            }
            if (mNotificationManager == null) {
                mNotificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
            }
            NotificationInfo mNotificationInfo = mNotifiMap.get(MD5Util.MD5(info.getUrl()));
            Notification mNotificationItem = null;
            if (mNotificationInfo == null) {

                mNotificationInfo = new NotificationInfo();
                mNotificationItem = new Notification(R.mipmap.ic_launcher, BaseApplication.getInstance().getString(R.string.download_start), System
                        .currentTimeMillis());
                mNotificationItem.contentView = new RemoteViews(context.getPackageName(), R.layout
                        .notification_download_view_layout);
                mNotificationItem.contentView.setProgressBar(R.id.pb_app_store_down, 100, 0, false);
                mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name, info.appName);

                //后台没给key那么现在只能自己生成key
                if(StringUtil.isEmpty(info.appId)){
                    String node = MD5Util.MD5(info.getUrl());
                    String appId = SharedPreUtil.getString(context,node);
                    if(StringUtil.isEmpty(appId)){
                        Random random = new Random();
                        appId = random.nextInt(500000)+"";
                        SharedPreUtil.put(context,node,appId);
                    }
                    info.appId = appId;
                }

                int id = Integer.valueOf(info.appId);
                mNotificationInfo.setId(id);
                mNotificationInfo.keyId = Integer.valueOf(info.appId);
                mNotificationInfo.setmNotification(mNotificationItem);
                mNotifiMap.put(MD5Util.MD5(info.getUrl()), mNotificationInfo);
                infoSparseArray.put(mNotificationInfo.keyId, info);
//            Intent buttonIntent = new Intent(DownloadNotificationClickReceiver.class.getSimpleName());
//            buttonIntent.putExtra(ApkDownloadInfo.class.getName(), info);
//            PendingIntent pendButtonIntent = PendingIntent.getBroadcast(context, keyId, buttonIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            mNotificationItem.contentView.setOnClickPendingIntent(R.id.rlay_app_store_box, pendButtonIntent);
                //构建notificationTarget用于Glide  into对象

                NotificationTarget notificationTarget = new NotificationTarget(
                        context,
                        R.id.iv_app_store_icon,
                        mNotificationItem.contentView,
                        mNotificationItem,
                        mNotificationInfo.getId());

                //用Glide将url图片加载成Notification中显示的图片
                // TODO: 2018/6/25 Glide 4.2
//            Glide.with(context.getApplicationContext()) // safer!
//                    .load("")
//                    .asBitmap()
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
//                    .into(notificationTarget);
            } else {
                mNotificationItem = mNotificationInfo.getmNotification();
                infoSparseArray.put(mNotificationInfo.keyId, info);
            }
            Intent buttonIntent = new Intent(DownloadNotificationClickReceiver.class.getSimpleName());
            buttonIntent.putExtra(ApkDownloadInfo.class.getName(), info);
            PendingIntent pendButtonIntent = PendingIntent.getBroadcast(context, keyId, buttonIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mNotificationItem.contentView.setOnClickPendingIntent(R.id.rlay_app_store_box, pendButtonIntent);

            int stateValue = info.getState().getState().getIntValue();
            if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_NEW.getIntValue()) {
                onDownloadNewDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_WAIT.getIntValue()) {
                onDownloadWaitDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_CONNECTING.getIntValue()) {
                onDownloadConnectDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
                onDownloadingDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_PAUSED.getIntValue()) {
                onDownloadPausedDisplay(context, mNotificationItem, info);
            }
            if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_PAUSEING.getIntValue()) {
                onDownloadPausingDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_FAILED.getIntValue()) {
                onDownloadFailedDisplay(context, mNotificationItem, info);

            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOAD_CANCELING.getIntValue()) {
                onDownloadCancelingDisplay(context, mNotificationItem, info);
            } else if (stateValue == BaseDownloadStateFactory.State.DOWNLOADED.getIntValue()) {
                onDownloadedDisplay(context, mNotificationItem, info);
            }
            NotificationTarget notificationTarget = new NotificationTarget(
                    context,
                    R.id.iv_app_store_icon,
                    mNotificationItem.contentView,
                    mNotificationItem,
                    mNotificationInfo.getId());

            //用Glide将url图片加载成Notification中显示的图片
            // TODO: 2018/6/25 Glide4.2使用
//        Glide
//                .with(context.getApplicationContext()) // safer!
//                .load("")
//                .asBitmap()
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(notificationTarget);
            mNotificationManager.notify(MD5Util.MD5(info.getUrl()), mNotificationInfo.getId(), mNotificationItem);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onDownloadNewDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_wait));
    }

    public void onDownloadWaitDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_wait));
    }

    public void onDownloadingDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        String dSize = StringUtil.getMemorySizeString(info.getdSize());
        String fSize = StringUtil.getMemorySizeString(info.getfSize());
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, dSize + "/" + fSize);
        int progress = (int) ((info.getdSize() * 100.0) / info.getfSize());
        mNotificationItem.contentView.setProgressBar(R.id.pb_app_store_down, 100, progress, false);
    }

    public void onDownloadedDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_complete));
        mNotificationItem.contentView.setProgressBar(R.id.pb_app_store_down, 100, 100, false);
        File file = new File(info.getSaveDir() + info.getSaveName());
        Uri e = Uri.fromFile(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(e, "application/vnd.android.package-archive");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mNotificationItem.contentIntent = contentIntent;
    }

    public void onDownloadPausingDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_pausing));
    }

    public void onDownloadPausedDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_pause));

    }

    public void onDownloadCancelingDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_canceling));
    }

    public void onDownloadFailedDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_failure));
    }

    public void onDownloadNoneDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_unknown));

    }

    public void onDownloadConnectDisplay(Context context, Notification mNotificationItem, BaseDownloadInfo info) {
        mNotificationItem.contentView.setTextViewText(R.id.tv_app_store_name2, BaseApplication.getInstance().getString(R.string.download_connecting));
    }

    public void removeNotificationByPackName(Context context, String key) {
        NotificationInfo info = mNotifiMap.remove(key);
        if (info == null) {
            return;
        }
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(info.getId());
    }

    private class NotificationInfo implements Serializable {
        /**
         * 1.991添加的，主要用来记录ScriptDownloadInfo的位置
         */
        public int keyId = 2016;
        private int id;
        private Notification mNotification;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Notification getmNotification() {
            return mNotification;
        }

        public void setmNotification(Notification mNotification) {
            this.mNotification = mNotification;
        }
    }
}
