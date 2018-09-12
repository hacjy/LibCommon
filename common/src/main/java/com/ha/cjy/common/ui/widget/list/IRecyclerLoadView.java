package com.ha.cjy.common.ui.widget.list;

import com.ha.cjy.common.ui.ILoadViewResult;
import com.ha.cjy.common.ui.stateview.ILoadStateViewResult;

/**
 * 列表加载接口
 * Created by cjy on 2018/7/17.
 */

public interface IRecyclerLoadView extends ILoadStateViewResult {
    /**
     * 获取适配器
     * @return
     */
    IAdapterHelp getAdapter();

    /**
     * 获取加载状态
     * @return
     */
    ILoadViewResult getLoadViewResultState();
}
