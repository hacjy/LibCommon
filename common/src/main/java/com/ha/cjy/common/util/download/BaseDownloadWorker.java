package com.ha.cjy.common.util.download;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.ha.cjy.common.util.download.abst.ADownloadWorker;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;
import com.ha.cjy.common.util.download.kernel.DownloadWorkerSupervisor;


/**
 * 基础下载器
 *
 */
public class BaseDownloadWorker<T extends BaseDownloadInfo> extends ADownloadWorker<T> {

	/** 刷新视图广播Action */
	public final static String NOTIFY_VIEW_ACTION = "com.kaopu.core.download.BaseDownloadWorker.notify_view_action";
	/** 刷新视图广播信息KEY */
	public final static String NOTIFY_VIEW_ACTION_EXTRA_INFO_KEY = "info_key";
	/** 上次计算速度时的时间 */
	private long timeOld;
	/** 上次计算速度时的大小 */
	private long sizeOld;
	/** 下载回调 */
	private DownloadCallBack mCallBack;
	/** 上下文，用于发送刷新广播 */
	protected Context mContext;

	public BaseDownloadWorker(Context context, T info) {
		super(info);
		mContext = context;
		mCallBack = info.getCallBack();
	}

	@Override
	protected void onDownloadCanceled(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadNewState());
		if (mCallBack != null) {
			mCallBack.onDownloadCanceled(identification);
		}
		DownloadWorkerSupervisor.remove(identification);
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadPaused(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadPausedState());
		if (mCallBack != null) {
			mCallBack.onDownloadPaused(identification);
		}
		DownloadWorkerSupervisor.remove(identification);
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadWorking(String identification, long totalSize, long downloadSize, int progress) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadingState());
		Log.e("download","ssss"+downloadSize+"    "+totalSize);
		mDownloadInfo.setfSize(totalSize);
		mDownloadInfo.setdSize(downloadSize);
		if (timeOld == 0) {
			timeOld = System.currentTimeMillis();
			sizeOld = downloadSize;
			mDownloadInfo.setSpeed(0);
		} else {
			final long timeNow = System.currentTimeMillis();
			final long timeInterval = timeNow - timeOld;
			final long addSize = downloadSize - sizeOld;
			final long speed = addSize / timeInterval;
			mDownloadInfo.setSpeed(speed);
		}
		if (mCallBack != null) {
			
			mCallBack.onDownloadWorking(identification, totalSize, downloadSize, progress);
		}
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadCompleted(String identification, String file, long totalSize) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadedState());
		mDownloadInfo.setDownloadedTime(System.currentTimeMillis());
		if (mCallBack != null) {
			mCallBack.onDownloadCompleted(identification, file, totalSize);
		}
		DownloadWorkerSupervisor.remove(identification);
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadConnecting(String identification, long downloadSize) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadConnectingState());
		mDownloadInfo.setdSize(downloadSize);
		if (downloadSize == 0) {
			mDownloadInfo.setCreateTime(System.currentTimeMillis());
		}
		if (mCallBack != null) {
			mCallBack.onDownloadStart(identification, downloadSize);
		}
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadWait(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadWaitState());
		if (mCallBack != null) {
			mCallBack.onDownloadWait(identification);
		}
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadPausing(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadPausingState());
		if (mCallBack != null) {
			mCallBack.onDownloadPausing(identification);
		}
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadCanceling(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadCancelingState());
		if (mCallBack != null) {
			mCallBack.onDownloadCanceling(identification);
		}
		sendNotifyBroadcast();
	}

	@Override
	protected void onDownloadFailed(String identification) {
		mDownloadInfo.setState(BaseDownloadStateFactory.getDownloadFailedState());
		if (mCallBack != null) {
			mCallBack.onDownloadFailed(identification);
		}
		DownloadWorkerSupervisor.remove(identification);
		sendNotifyBroadcast();
	}

	private void sendNotifyBroadcast() {
		Intent intent = new Intent(NOTIFY_VIEW_ACTION);
		intent.putExtra(NOTIFY_VIEW_ACTION_EXTRA_INFO_KEY, mDownloadInfo);
		mContext.sendBroadcast(intent);
	}

	/***
	 * 下载回调，处理额外的一些操作，如要在这里面改变界面，需用handler
	 *
	 */
	public interface DownloadCallBack extends Parcelable {
		/**
		 * 取消下载
		 * 
		 * @param id
		 */
		public void onDownloadCanceled(String id);

		/**
		 * 暂停下载
		 * 
		 * @param id
		 */
		public void onDownloadPaused(String id);

		/***
		 * 下载中
		 * 
		 * @param id
		 * @param totalSize
		 * @param downloadSize
		 * @param progress
		 */
		public void onDownloadWorking(String id, long totalSize, long downloadSize, int progress);

		/**
		 * 下载完成
		 * 
		 * @param id
		 * @param file
		 * @param totalSize
		 */
		public void onDownloadCompleted(String id, String file, long totalSize);

		/**
		 * 下载开始
		 * 
		 * @param id
		 * @param downloadSize
		 */
		public void onDownloadStart(String id, long downloadSize);

		/**
		 * 等待下载
		 * 
		 * @param id
		 */
		public void onDownloadWait(String id);

		/**
		 * 暂停中
		 * 
		 * @param id
		 */
		public void onDownloadPausing(String id);

		/**
		 * 取消中
		 * 
		 * @param id
		 */
		public void onDownloadCanceling(String id);

		/**
		 * 下载失败
		 * 
		 * @param id
		 */
		public void onDownloadFailed(String id);

	}

}
