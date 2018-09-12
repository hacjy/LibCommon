package com.ha.cjy.common.util.download;

import android.content.Context;

import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;
import com.ha.cjy.common.util.download.kernel.DownloadServiceConnection;
import com.ha.cjy.common.util.download.util.DownloadObjectUtil;

import java.util.Map;

/**
 * 下载基本操作类
 * 
 * @author LinXin
 */
public class BaseDownloadOperate {

	/**
	 * 添加新下载任务
	 * 
	 * @param context
	 * @param info
	 */
	public static void addNewDownloadTask(Context context, BaseDownloadInfo info) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		connection.addDownloadTask(DownloadObjectUtil.object2Map(info));
	}

	/**
	 * 暂停下载任务
	 * 
	 * @param context
	 * @param info
	 */
	public static void pauseDownloadTask(Context context, BaseDownloadInfo info) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		connection.pause(DownloadObjectUtil.object2Map(info));
	}

	/**
	 * 取消下载任务
	 * 
	 * @param context
	 * @param info
	 */
	public static void cancelDownloadTask(Context context, BaseDownloadInfo info) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		connection.cancel(DownloadObjectUtil.object2Map(info));
	}

	/**
	 * 获取下载信息
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static BaseDownloadInfo getDownloadInfo(Context context, String id) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		Map map = connection.getDownloadInfo(id);
		if(map==null){
			return null;
		}
		BaseDownloadInfo info = DownloadObjectUtil.map2DownloadInfo(map);
		return info;
	}

	/**
	 * 是否绑定
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBind(Context context, String id) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		return connection.isBind();
	}

	/**
	 * 清除所有任务
	 * 
	 * @param context
	 */
	public static void clearAllDownloadTask(Context context) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		connection.clearAllDownloadTask();
	}

	/**
	 * 获取所有下载信息
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map getDownloadInfos(Context context) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		return connection.getDownloadInfos();
	}

	/**
	 * 获取下载任务总数
	 * 
	 * @param context
	 * @return
	 */
	public static int getTaskCount(Context context) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		return connection.getTaskCount();
	}

	/**
	 * 继续下载
	 * 
	 * @param context
	 * @param info
	 * @return
	 */
	public static boolean continueDownload(Context context, BaseDownloadInfo info) {
		DownloadServiceConnection connection = new DownloadServiceConnection(context);
		return connection.continueDownload(DownloadObjectUtil.object2Map(info));
	}

}
