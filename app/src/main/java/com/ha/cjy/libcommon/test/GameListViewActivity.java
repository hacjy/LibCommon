package com.ha.cjy.libcommon.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;

/**
 * 测试：列表页面 DefaultListView的用法展示
 * Created by cjy on 2018/7/17.
 */

public class GameListViewActivity extends BaseToolbarActivity {
    /**
     * 列表容器
     */
    private FrameLayout mLayoutListViewContainer;
    /**
     * 列表
     */
    private GameListView mGameListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_game_listview);
    }

    @Override
    public void initView() {
        mLayoutListViewContainer = findViewById(R.id.layout_listview_container);
        mGameListView = new GameListView(GameListViewActivity.this);
        mLayoutListViewContainer.addView(mGameListView);
    }

    @Override
    public void initData() {
        mGameListView.requestionData();
    }


    @Override
    public void initListener() {

    }


    @Override
    public View getContentView() {
        return mLayoutListViewContainer;
    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackToolbar(GameListViewActivity.this,"","游戏列表");
    }
}
