package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 快速开发的RecyclerView
 * Created by cjy on 2018/9/12.
 */

public class FastRecyclerView extends DefaultRecyclerView{

    public FastRecyclerView(Context context) {
        super(context);
    }

    public FastRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public IHttpPresenter getIHttpPresenter() {
        return mP;
    }

    @Override
    public IAdapterHelp getRecyclerViewAdapter() {
        return mAdapter;
    }

    @Override
    public void onClickLoadFailed() {
        mP.initRequestData();
    }

    @Override
    public void onClickLoadEmpty() {
        mP.initRequestData();
    }
}
