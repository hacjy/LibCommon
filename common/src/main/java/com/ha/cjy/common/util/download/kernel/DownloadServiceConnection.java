package com.ha.cjy.common.util.download.kernel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.ha.cjy.common.IDownloadService;

import java.util.ArrayList;
import java.util.Map;

/**
 * 绑定下载进程服务后回调逻辑类
 *
 * @author LinXin
 */
public class DownloadServiceConnection implements ServiceConnection {

	private static IDownloadService sAidlService = null;

	private Context mContext = null;

	private boolean mIsConnectionBind = false;

	public DownloadServiceConnection(Context context) {
		mContext = context;
		try {
			if (sAidlService == null || !sAidlService.isServiceAlive())
				context.startService(new Intent(mContext, DownloadService.class));
		} catch (Exception e) {
			context.startService(new Intent(mContext, DownloadService.class));
		}

	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		try {
			IDownloadService remoteService = IDownloadService.Stub.asInterface(service);
			sAidlService = remoteService;
			mIsConnectionBind = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		sAidlService = null;
		mIsConnectionBind = false;
	}

	/**
	 * 跨进程添加下载任务
	 *
	 * @param info
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean addDownloadTask(Map info) {
		if (!startBind())
			return false;
		if (sAidlService != null) {
			try {
				return sAidlService.addNewDownloadTask(info);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 跨进程批量添加下载任务
	 *
	 * @param infos
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean addDownloadTask(ArrayList<Map> infos) {
		if (!startBind())
			return false;

		if (sAidlService != null) {
			try {
				for (Map info : infos) {
					sAidlService.addNewDownloadTask(info);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 暂停下载任务
	 *
	 * @return true 下载队列存在对应的下载任务，等待暂停广播处理<br>
	 *         false 下载队列不存在对应的下载任务，自行处理ui
	 */
	@SuppressWarnings("rawtypes")
	public boolean pause(Map info) {
		if (!startBind())
			return false;

		if (sAidlService != null) {
			try {
				return sAidlService.pauseDownloadTask(info);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 取消下载任务
	 *
	 * @return true 下载队列存在对应的下载任务，等待取消广播处理<br>
	 *         false 下载队列不存在对应的下载任务，自行处理ui
	 */
	@SuppressWarnings("rawtypes")
	public boolean cancel(Map info) {
		if (!startBind())
			return false;

		if (sAidlService != null) {
			try {
				return sAidlService.cancelDownloadTask(info);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 清除总队列
	 */
	public boolean clearAllDownloadTask() {
		if (!startBind())
			return false;

		try {
			sAidlService.clearAllDownloadTask();
			return true;
		} catch (Exception e) {
		}

		return false;
	}

	/**
	 * 获取下载总队列
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, BaseDownloadInfo> getDownloadInfos() {
		if (!startBind())
			return null;

		try {
			return (Map<String, BaseDownloadInfo>) sAidlService.getDownloadInfos();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取单个下载任务
	 *
	 *        标识
	 * @return 下载任务
	 */
	@SuppressWarnings("rawtypes")
	public Map getDownloadInfo(String id) {
		if (!startBind())
			return null;

		try {
			return sAidlService.getDownloadInfo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回任务数，包括所有状态的
	 *
	 * @return
	 */
	public int getTaskCount() {
		if (!startBind())
			return 0;

		try {
			return sAidlService.getTaskCount();
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 绑定下载服务，异步型，绑定后服务实例不能马上获取到，需等绑定完成后回调通知才可确认服务实例可用
	 *
	 *        执行回调时切到UI线程的处理,
	 * @param callBack
	 *        回调的接口
	 */
	public void bindDownloadService(final ConnectionCallBack<Boolean> callBack) {
		if (isBind()) {
			// 回调接口
			if (callBack != null)
				callBack.invoke(Boolean.valueOf(true));
			return;
		}

		// 开始绑定
		boolean bindSuccess = mContext.bindService(new Intent(mContext, DownloadService.class), DownloadServiceConnection.this, Context.BIND_AUTO_CREATE);

		if (!bindSuccess) {
			// 回调接口
			if (callBack != null)
				callBack.invoke(Boolean.valueOf(false));
		}

	}// end bindDownloadService

	/**
	 * <br>
	 * Description:解除下载服务绑定 <br>
	 * Author:zhuchenghua <br>
	 * Date:2013-4-19下午05:30:50
	 */
	public void unBindDownloadService() {
		try {
			if (mIsConnectionBind) {
				mContext.unbindService(DownloadServiceConnection.this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始绑定
	 *
	 * @return
	 */
	public boolean startBind() {
		if (isBind())
			return true;
		try {
			boolean bindSuccess = mContext.bindService(new Intent(mContext, DownloadService.class), DownloadServiceConnection.this, Context.BIND_AUTO_CREATE);
			return bindSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				mContext.unbindService(DownloadServiceConnection.this);
			} catch (Exception e2) {
			}
		}
		return false;
	}// end startBind

	/**
	 * 注销绑定的下载服务
	 *
	 * @param context
	 */
	/*
	 * public void unBindDownloadService() { try {
	 * mContext.unbindService(DownloadServerServiceConnection.this); } catch
	 * (Exception e) { } }
	 */

	/**
	 * 是否已绑定
	 *
	 * @return
	 */
	public boolean isBind() {
		try {
			if (sAidlService != null && sAidlService.isServiceAlive())
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 继续下载
	 */
	@SuppressWarnings("rawtypes")
	public boolean continueDownload(Map info) {
		if (!startBind())
			return false;

		try {
			return sAidlService.continueDownload(info);
		} catch (Exception e) {
		}

		return false;
	}
}
