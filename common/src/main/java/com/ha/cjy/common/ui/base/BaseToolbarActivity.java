package com.ha.cjy.common.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ha.cjy.common.R;
import com.ha.cjy.common.util.ToolbarUtil;

/**
 * 带有标题栏的activity
 * Created by cjy on 2018/7/19.
 */

public abstract class BaseToolbarActivity extends BaseActivity {
    private ToolbarUtil mToolbarUtil;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolbarUtil = new ToolbarUtil(this, layoutResID);
        mToolbar = mToolbarUtil.getToolBar();
        setContentView(mToolbarUtil.getContentView());
        //设置标题栏为Toolbar
        setSupportActionBar(mToolbar);
        onCreateCustomToolbar(mToolbar);

        initActivityView();

    }

    private void initActivityView(){
        initDataBeforeView();
        initToolbar();
        initView();
        initData();
        initListener();
    }

    @Override
    public View getContentView() {
        return null;
    }


    /**
     * 获取toolbar
     * @return
     */
    public Toolbar getToolbar(){
        return mToolbar;
    }

    /**
     * 初始化toolbar的一些功能，比如点击绑定等
     */
    protected abstract void initToolbar();

    /**
     * 获取toolbar的布局view
     * @return
     */
    protected int getToolbarLayout(){
        return R.layout.default_toolbar_layout;
    }

    /**
     * 自定义Toolbar
     * @param toolbar
     */
    protected void onCreateCustomToolbar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
        getLayoutInflater().inflate(getToolbarLayout(), toolbar);
    }
}
