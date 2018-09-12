package com.ha.cjy.common.ui.stateview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.ha.cjy.common.ui.ILoadViewResult;

/**
 * 状态视图显示控制
 * Created by cjy on 2018/7/16.
 */

public class LoadStateViewResultHelper implements ILoadViewResult{
    /**
     * Message.what 消息码
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_EMPTY = 1;
    public final static int MESSAGE_ID_COMMON_LOADDATA_FAILED = 2;
    public final static int MESSAGE_ID_COMMON_LOADDATA_FINISH = 3;
    public final static int MESSAGE_ID_COMMON_LOADDATA_SUCCESS= 4;

    /**
     * 状态视图
     */
    private View mLoadFailedView;
    private View mContentView;
    private View mLoadEmptyView;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void dispatchMessage(Message msg) {
            switch(msg.what) {
                case MESSAGE_ID_COMMON_LOADDATA_EMPTY:
                    LoadStateViewResultHelper.this.mLoadFailedView.setVisibility(View.GONE);
                    LoadStateViewResultHelper.this.mContentView.setVisibility(View.GONE);
                    LoadStateViewResultHelper.this.mLoadEmptyView.setVisibility(View.VISIBLE);
                    break;
                case MESSAGE_ID_COMMON_LOADDATA_FAILED:
                    LoadStateViewResultHelper.this.mLoadFailedView.setVisibility(View.VISIBLE);
                    LoadStateViewResultHelper.this.mContentView.setVisibility(View.GONE);
                    LoadStateViewResultHelper.this.mLoadEmptyView.setVisibility(View.GONE);
                    break;
                case MESSAGE_ID_COMMON_LOADDATA_FINISH:
                case MESSAGE_ID_COMMON_LOADDATA_SUCCESS:
                    LoadStateViewResultHelper.this.mLoadFailedView.setVisibility(View.GONE);
                    LoadStateViewResultHelper.this.mContentView.setVisibility(View.VISIBLE);
                    LoadStateViewResultHelper.this.mLoadEmptyView.setVisibility(View.GONE);
                    break;
            }

        }
    };

    public LoadStateViewResultHelper(ILoadStateView loadhelper) {
        this.mContentView = loadhelper.getContentView();
        this.mLoadFailedView = loadhelper.getLoadFailedView(this.mContentView);
        this.mLoadEmptyView = loadhelper.getLoadEmptyView(this.mContentView);
    }

    @Override
    public void onLoadFailed() {
        this.mHandler.sendEmptyMessage(MESSAGE_ID_COMMON_LOADDATA_FAILED);
    }

    @Override
    public void onLoadEmpty() {
        this.mHandler.sendEmptyMessage(MESSAGE_ID_COMMON_LOADDATA_EMPTY);
    }

    @Override
    public void onLoadSuccess() {
        this.mHandler.sendEmptyMessage(MESSAGE_ID_COMMON_LOADDATA_SUCCESS);
    }

    @Override
    public void onLoadComplete() {

    }
}
