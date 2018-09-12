package com.ha.cjy.common.util.download.abst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ha.cjy.common.util.download.BaseDownloadWorker;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadView;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;


/**
 * 下载显示帮助类
 * 
 * @author LinXin
 */
public abstract class ADownloadDisplayHelper<T extends BaseDownloadInfo> implements IDownloadDisplayHelper<T> {

	private DownloadViewNotifyBroadcastReceiver mReceiver;
	private IDownloadView<T> mDownloadView;
	private Context mContext;

	public ADownloadDisplayHelper(IDownloadView<T> downloadView) {
		mDownloadView = downloadView;
		mContext = mDownloadView.getContext();
	}

	public DownloadViewNotifyBroadcastReceiver getReceiver() {
		return mReceiver;
	}

	public void setReceiver(DownloadViewNotifyBroadcastReceiver receiver) {
		this.mReceiver = receiver;
	}

	@Override
	public void registerDownloadReceiver() {
		IntentFilter filter = new IntentFilter(BaseDownloadWorker.NOTIFY_VIEW_ACTION);
		if (mReceiver == null) {
			mReceiver = new DownloadViewNotifyBroadcastReceiver();
		}
		mContext.registerReceiver(mReceiver, filter);
	}

	@Override
	public void unregisterDownloadReceiver() {
		if (mReceiver != null) {
			mContext.unregisterReceiver(mReceiver);
		}
	};

	/**
	 * 默认下载视图刷新广播
	 */
	private class DownloadViewNotifyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			onReceiveResult(context, intent);
		}

	}
	public void onReceiveResult(Context context, Intent intent){
		T info = intent.getParcelableExtra(BaseDownloadWorker.NOTIFY_VIEW_ACTION_EXTRA_INFO_KEY);
		if (mDownloadView.checkDownloadState(info)) {
			mDownloadView.setDownloadInfo(info);
		}
	}
	


}
