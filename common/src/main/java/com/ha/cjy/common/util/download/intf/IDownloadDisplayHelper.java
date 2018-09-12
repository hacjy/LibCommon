package com.ha.cjy.common.util.download.intf;

import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 显示帮助类
 * 
 * @author LinXin
 */
public interface IDownloadDisplayHelper<T extends BaseDownloadInfo> {

	/**
	 * 设置下载对象
	 * 
	 * @param downloadInfo
	 */
	public void setDownloadInfo(T downloadInfo);

	/**
	 * 获取下载对象
	 * 
	 * @return
	 */
	public T getDownloadInfo();

	/**
	 * 注册广播监听
	 *
	 */
	public void registerDownloadReceiver();

	/**
	 * 反注册广播监听
	 *
	 */
	public void unregisterDownloadReceiver();

	/**
	 * 点击下载前的状态
	 *
	 */
	public void onDownloadNewDisplay();

	/**
	 * 等待状态
	 *
	 */
	public void onDownloadWaitDisplay();

	/**
	 * 下载中状态
	 *
	 */
	public void onDownloadingDisplay();

	/**
	 * 已下载状态
	 *
	 */
	public void onDownloadedDisplay();

	/**
	 * 暂停中状态
	 *
	 */
	public void onDownloadPausingDisplay();

	/**
	 * 已暂停状态
	 *
	 */
	public void onDownloadPausedDisplay();

	/**
	 * 取消中状态
	 *
	 */
	public void onDownloadCancelingDisplay();

	/**
	 * 下载失败状态
	 *
	 */
	public void onDownloadFailedDisplay();

	/**
	 * 未知状态
	 *
	 */
	public void onDownloadNoneDisplay();

	/**
	 * 连接中状态
	 *
	 */
	public void onDownloadConnectDisplay();

}
