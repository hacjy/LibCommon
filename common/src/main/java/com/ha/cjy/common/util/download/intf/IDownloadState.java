package com.ha.cjy.common.util.download.intf;

import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;


public interface IDownloadState extends Parcelable {

	/**
	 * 获取下载状态
	 * 
	 * @return
	 */
	public BaseDownloadStateFactory.State getState();

	/**
	 * 点击事件
	 * 
	 * @param clickHelper
	 */
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> clickHelper);

	/**
	 * 显示
	 * 
	 * @param displayHelper
	 */
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> displayHelper);
}
