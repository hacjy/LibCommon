package com.ha.cjy.common.util.download.util;

import com.ha.cjy.common.util.download.abst.ADownloadWorker;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 下载线程工具
 * 
 * @author LinXin
 */
public class DownloadThreadUtil {

	/**
	 * 默认下载线程数
	 */
	private final static int DEFAULT_DOWNLOAD_SIZE = 5;

	/**
	 * 下载线程池
	 */
	private static ThreadPoolExecutor mDownloadThreadPoolExecutor = new ThreadPoolExecutor(DEFAULT_DOWNLOAD_SIZE, DEFAULT_DOWNLOAD_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());;

	/**
	 * 处理下载操作
	 * 
	 * @param command
	 */
	public static void excuteDownload(Runnable command) {
		mDownloadThreadPoolExecutor.execute(command);
	}

	/**
	 * 清除所有下载任务
	 * 
	 * @return
	 */
	public static List<Runnable> clearAllDownloadTask() {
		return mDownloadThreadPoolExecutor.shutdownNow();
	}

	/**
	 * 移除一个等待中的任务
	 * 
	 * @param worker
	 * @return
	 */
	public static boolean remove(ADownloadWorker<? extends BaseDownloadInfo> worker) {
		return mDownloadThreadPoolExecutor.remove(worker);
	}
}
