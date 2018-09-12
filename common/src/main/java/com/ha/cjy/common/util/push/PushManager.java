package com.ha.cjy.common.util.push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.ha.cjy.common.ui.base.BaseApplication;

/**
 * 推送管理类
 * 阿里云配置：https://help.aliyun.com/document_detail/30064.html?spm=a2c4g.11186623.2.5.XR2qaU
 */

public class PushManager {
    public static final String TAG = "AliPush";
    private static PushManager sManager;

    private PushManager() {
    }

    private CloudPushService mPushService;

    public static PushManager getInstance() {
        if (sManager == null) {
            synchronized (PushManager.class) {
                if (sManager == null) {
                    sManager = new PushManager();
                }
            }
        }
        return sManager;
    }

    /**
     * 绑定账号
     */
    public void bindAccount() {
        long userId = BaseApplication.getInstance().getUserId();
        mPushService.bindAccount(userId + "", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "bindAccount success:" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.e(TAG, "bindAccount failed");

            }
        });
    }


    //注册
    public void register() {
        mPushService.register(BaseApplication.getInstance().getApplicationContext(), new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "init cloudchannel success");
                Log.e(TAG, "id--" + mPushService.getDeviceId() + "--utdId--" + mPushService.getUTDeviceId());
                bindAccount();
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    /**
     * 账号解绑
     */
    public void unBindAccount() {

        mPushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "unbindAccount success:" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.e(TAG, "bindAccount failed");

            }
        });
    }

    /**
     * 初始化推送服务
     *
     * @param context
     */
    public void initPushService(final Context context) {
        // 创建notificaiton channel,避免8.0系统通知栏不显示推送消息 使用阿里云推送3.1以上版本sdk适配8.0
        createNotificationChannel();

        PushServiceFactory.init(context);
        mPushService = PushServiceFactory.getCloudPushService();
        mPushService.register(context, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "init cloudchannel success");
                Log.e(TAG, "id--" + mPushService.getDeviceId() + "--utdId--" + mPushService.getUTDeviceId());
//                bindAccount();
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        mPushService.setPushIntentService(AppReceiverPushService.class);


//        mPushAgent = PushAgent.getInstance(context);
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//                CLog.e("push", "success " + s);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                CLog.e("push", "failure " + s + "--" + s1);
//            }
//        });
//        mPushAgent.setPushIntentServiceClass(AppReceiverPushService.class);
//        mPushAgent.setDebugMode(false);
//        //禁止震动
//        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        mPushAgent.setMessageHandler(umengMessageHandler);
//        mPushAgent.setNotificationClickHandler(new PushNotificationClickHelper());
    }

    public void createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager mNotificationManager = (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
                // 通知渠道的id
                String id = "1";
                // 用户可以看到的通知渠道的名字.
                CharSequence name = "notification channel";
                // 用户可以看到的通知渠道的描述
                String description = "notification description";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                // 配置通知渠道的属性
                mChannel.setDescription(description);
                // 设置通知出现时的闪灯（如果 android 设备支持的话）
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                // 设置通知出现时的震动（如果 android 设备支持的话）
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                //最后在notificationmanager中创建该通知渠道
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 开启推送
     *
     * @param context activity内调用
     */
    public void enablePush(Context context, CommonCallback commonCallback) {
        mPushService.turnOnPushChannel(commonCallback);
//        mPushAgent.enable(new IUmengCallback() {
//            @Override
//            public void onSuccess() {
//                CLog.e("push", "开启推送成功");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                CLog.e("push", "开启推送失败");
//            }
//        });
    }

    /**
     * 获取用户deviceId
     *
     * @return
     */
    public String getDeviceId() {
        if (mPushService == null) {
            return "";
        }
        return mPushService.getDeviceId();
    }

    /**
     * 关闭推送
     *
     * @param context activity内调用
     */
    public void disablePush(Context context, CommonCallback commonCallback) {
        mPushService.turnOffPushChannel(commonCallback);
//        mPushAgent.disable(new IUmengCallback() {
//            @Override
//            public void onSuccess() {
//                CLog.e("push", "关闭推送成功");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                CLog.e("push", "关闭推送失败");
//            }
//        });
    }

    /**
     * 添加用户推送标签
     *
     * @param tags
     */
    public void addPushTag(String... tags) {
//        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
//            @Override
//            public void onMessage(boolean b, ITagManager.Result result) {
//                if (result != null) {
//                }
//            }
//        }, tags);
    }

//    public void clearPushTag(TagManager.TCallBack tCallBack) {
//        mPushAgent.getTagManager().reset(tCallBack);
//    }

    public void listPushTag() {
//        mPushAgent.getTagManager().list(new TagManager.TagListCallBack() {
//            @Override
//            public void onMessage(boolean b, List<String> list) {
//                for (String tag : list) {
//                    CLog.e("pushInfo", "tag--" + tag);
//                }
//            }
//        });
    }

    /**
     * 添加用户别名
     *
     * @param alias 用户别名内容
     */
    public void addPushAlias(String alias) {
//        mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean b, String s) {
//
//            }
//        });
        mPushService.addAlias(alias, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailed(String s, String s1) {
            }
        });
    }

}
