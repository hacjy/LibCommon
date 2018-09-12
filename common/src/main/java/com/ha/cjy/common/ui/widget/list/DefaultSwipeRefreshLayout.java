package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.ha.cjy.common.ui.ILoadViewResult;

/**
 * 默认的下拉刷新
 * Created by cjy on 2018/7/17.
 */

public class DefaultSwipeRefreshLayout extends SwipeRefreshLayout implements ILoadViewResult {
    /**
     * 加载完的接口对象
     */
    protected ILoadViewResult mChildView;
    /**
     * 滑动距离
     */
    private int mTouchSlop;
    /**
     * 上次点击的x坐标
     */
    private float mPrevX;

    public DefaultSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setChildView(ILoadViewResult mChildView) {
        this.mChildView = mChildView;
    }

    public void onLoadFailed() {
        this.setRefreshing(false);
        if(this.mChildView != null) {
            this.mChildView.onLoadFailed();
        }

    }

    public void onLoadSuccess() {
        this.setRefreshing(false);
        if(this.mChildView != null) {
            this.mChildView.onLoadSuccess();
        }

    }

    public void onLoadComplete() {
        this.setRefreshing(false);
        if(this.mChildView != null) {
            this.mChildView.onLoadComplete();
        }

    }

    public void onLoadEmpty() {
        this.setRefreshing(false);
        if(this.mChildView != null) {
            this.mChildView.onLoadEmpty();
        }

    }

    /**
     * 滑动时的拦截
     * @param event
     * @return
     */
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.mPrevX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float xDiff = Math.abs(eventX - this.mPrevX);
                if(xDiff > (float)(this.mTouchSlop + 60)) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
