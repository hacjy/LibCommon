package com.ha.cjy.libcommon.test;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ha.cjy.common.ui.base.BaseActivity;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.constants.SharePreCfg;
import com.ha.cjy.common.util.DialogUtil;
import com.ha.cjy.common.util.SharedPreUtil;
import com.ha.cjy.common.util.app.SystemNoticationUtil;
import com.ha.cjy.common.util.permission.PermissionCallback;
import com.ha.cjy.common.util.permission.PermissionsUtil;
import com.ha.cjy.libcommon.R;

/**
 * 欢迎页
 * Created by cjy on 2018/7/19.
 */

public class WelcomeActivity extends BaseActivity {
    private ImageView mIvBackground;
    /**
     * 是否授权有结果了
     */
    private boolean isResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        requestPermission();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mIvBackground = findViewById(R.id.iv_background);
    }

    @Override
    public void initListener() {

    }

    private void handResult(){
        if (isResult) {
            boolean ret = SharedPreUtil.getBoolean(WelcomeActivity.this,SharePreCfg.IS_FIRST_INSTALL);
            if (ret) {
                //检查通知是否开启，推送消息需要该权限
                boolean isEnable = SystemNoticationUtil.checkNoticationIsEnable(WelcomeActivity.this, new SystemNoticationUtil.OnClickCallback() {
                    @Override
                    public void onClick(int index) {
                        toMainActivity();
                    }
                });
                if (isEnable) {
                    toMainActivity();
                }
                SharedPreUtil.put(WelcomeActivity.this, SharePreCfg.IS_FIRST_INSTALL,false);
            }else {
                toMainActivity();
            }
        } else {
        }
    }

    private void toMainActivity(){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public View getContentView() {
        return null;
    }

    /**
     * 权限授权
     */
    private void requestPermission(){
        if (Build.VERSION.SDK_INT < 23){
            mIvBackground.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isResult = true;
                   handResult();
                }
            },2000);
            return;
        }
        final Activity activity = this;
        //读取、写入存储、读取手机状态的权限
        new PermissionsUtil().requestPermission(WelcomeActivity.this, 0, new PermissionCallback() {
            @Override
            public void grantPermission(@NonNull String[] permissions) {
                isResult = true;
                handResult();
            }

            @Override
            public void denyPermission(@NonNull String[] permissions) {
                BaseApplication.getInstance().exitApp();
            }

            @Override
            public void denyNotRemindPermission(@NonNull String[] permissions) {
                DialogUtil.showConfirmDialog(WelcomeActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert, "提示", "该应用需要开启相关权限，否则无法正常使用，请前往设置页面开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //前往权限设置界面
                      PermissionsUtil.toSettingActivity(activity);
                    }
                },"去设置",null,null);
            }
        },Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE);
    }
}
