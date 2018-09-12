package com.ha.cjy.common.ui;

import com.ha.cjy.common.ui.stateview.ILoadStateViewResult;

/**
 * 加载状态接口
 * Created by cjy on 2018/7/17.
 */

public interface ILoadViewResult extends ILoadStateViewResult {
    /**
     * 加载完成
     */
    void  onLoadComplete();
}
