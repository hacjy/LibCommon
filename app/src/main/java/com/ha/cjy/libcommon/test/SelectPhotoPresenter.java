package com.ha.cjy.libcommon.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Gravity;

import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.constants.FilePathCfg;
import com.ha.cjy.common.ui.widget.SelectPhotoPopupWindow;
import com.ha.cjy.common.util.BitmapUtil;
import com.ha.cjy.common.util.FileUtil;
import com.ha.cjy.common.util.GlideUtil;
import com.ha.cjy.common.util.SelectPhotoUtil;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.libcommon.R;

import java.io.ByteArrayOutputStream;

/**
 * 选择图片的p层
 * Created by cjy on 2018/9/12.
 */

public class SelectPhotoPresenter implements SelectPhotoContract.Presenter{
    private SelectPhotoContract.View mView;
    /**
     * 图片选择对话框
     */
    private SelectPhotoPopupWindow selectPopupWindow;
    /**
     * 图片选择器帮助类
     */
    private SelectPhotoUtil mHelp;
    /**
     * 选择拍照还是相册的操作标志 1-拍照 2-相册
     */
    private int actionFlag;

    public SelectPhotoPresenter(SelectPhotoContract.View mView) {
        this.mView = mView;
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        mHelp = new SelectPhotoUtil(mView.getActivity(), new SelectPhotoUtil.SelPopCallBack() {
            @Override
            public void callBitmapData(Bitmap bitmap, String data) {
                String path = FilePathCfg.FILE_IMG_DIR + FilePathCfg.USER_HEAD_IMAGE_FILE_NAME + ".jpg";
                //防止找不到文件，先创建文件
                FileUtil.createFile(path);
                BitmapUtil.saveBitmap2file(bitmap, path);
                //更新图片
                updateUserPic(bitmap);
            }

            @Override
            public void callPermissionResult(boolean isGranted) {
                if (isGranted){
                    if (actionFlag == 1){
                        mHelp.selPicForCamera();
                    }else if (actionFlag == 2){
                        mHelp.selPicForPhoto();
                    }
                }else{
                    ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(com.ha.cjy.common.R.string.mine_edit_photo_permission_sdcard_warn));
                }
            }
        });
    }

    /**
     * 更新图片
     * @param bitmap
     */
    private void updateUserPic(Bitmap bitmap){
        try {
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes=baos.toByteArray();
                GlideUtil.glideCircle(mView.getActivity(), mView.getPhotoView(), bytes, R.drawable.ic_load_empty_icon);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 选择图片的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHelp.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 显示选择拍照或者相册的popupwindow
     */
    @Override
    public void showSelectPopupWindow() {
        if (selectPopupWindow == null) {
            selectPopupWindow = new SelectPhotoPopupWindow(mView.getActivity(), mView.getClickListener());
        }
        if (!selectPopupWindow.isShowing()) {
            selectPopupWindow.showAtLocation(mView.getLayoutView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissDialog(){
        if (selectPopupWindow != null){
            selectPopupWindow.dismiss();
        }
    }

    /**
     * 拍照
     */
    @Override
    public void cameraClick(){
        actionFlag = 1;
        mHelp.requestPermission();
        dismissDialog();
    }

    /**
     * 选择相册
     */
    @Override
    public void photoClick(){
        actionFlag = 2;
        mHelp.requestPermission();
        dismissDialog();
    }

}
