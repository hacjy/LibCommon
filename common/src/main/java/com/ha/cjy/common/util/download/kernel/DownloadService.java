package com.ha.cjy.common.util.download.kernel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.ha.cjy.common.IDownloadService;
import com.ha.cjy.common.util.download.BaseDownloadWorker;
import com.ha.cjy.common.util.download.abst.ADownloadWorker;
import com.ha.cjy.common.util.download.util.DownloadObjectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 下载管理服务
 *
 * @author LinXin
 */
public class DownloadService extends Service {

	/** 服务是否在开启状态 */
	private boolean isServiceAlive = false;

	/** AIDL实现 */
	private IDownloadService.Stub mDownloadServiceImpl = new IDownloadService.Stub() {

		@Override
		public boolean isServiceAlive() throws RemoteException {
			return isServiceAlive;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean addNewDownloadTask(Map map) throws RemoteException {
			BaseDownloadInfo info = DownloadObjectUtil.map2DownloadInfo(map);
			if (info == null) {
				return false;
			}
			ADownloadWorker<? extends BaseDownloadInfo> worker = createDownloadWorker(info);
			if (worker == null) {
				return false;
			}
			return worker.start();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean pauseDownloadTask(Map map) throws RemoteException {
			BaseDownloadInfo info = DownloadObjectUtil.map2DownloadInfo(map);
			if (info == null) {
				return false;
			}
			ADownloadWorker<? extends BaseDownloadInfo> worker = DownloadWorkerSupervisor.getDownloadWorkerById(info.getIdentification());
			if (worker == null) {
				return false;
			}
			return worker.pause();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean cancelDownloadTask(Map map) throws RemoteException {
			BaseDownloadInfo info = DownloadObjectUtil.map2DownloadInfo(map);
			if (info == null) {
				return false;
			}
			ADownloadWorker<? extends BaseDownloadInfo> worker = DownloadWorkerSupervisor.getDownloadWorkerById(info.getIdentification());
			if (worker == null) {
				worker = createDownloadWorker(info);
			}
			if (worker == null) {
				return false;
			}
			return worker.cancle();
		}

		@Override
		public void clearAllDownloadTask() throws RemoteException {
			DownloadWorkerSupervisor.clearAllDownloadTask();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map getDownloadInfos() throws RemoteException {
			return DownloadWorkerSupervisor.getDownloadInfos();
		}

		@Override
		public int getTaskCount() throws RemoteException {
			return DownloadWorkerSupervisor.getTaskCount();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map getDownloadInfo(String id) throws RemoteException {
			BaseDownloadInfo info = DownloadWorkerSupervisor.getDownloadInfo(id);
			return DownloadObjectUtil.object2Map(info);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean continueDownload(Map map) throws RemoteException {
			BaseDownloadInfo info = DownloadObjectUtil.map2DownloadInfo(map);
			if (info == null) {
				return false;
			}
			ADownloadWorker<? extends BaseDownloadInfo> worker = createDownloadWorker(info);
			if (worker == null)
				return false;
			return worker.start();
		}
	};

	/**
	 * 生成下载对象
	 *
	 * @param info
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ADownloadWorker<? extends BaseDownloadInfo> createDownloadWorker(BaseDownloadInfo info) {
		final String downloadWorkerClassName = info.getDownloadWorkerClassName();
		if (downloadWorkerClassName == null) {
			return new BaseDownloadWorker<BaseDownloadInfo>(DownloadService.this, info);
		}
		try {
			Class clazz = Class.forName(info.getDownloadWorkerClassName());
			Constructor constructor = clazz.getConstructor(new Class[] { Context.class, info.getClass() });
			ADownloadWorker<? extends BaseDownloadInfo> worker = (ADownloadWorker<? extends BaseDownloadInfo>) constructor.newInstance(new Object[] { DownloadService.this, info });
			return worker;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mDownloadServiceImpl;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		isServiceAlive = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isServiceAlive = false;
	}

}
