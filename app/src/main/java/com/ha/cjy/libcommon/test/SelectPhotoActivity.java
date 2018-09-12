package com.ha.cjy.libcommon.test;

import android.app.Activity;
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

public class SelectPhotoActivity extends BaseToolbarActivity implements View.OnClickListener,SelectPhotoContract.View{
    private SelectPhotoContract.Presenter mPresenter;
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
        mPresenter = new SelectPhotoPresenter (this);
        setUserPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533034814140&di=4716ebc61e1b7e9ae0a57157d696f209&imgtype=0&src=http%3A%2F%2Fwww.itbear.com.cn%2Fupload%2F2016-04%2F160419120287331.jpg");
    }

    public void setUserPic(String pic) {
        GlideUtil.glideCircle(this, mUserPic, pic,R.drawable.ic_load_empty_icon);
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
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_user_head) {
            mPresenter.showSelectPopupWindow();
            return;
        }
        if (id == R.id.btn_take_photo) {
            mPresenter.cameraClick();
            return;
        }
        if (id == R.id.btn_pick_photo) {
            mPresenter.photoClick();
            return;
        }
    }


    @Override
    public View.OnClickListener getClickListener() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return SelectPhotoActivity.this;
    }

    @Override
    public View getLayoutView() {
        return findViewById(R.id.layout_content);
    }

    @Override
    public ImageView getPhotoView() {
        return mUserPic;
    }
}
