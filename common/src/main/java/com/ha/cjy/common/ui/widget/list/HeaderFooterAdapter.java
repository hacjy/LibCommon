package com.ha.cjy.common.ui.widget.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 支持添加头部底部的列表适配器
 * Created by cjy on 2018/7/17.
 */

public class HeaderFooterAdapter extends RecyclerView.Adapter implements IWrapperAdapter {
    private RecyclerView.Adapter mAdapter;
    private ArrayList<View> mHeaderViews;
    private ArrayList<View> mFootViews;
    static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList();
    private int mCurrentPosition;

    public HeaderFooterAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFootViews, RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        if(mHeaderViews == null) {
            this.mHeaderViews = EMPTY_INFO_LIST;
        } else {
            this.mHeaderViews = mHeaderViews;
        }

        if(mHeaderViews == null) {
            this.mFootViews = EMPTY_INFO_LIST;
        } else {
            this.mFootViews = mFootViews;
        }

    }

    public int getHeadersCount() {
        return this.mHeaderViews.size();
    }

    public int getFootersCount() {
        return this.mFootViews.size();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (RecyclerView.ViewHolder)(viewType == -1?new HeaderFooterAdapter.HeaderViewHolder((View)this.mHeaderViews.get(0)):(viewType == -2?new HeaderFooterAdapter.HeaderViewHolder((View)this.mFootViews.get(0)):this.mAdapter.onCreateViewHolder(parent, viewType)));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = this.getHeadersCount();
        if(position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = 0;
            if(this.mAdapter != null) {
                adapterCount = this.mAdapter.getItemCount();
                if(adjPosition < adapterCount) {
                    this.mAdapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }

        }
    }

    public int getItemCount() {
        return this.mAdapter != null?this.getHeadersCount() + this.getFootersCount() + this.mAdapter.getItemCount():this.getHeadersCount() + this.getFootersCount();
    }

    public int getItemViewType(int position) {
        this.mCurrentPosition = position;
        int numHeaders = this.getHeadersCount();
        if(position < numHeaders) {
            return -1;//头部
        } else {
            int adjPosition = position - numHeaders;
            int adapterCount = 0;
            if(this.mAdapter != null) {
                adapterCount = this.mAdapter.getItemCount();
                if(adjPosition < adapterCount) {
                    return this.mAdapter.getItemViewType(adjPosition);
                }
            }

            return -2;//底部
        }
    }

    public long getItemId(int position) {
        int numHeaders = this.getHeadersCount();
        if(this.mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = this.mAdapter.getItemCount();
            if(adjPosition < adapterCount) {
                return this.mAdapter.getItemId(adjPosition);
            }
        }

        return -1L;
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return this.mAdapter;
    }


    /**
     * ViewHolder
     */
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
