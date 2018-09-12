package com.ha.cjy.common.ui;

/**
 * 一切view都可以实现该接口：抽象出视图的一些通用步骤
 * Created by cjy on 2018/7/17.
 */

public interface IInitView {
    /**
     * 在视图创建之前初始化数据
     */
    void initDataBeforeView();
    /**
     * 初始化数据
     */
    void initData();
    /**
     * 初始化视图
     */
    void initView();
    /**
     * 初始化监听
     */
    void initListener();
}
