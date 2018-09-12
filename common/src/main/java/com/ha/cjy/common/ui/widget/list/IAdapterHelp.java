package com.ha.cjy.common.ui.widget.list;


import java.util.List;

/**
 * 适配器帮助类
 * Created by cjy on 2018/7/17.
 */

public interface IAdapterHelp<T> {
    /**
     * 往指定位置添加数据
     * @param position
     * @param data
     */
    void addDataNotifyDataSetChanged(int position, T data);

    /**
     * 添加数据
     * @param data
     */
    void addDataNotifyDataSetChanged(T data);

    /**
     * 添加列表数据
     * @param data
     */
    void addBatchDataNotifyDataSetChanged(List<T> data);

    /**
     * 清除数据
     */
    void clear();

    /**
     * 获取adapter的item数量
     * @return
     */
    int getCount();

    /**
     * 获取item数据
     * @param position
     * @return
     */
    T getItem(int position);

    /**
     * 获取列表数据
     * @return
     */
    List<T> getData();

    /**
     * 更新数据
     * @param data
     */
    void notifyDataSetChanged(List<T> data);

    /**
     * 移除数据
     * @param position
     */
    void removeData(int position);
}
