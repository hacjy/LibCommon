package com.ha.cjy.libcommon.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;

/**
 * 测试：默认的ViewPager视图：3个tab项
 * Created by cjy on 2018/7/18.
 */

public class GameViewPagerViewActivity extends BaseToolbarActivity {
    private FrameLayout mLayoutContent;
    private GameViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_viewpager);
    }

    @Override
    public void initDataBeforeView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mLayoutContent = findViewById(R.id.game_viewpager);
        mViewPager = new GameViewPager(GameViewPagerViewActivity.this);
        mLayoutContent.addView(mViewPager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public View getContentView() {
        return mViewPager;
    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackToolbar(GameViewPagerViewActivity.this,"","我的游戏列表");
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.default_toolbar_layout;
    }
}
