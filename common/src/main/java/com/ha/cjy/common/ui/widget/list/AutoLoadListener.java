package com.ha.cjy.common.ui.widget.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.ha.cjy.common.ui.ILoadViewResult;

/**
 * 列表滑动到底部自动加载监听器
 * Created by cjy on 2018/7/17.
 */

public class AutoLoadListener extends RecyclerView.OnScrollListener implements ILoadViewResult {
    private final static String TAG = "AutoLoadListener";
    //下一页监听回调
    private INextLoadCallBack mCallBack;
    //开始的位置
    private int mPrepareIndex;
    //是否加载更多
    private boolean isLoadingMore;
    //记录最后一次位置
    int[] lastPositions;

    public AutoLoadListener(INextLoadCallBack mCallBack, int prepareIndex) {
        this.mCallBack = mCallBack;
        this.mPrepareIndex = prepareIndex;
    }

    public AutoLoadListener(INextLoadCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
        int lastVisibleItem = 0;
        //获取最后一个item的位置
        if(mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        } else if(mLayoutManager instanceof StaggeredGridLayoutManager) {
            if(this.lastPositions == null) {
                this.lastPositions = new int[((StaggeredGridLayoutManager)mLayoutManager).getSpanCount()];
            }

            ((StaggeredGridLayoutManager)mLayoutManager).findLastVisibleItemPositions(this.lastPositions);
            lastVisibleItem = this.findMax(this.lastPositions);
        }

        //列表的item数量
        int totalItemCount = mLayoutManager.getItemCount();
        if(lastVisibleItem >= totalItemCount - this.mPrepareIndex && dy > 0) {
            if(!this.isLoadingMore) {
                if(this.mCallBack != null) {
                    this.mCallBack.loadNextPage();
                }

                this.isLoadingMore = true;
                Log.e(TAG, "onScrolled: isLoadingMore==true");
            } else {
                Log.e(TAG, "哥正在加载别吵吵...");
            }
        }

    }

    /**
     * 获取最大的坐标
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        int[] var3 = lastPositions;
        int var4 = lastPositions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int value = var3[var5];
            if(value > max) {
                max = value;
            }
        }

        return max;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public void onLoadFailed() {
        this.isLoadingMore = false;
    }

    public void onLoadSuccess() {
        this.isLoadingMore = false;
    }

    public void onLoadComplete() {
        this.isLoadingMore = false;
    }

    public void onLoadEmpty() {
        this.isLoadingMore = false;
    }
}
