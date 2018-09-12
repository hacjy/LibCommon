package com.ha.cjy.common.ui.widget.list;

/**
 * 请求数据的p层接口
 * Created by cjy on 2018/7/17.
 */

public interface IHttpPresenter {
    /**
     * 加载
     */
    void load();

    /**
     * 重新加载
     */
    void refreshLoad();

    /**
     * 开始时加载数据
     */
    void initRequestData();
}
