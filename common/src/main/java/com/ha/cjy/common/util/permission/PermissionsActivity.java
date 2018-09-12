package com.ha.cjy.common.util.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.ha.cjy.common.R;

/**
 * 权限获取页面--单纯用来申请权限用的
 * Created by cjy on 2018/7/9.
 */

public class PermissionsActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS =  "com.ha.cjy.permission.extra_permission"; // 权限参数
    private static PermissionCallback callback;
    private boolean isRequireCheck; // 是否需要系统权限检测, 防止和系统提示框重叠

    /**
     * 启动当前权限页面的公开接口
     */
    public static void startActivityForResult(Activity activity, int requestCode, PermissionCallback callback, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        PermissionsActivity.callback = callback;
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permissions);

        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (new PermissionsUtil().checkPermissions(this, permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                //权限已经授予了
                if (callback != null) {
                    callback.grantPermission(permissions);
                }
                finish();
            }
        } else {
            isRequireCheck = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback = null;
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (callback != null) {

            if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
                isRequireCheck = true;
                //权限全部都授予成功
                callback.grantPermission(permissions);
            } else {
                handlePermissionsCallback(permissions, grantResults);
            }
        }
        finish();
    }

    //如果并不是全部权限均授予了，就要针对每个申请的权限回调进行处理
    private void handlePermissionsCallback(String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                //遍历所有回调结果的权限授予情况，如果没有授予的话继续下面的处理
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //如果这个权限已经拒绝过了，并且用户勾选了不在提示的按钮,进行相应的回调处理---目前直接强退应用，不需要特殊处理
                    isRequireCheck = false;
                    callback.denyNotRemindPermission(permissions);
                    break;
                } else {
                    //用户单纯拒绝,跳出循环--因为目前直接强退应用就行了，不用特殊处理
                    isRequireCheck = false;
                    callback.denyPermission(permissions);
                    break;
                }
            }
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
