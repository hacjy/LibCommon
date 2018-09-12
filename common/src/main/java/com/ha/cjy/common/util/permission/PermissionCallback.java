package com.ha.cjy.common.util.permission;

import android.support.annotation.NonNull;

/**
 * 权限授权回调
 * Created by cjy on 2018/7/9.
 */

public interface PermissionCallback {
    /**
     * 权限已经授予了的回调
     */
    void grantPermission(@NonNull String[] permissions);

    /**
     * 权限被拒绝授予了的回调
     */
    void denyPermission(@NonNull String[] permissions);

    /**
     * 权限没有授予，并且用户之前已经勾选了不再提示授予权限，需要针对这个另外处理
     */
    void denyNotRemindPermission(@NonNull String[] permissions);
}
