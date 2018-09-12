package com.ha.cjy.common.util.push;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.AliyunMessageIntentService;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

/**
 * 推送的接收信息服务
 */

public class AppReceiverPushService extends AliyunMessageIntentService {

    @Override
    protected void onNotification(Context context, String s, String s1, Map<String, String> map) {
        Log.e(PushManager.TAG,"onNotification--"+s+"---"+s1);
    }

    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e(PushManager.TAG,"onMessage--"+cPushMessage.getTitle()+"---"+cPushMessage.getContent());
    }

    @Override
    protected void onNotificationOpened(Context context, String s, String s1, String map) {
    }

    /**
     * 点击通知栏推送消息的事件响应
     * @param context
     * @param s
     * @param s1
     * @param map
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String s, String s1, String map) {
        Log.e(PushManager.TAG,"onNotificationClickedWithNoAction--"+s+"---"+s1+"---"+map);
        PushParseUtil.parsePushData(context,map);
    }

    @Override
    protected void onNotificationRemoved(Context context, String s) {
        Log.e(PushManager.TAG,"onNotificationRemoved--"+s);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String s, String s1, Map<String, String> map, int i, String s2, String s3) {
        Log.e(PushManager.TAG,"onNotificationReceivedInApp--"+s+"---"+s1+"---"+i+"--"+s2+"---"+s3);
    }
}
