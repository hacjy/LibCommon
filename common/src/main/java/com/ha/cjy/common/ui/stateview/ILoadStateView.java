package com.ha.cjy.common.ui.stateview;

import android.view.View;

/**
 * 获取对应状态view的接口
 * Created by cjy on 2018/7/16.
 */

public interface ILoadStateView {
    /**
     * 获取当前要显示的内容视图
     * @return
     */
    View getContentView();

    /**
     * 获取失败后显示的视图
     * @return
     */
    View getLoadFailedView(View view);

    /**
     * 获取空视图
     * @return
     */
    View getLoadEmptyView(View view);
}
