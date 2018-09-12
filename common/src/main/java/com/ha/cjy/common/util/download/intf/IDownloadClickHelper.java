package com.ha.cjy.common.util.download.intf;


import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 下载View帮助类
 * 
 * @author linxin
 * 
 */
public interface IDownloadClickHelper<T extends BaseDownloadInfo> {

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
	 * 下载完成后的点击事件
	 */
	public void onDownloadedClick();

	/**
	 * 点击下载前的点击事件
	 */
	public void onDownloadNewClick();

	/**
	 * 等待时点击事件
	 */
	public void onDownloadWaitClick();

	/**
	 * 下载中点击事件
	 */
	public void onDownloadingClick();

	/**
	 * 暂停中点击事件
	 */
	public void onDownloadPausingClick();

	/**
	 * 已暂停点击事件
	 */
	public void onDownloadPausedClick();

	/**
	 * 取消中点击事件
	 */
	public void onDownloadCancelingClick();

	/**
	 * 下载失败点击事件
	 */
	public void onDownloadFailedClick();

	/**
	 * 未知状态点击事件
	 */
	public void onDownloadNoneClick();

	/**
	 * 连接中点击事件
	 *
	 */
	public void onDownloadConnectingClick();
}
