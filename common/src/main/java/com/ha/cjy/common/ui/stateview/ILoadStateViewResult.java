package com.ha.cjy.common.ui.stateview;

/**
 * 页面加载结果接口
 * Created by cjy on 2018/7/16.
 */

public interface ILoadStateViewResult {

    /**
     * 加载成功
     */
    void onLoadSuccess();

    /**
     * 加载失败
     */
    void onLoadFailed();

    /**
     * 加载空视图
     */
    void onLoadEmpty();
}
