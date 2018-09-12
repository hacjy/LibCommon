package com.ha.cjy.common.util.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;

import com.ha.cjy.common.util.DialogUtil;

/**
 * 判断系统通知栏显示通知是否开启
 * Created by cjy on 2018/7/16.
 */

public class SystemNoticationUtil {

    /**
     * 检查通知是否开启了
     * @param context
     */
    public static boolean checkNoticationIsEnable(Context context){
        return checkNoticationIsEnable(context,null);
    }

    /**
     * 检查通知是否开启了
     * @param context
     */
    public static boolean checkNoticationIsEnable(Context context,OnClickCallback callback){
        boolean enable = isNoticationEnable(context);
        if (!enable){
            showDialog(context,callback);
        }
        return enable;
    }


    /**
     * 是否开启了
     * @param context
     * @return
     */
    public static boolean isNoticationEnable(Context context){
        boolean enable = NotificationManagerCompat.from(context).areNotificationsEnabled();
        return enable;
    }

    /**
     * 提示对话框
     * @param context
     */
    public static void showDialog(final Context context, final OnClickCallback callback){
        DialogUtil.showConfirmDialog(context,0, "提示", "检测到您还没有打开通知是否去打开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null){
                    callback.onClick(0);
                }
                toSetting(context);
            }
        },"去打开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onClick(1);
                }
                dialog.dismiss();
            }
        },"暂不打开");
    }

    /**
     * 跳转到通知设置页面
     * @param context
     */
    public static void toSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }


    public interface OnClickCallback{
        void onClick(int index);
    }
}
