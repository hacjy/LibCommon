package com.ha.cjy.common.ui.stateview;

import android.content.Context;
import android.view.View;

/**
 * 生成对应的状态视图
 * Created by cjy on 2018/7/16.
 */

public class LoadStateViewHelper implements ILoadStateView {

    //状态视图
    protected View mContentView;
    private View mLoadEmptyView;
    private View mLoadFailedView;

    protected Context mContext;
    protected View.OnClickListener mOnClick;
    private ILoadStateView mLoadHelper;

    /**
     *
     * @param mContext
     * @param mLoadHelper 使用该代理，主要是让外界能够传入自定义的页面，灵活可扩展
     */
    public LoadStateViewHelper(Context mContext, ILoadStateView mLoadHelper) {
        this.mContext = mContext;
        this.mLoadHelper = mLoadHelper;
    }

    public LoadStateViewHelper(Context mContext, View mContentView,View mLoadEmptyView, View mLoadFailedView) {
        this.mContext = mContext;
        this.mContentView = mContentView;
        this.mLoadEmptyView = mLoadEmptyView;
        this.mLoadFailedView = mLoadFailedView;
    }

    public LoadStateViewHelper(Context mContext, View mContentView,View mLoadEmptyView, View mLoadFailedView, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mContentView = mContentView;
        this.mLoadEmptyView = mLoadEmptyView;
        this.mLoadFailedView = mLoadFailedView;
        this.mOnClick = onClickListener;
    }

    public void setLoadEmptyView(View mLoadEmptyView) {
        this.mLoadEmptyView = mLoadEmptyView;
    }

    public void setLoadFailedView(View mLoadFailedView) {
        this.mLoadFailedView = mLoadFailedView;
    }

    @Override
    public View getContentView() {
        if (mLoadHelper != null){
            return mLoadHelper.getContentView();
        }
        return mContentView;
    }

    @Override
    public View getLoadFailedView(View view) {
        if (mLoadHelper != null){
            return mLoadHelper.getLoadFailedView(view);
        }
        if (mLoadFailedView != null){
            return mLoadFailedView;
        }
        if (mContentView == null){
            return null;
        }
        //返回默认的失败视图
        return LocalStateViewFactory.getLoadDefaultFailedView(mContext,view,mOnClick);
    }

    @Override
    public View getLoadEmptyView(View view) {
        if (mLoadHelper != null){
            return mLoadHelper.getLoadEmptyView(view);
        }
        if (mLoadEmptyView != null){
            return mLoadEmptyView;
        }
        if (mContentView == null){
            return null;
        }
        //返回默认的空视图
        return LocalStateViewFactory.getLoadDefaultEmptyView(mContext,view,mOnClick);
    }


}
