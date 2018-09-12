package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 支持头部，底部添加视图的RecyclerView
 * Created by cjy on 2018/7/17.
 */

public class HeaderFooterRecyclerView extends RecyclerView {
    /**
     * 头部视图集合
     */
    private ArrayList<View> mHeaderViews = new ArrayList();
    /**
     * 顶部视图集合
     */
    private ArrayList<View> mFootViews = new ArrayList();
    /**
     * 适配器
     */
    private Adapter mAdapter;

    public HeaderFooterRecyclerView(Context context) {
        super(context);
    }

    public HeaderFooterRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderFooterRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view) {
        this.mHeaderViews.clear();
        this.mHeaderViews.add(view);
        if(this.mAdapter != null && !(this.mAdapter instanceof HeaderFooterAdapter)) {
            this.mAdapter = new HeaderFooterAdapter(this.mHeaderViews, this.mFootViews, this.mAdapter);
        }

    }

    public void removeHeaderView() {
        this.mHeaderViews.clear();
        if(this.mAdapter != null && !(this.mAdapter instanceof HeaderFooterAdapter)) {
            this.mAdapter = new HeaderFooterAdapter(this.mHeaderViews, this.mFootViews, this.mAdapter);
        }

    }

    public void addFootView(View view) {
        this.mFootViews.clear();
        this.mFootViews.add(view);
        if(this.mAdapter != null && !(this.mAdapter instanceof HeaderFooterAdapter)) {
            this.mAdapter = new HeaderFooterAdapter(this.mHeaderViews, this.mFootViews, this.mAdapter);
        }

    }

    public void setAdapter(Adapter adapter) {
        if(this.mHeaderViews.isEmpty() && this.mFootViews.isEmpty()) {
            super.setAdapter((Adapter)adapter);
        } else {
            adapter = new HeaderFooterAdapter(this.mHeaderViews, this.mFootViews, (Adapter)adapter);
            super.setAdapter((Adapter)adapter);
        }

        this.mAdapter = (Adapter)adapter;
    }
}
