package com.ha.cjy.libcommon.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;


import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.ui.constants.FilePathCfg;
import com.ha.cjy.common.ui.widget.SelectPhotoPopupWindow;
import com.ha.cjy.common.util.BitmapUtil;
import com.ha.cjy.common.util.FileUtil;
import com.ha.cjy.common.util.GlideUtil;
import com.ha.cjy.common.util.SelectPhotoUtil;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;

import java.io.ByteArrayOutputStream;

/**
 * 替换用户头像
 * Created by cjy on 2018/7/31.
 */

public class SelectPhotoActivity extends BaseToolbarActivity implements View.OnClickListener{
    /**
     * 图片选择对话框
     */
    private SelectPhotoPopupWindow selectPopupWindow;
    /**
     * 图片选择器帮助类
     */
    private SelectPhotoUtil mHelp;

    private ImageView mUserPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void initView() {
        mUserPic = findViewById(R.id.iv_user_head);
    }

    @Override
    public void initData() {

        setUserPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533034814140&di=4716ebc61e1b7e9ae0a57157d696f209&imgtype=0&src=http%3A%2F%2Fwww.itbear.com.cn%2Fupload%2F2016-04%2F160419120287331.jpg");

        mHelp = new SelectPhotoUtil(SelectPhotoActivity.this, new SelectPhotoUtil.SelPopCallBack() {
            @Override
            public void callBitmapData(Bitmap bitmap, String data) {
                String path = FilePathCfg.FILE_IMG_DIR + FilePathCfg.USER_HEAD_IMAGE_FILE_NAME + ".jpg";
                //防止找不到文件，先创建文件
                FileUtil.createFile(path);
                BitmapUtil.saveBitmap2file(bitmap, path);
                //更新图片
                updateUserPic(bitmap);
            }
        });
    }

    public void setUserPic(String pic) {
        GlideUtil.glideCircle(this, mUserPic, pic,R.drawable.ic_load_empty_icon);
    }

    private void updateUserPic(Bitmap bitmap){
        try {
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes=baos.toByteArray();
                GlideUtil.glideCircle(this, mUserPic, bytes,R.drawable.ic_load_empty_icon);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initListener() {
        mUserPic.setOnClickListener(this);
    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackToolbar(SelectPhotoActivity.this,"","个人中心");
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.default_toolbar_layout;
    }

    /**
     * 选择完图片后的处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHelp.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_user_head) {
            showSelectPopupWindow();
            return;
        }
        if (id == R.id.btn_take_photo) {
            cameraClick();
            selectPopupWindow.dismiss();
            return;
        }
        if (id == R.id.btn_pick_photo) {
            photoClick();
            selectPopupWindow.dismiss();
            return;
        }
    }

    /**
     * 显示选择拍照或者相册的popupwindow
     */
    private void showSelectPopupWindow() {
        if (selectPopupWindow == null) {
            selectPopupWindow = new SelectPhotoPopupWindow(this, this);
        }
        if (!selectPopupWindow.isShowing()) {
            selectPopupWindow.showAtLocation(findViewById(R.id.layout_content), Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 拍照
     */
    private void cameraClick(){
        mHelp.requestPermission();
        mHelp.selPicForCamera();
    }

    /**
     * 选择相册
     */
    private void photoClick(){
        mHelp.requestPermission();
        mHelp.selPicForPhoto();
    }

}
