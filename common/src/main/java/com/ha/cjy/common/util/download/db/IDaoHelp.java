package com.ha.cjy.common.util.download.db;

import java.util.List;

public interface IDaoHelp<T, ID> {

    /**
     * 添加
     * @param t 对象
     * @return
     */
    public int insert(T t);
    /**
     * 批量添加
     * @param data 对象集合
     */
    public void batchInsert(List<T> data);
    
    /**
     * 修改
     * @param t 对象
     * @return
     */
    public int update(T t);
    /**
     * 修改
     * @param data 对象集合
     */
    public void batchUpdate(List<T> data);
    /**
     * 先修改后添加
     * @param t 对象
     */
    public void insertOrUpdate(T t);
    
    /**
     * 批量 先修改后添加
     * @param data 对象集合
     */
    public void batchInsertOrUpdate(List<T> data);
    /**
     * 删除
     * @param t 对象
     * @return
     */
    public int delete(T t);
    /**
     * 删除
     * @param id 主键
     * @return
     */
    public int deleteById(ID id);
    /**
     * 批量删除
     * @param ids 主鍵集合
     * @return
     */
    public int batchDelete(List<ID> ids);
    /**
     * 删除所有
     * @return
     */
    public int deleteAll();
    
    /**
     * 查询
     * @param id 主键
     * @return
     */
    public T query(ID id);
    
    /**
     * 查询所有
     * @return
     */
    public List<T> queryAll();

    /**
     * 表的记录数
     * @return
     */
    public long getCount();
    /**
     * 表是否已经开启
     * @return
     */
    public boolean isOpen();
}
