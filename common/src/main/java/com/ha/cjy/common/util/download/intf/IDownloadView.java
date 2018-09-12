package com.ha.cjy.common.util.download.intf;

import android.content.Context;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;


/**
 * 下载View显示帮助类
 * 
 * @author linxin
 * 
 */
public interface IDownloadView<T extends BaseDownloadInfo> {

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public Context getContext();

	/***
	 * 获取下载对象
	 */
	public T getDownloadInfo();

	/**
	 * 设置下载对象
	 * 
	 * @param info
	 */
	public void setDownloadInfo(T info);

	/**
	 * 核对下载状态
	 */
	public boolean checkDownloadState(T info);

	/**
	 * 取消下载
	 */
	public void cancel();

	/**
	 * 暂停下载
	 */
	public void pause();

	/**
	 * 获取下载状态
	 * 
	 * @return
	 */
	public BaseDownloadStateFactory.State getState();

}
