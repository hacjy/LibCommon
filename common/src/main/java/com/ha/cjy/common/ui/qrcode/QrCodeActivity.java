package com.ha.cjy.common.ui.qrcode;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseActivity;
import com.ha.cjy.common.util.DialogUtil;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.common.util.permission.PermissionCallback;
import com.ha.cjy.common.util.permission.PermissionsUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 二维码扫描页面
 * Created by cjy on 2018/7/19.
 */
public class QrCodeActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_qrcode);
        try {
            /**
             * 执行扫面Fragment的初始化操作
             */
            CaptureFragment captureFragment = new CaptureFragment();
            // 为二维码扫描界面设置定制化界面
            CodeUtils.setFragmentArgs(captureFragment, R.layout.default_qrcode_camera);
            captureFragment.setAnalyzeCallback(analyzeCallback);
            /**
             * 替换我们的扫描控件
             */
            getSupportFragmentManager().beginTransaction().replace(R.id.rl_qr_content, captureFragment).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initDataBeforeView() {

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            result = result.trim().replaceAll("\r|\n", "");
            onSuccess(result);
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtil.showToast(QrCodeActivity.this, "解析二维码失败");
        }
    };

    /**
     * 扫描成功的处理
     * @param result
     */
    public void onSuccess(String result){
        Log.e("二维码解析结果",result);
        ToastUtil.showToast(QrCodeActivity.this, "解析二维码成功");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
       showDialog();
    }

    /**
     * 提示对话框
     */
    private void showDialog(){
        //是否缺少权限
        boolean ret = new PermissionsUtil().checkPermissions(QrCodeActivity.this,Manifest.permission.CAMERA);
        if (ret){//没有授权，显示对话框
            DialogUtil.showConfirmDialog(QrCodeActivity.this, 0, "提示", "扫描二维码需要相机功能，请先开启！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermission();
                }
            },"知道了",null,null);
        }
    }

    /**
     * 请求权限
     */
    private void requestPermission(){
        new PermissionsUtil().requestPermission(QrCodeActivity.this, Manifest.permission.CAMERA, 0, new PermissionCallback() {
            @Override
            public void grantPermission(@NonNull String[] permissions) {

            }

            @Override
            public void denyPermission(@NonNull String[] permissions) {
                ToastUtil.showToast(QrCodeActivity.this,"没有相机权限，扫描不了二维码的哦！");
                QrCodeActivity.this.finish();
            }

            @Override
            public void denyNotRemindPermission(@NonNull String[] permissions) {

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void onClickLoadFailed() {

    }

    @Override
    public void onClickLoadEmpty() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
