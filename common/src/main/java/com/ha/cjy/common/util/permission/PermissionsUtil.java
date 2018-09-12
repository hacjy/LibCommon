package com.ha.cjy.common.util.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

/**
 * 权限帮助类，提供给外部访问
 * Created by cjy on 2018/7/9.
 */

public class PermissionsUtil {
    /**
     * 跳转到应用的设置页面设置权限的请求码
     */
    public static final int REQUEST_PERMISSION_SEETING = 10000;

    /**
     * 单个申请权限
     *
     * @param permission            需要申请的权限
     * @param requestPermissionCode 申请权限的回调code
     * @param permissionCallback    申请权限的回调
     */
    public void requestPermission(Activity activity, String permission, int requestPermissionCode, PermissionCallback permissionCallback) {
        PermissionsActivity.startActivityForResult(activity, requestPermissionCode, permissionCallback, permission);
    }
    /**
     * 多个申请权限
     *
     * @param permissions            需要申请的权限
     * @param requestPermissionCode 申请权限的回调code
     * @param permissionCallback    申请权限的回调
     */
    public void requestPermission(Activity activity,  int requestPermissionCode, PermissionCallback permissionCallback,String... permissions) {
        PermissionsActivity.startActivityForResult(activity, requestPermissionCode, permissionCallback, permissions);
    }
    /**
     * 判断权限的集合是否缺少
     *
     * @param permissions 权限的集合
     * @return true 有缺少的权限 false：没有缺少的权限
     */
    public boolean checkPermissions(Activity activity,String... permissions) {
        for (String permission : permissions) {
            if (lackPermission(activity,permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少该权限
     *
     * @param permission 需要进行判断的权限
     * @return true：缺少该权限；false：已有该权限
     */
    private boolean lackPermission(Activity activity,String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 跳转到应用的设置页面
     */
    public static void toSettingActivity(Activity activity){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
    }
}
