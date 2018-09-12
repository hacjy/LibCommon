package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ha.cjy.common.ui.ILoadViewResult;

/**
 * 列表控件：支持加载更多,支持item click
 * Created by cjy on 2018/7/16.
 */

public class LoadRecyclerView extends ItemClickRecyclerView implements ILoadViewResult {
    /**
     * 自动加载监听
     */
    private AutoLoadListener autoLoadListener;
    /**
     * 加载结果监听
     */
    private ILoadViewResult mLoadViewResult;

    public LoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadRecyclerView(Context context) {
        super(context);
    }

    public void addLoadFootView(View v) {
        if(v instanceof ILoadViewResult) {
            this.addFootView(v);
        } else {
            Log.i("LoadRecyclerView", "骚年,你加脚的方式不正确");
        }

    }

    public void onLoadFailed() {
        if(this.autoLoadListener != null) {
            this.autoLoadListener.onLoadFailed();
        }

        if(this.mLoadViewResult != null) {
            this.mLoadViewResult.onLoadFailed();
        }

    }

    public void onLoadSuccess() {
        if(this.autoLoadListener != null) {
            this.autoLoadListener.onLoadSuccess();
        }

        if(this.mLoadViewResult != null) {
            this.mLoadViewResult.onLoadSuccess();
        }

    }

    public void onLoadComplete() {
        if(this.autoLoadListener != null) {
            this.autoLoadListener.onLoadComplete();
        }

        if(this.mLoadViewResult != null) {
            this.mLoadViewResult.onLoadComplete();
        }

    }

    public void onLoadEmpty() {
        if(this.autoLoadListener != null) {
            this.autoLoadListener.onLoadEmpty();
        }

        if(this.mLoadViewResult != null) {
            this.mLoadViewResult.onLoadEmpty();
        }

    }

    public void setLoadViewResult(ILoadViewResult loadViewResult){
        mLoadViewResult = loadViewResult;
    }

    /**
     * 预加载
     * @param mCallBack
     * @param prepareIndex
     */
    public void prepareLoad(INextLoadCallBack mCallBack, int prepareIndex) {
        this.autoLoadListener = new AutoLoadListener(mCallBack, prepareIndex);
        this.addOnScrollListener(this.autoLoadListener);
    }
}
