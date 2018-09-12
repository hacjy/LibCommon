package com.ha.cjy.common.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ha.cjy.common.ui.ActivitysManager;
import com.ha.cjy.common.ui.IInitView;
import com.ha.cjy.common.ui.stateview.LoadStateViewHelper;
import com.ha.cjy.common.ui.stateview.LoadStateViewResultHelper;
import com.ha.cjy.common.ui.stateview.LocalStateViewFactory;

/**
 * Activity基类
 * Created by cjy on 2018/7/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements IInitView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        ActivitysManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitysManager.getInstance().finishActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    public void init(){
        initDataBeforeView();
        initView();
        initData();
        initListener();
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void initDataBeforeView() {

    }

    /**
     * 初始化默认的状态视图帮助类
     */
    public LoadStateViewResultHelper getLocalLoadHelper(){
        LoadStateViewHelper stateViewHelper = new LoadStateViewHelper(this, getContentView(), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickAction = (int) v.getTag();
                switch (clickAction){
                    case LocalStateViewFactory.CLICK_ACTION_FAILED:{
                        onClickLoadFailed();
                        break;
                    }
                    case LocalStateViewFactory.CLICK_ACTION_EMPTY:{
                        onClickLoadEmpty();
                        break;
                    }
                }
            }
        });
        LoadStateViewResultHelper resultHelper = new LoadStateViewResultHelper(stateViewHelper);
        return resultHelper;
    }

    /**
     * 获取内容视图
     * @return
     */
    public abstract View getContentView();

    /**
     * 加载失败
     */
    public void onClickLoadFailed(){};

    /**
     * 加载空视图
     */
    public void onClickLoadEmpty(){};
}
