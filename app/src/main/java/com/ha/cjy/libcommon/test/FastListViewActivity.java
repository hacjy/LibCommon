package com.ha.cjy.libcommon.test;

import android.os.Bundle;
import android.view.View;

import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.ui.widget.list.FastRecyclerView;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;

/**
 * 直接使用FastRecyclerView的Activity
 */
public class FastListViewActivity extends BaseToolbarActivity {
    private FastRecyclerView mListView;
    private GameListViewAdapter mAdapter;
    private FastListViewPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_list_view);
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void initData() {
        mPresenter = new FastListViewPresenter(mListView);
        mListView.setHttpPresenter(mPresenter);

        mAdapter = new GameListViewAdapter(this);
        mListView.setAdapterHelp(mAdapter);

        mPresenter.initRequestData();
    }

    @Override
    public void initView() {
        mListView = findViewById(R.id.listview);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackToolbar(FastListViewActivity.this,"","快速开发列表");
    }
}
