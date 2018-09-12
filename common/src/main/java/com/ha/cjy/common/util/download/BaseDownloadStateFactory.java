package com.ha.cjy.common.util.download;

import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.state.DownloadCancelingState;
import com.ha.cjy.common.util.download.state.DownloadConnectingState;
import com.ha.cjy.common.util.download.state.DownloadFailedState;
import com.ha.cjy.common.util.download.state.DownloadNewState;
import com.ha.cjy.common.util.download.state.DownloadPausedState;
import com.ha.cjy.common.util.download.state.DownloadPausingState;
import com.ha.cjy.common.util.download.state.DownloadUnKnownState;
import com.ha.cjy.common.util.download.state.DownloadWaitState;
import com.ha.cjy.common.util.download.state.DownloadedState;
import com.ha.cjy.common.util.download.state.DownloadingState;

/**
 * 下载状态工厂
 * 
 * @author LinXin
 */
public class BaseDownloadStateFactory {

	public enum State {
		/** 未知 */
		DOWNLOAD_UNKNOWN(-1),
		/** 新任务 */
		DOWNLOAD_NEW(1),
		/** 等待下载 */
		DOWNLOAD_WAIT(2),
		/** 下载中 */
		DOWNLOADING(3),
		/** 下载成功 */
		DOWNLOADED(4),
		/** 暂停 */
		DOWNLOAD_PAUSED(5),
		/** 下载失败 */
		DOWNLOAD_FAILED(6),
		/** 正在取消 */
		DOWNLOAD_CANCELING(7),
		/** 暂停中 */
		DOWNLOAD_PAUSEING(8),
		/** 连接中 */
		DOWNLOAD_CONNECTING(9);

		/**
		 * Maps an int to a specific state. This is needed when saving state.
		 * 
		 * @param stateInt
		 *        - int to map a State to
		 * @return State that stateInt maps to
		 */
		public static State mapIntToValue(final int stateInt) {
			for (State value : State.values()) {
				if (stateInt == value.getIntValue()) {
					return value;
				}
			}

			// If not, return default
			return DOWNLOAD_UNKNOWN;
		}

		private int mIntValue;

		State(int intValue) {
			mIntValue = intValue;
		}

		public int getIntValue() {
			return mIntValue;
		}

		@Override
		public String toString() {
			return String.valueOf(mIntValue);
		}
	}

	private static DownloadingState downloadingState;

	private static DownloadNewState downloadNewState;

	private static DownloadPausedState downloadPausedState;

	private static DownloadPausingState downloadPausingState;

	private static DownloadUnKnownState downloadUnKnownState;

	private static DownloadWaitState downloadWaitState;

	private static DownloadCancelingState downloadCancelingState;

	private static DownloadConnectingState downloadConnectingState;

	private static DownloadedState downloadedState;

	private static DownloadFailedState downloadFailedState;

	/**
	 * 根据状态返回不同的状态
	 * 
	 * @param state
	 * @return
	 */
	public static IDownloadState getDownloadState(State state) {
		switch (state) {
		case DOWNLOADED:
			return getDownloadedState();
		case DOWNLOADING:
			return getDownloadingState();
		case DOWNLOAD_CANCELING:
			return getDownloadCancelingState();
		case DOWNLOAD_CONNECTING:
			return getDownloadConnectingState();
		case DOWNLOAD_FAILED:
			return getDownloadFailedState();
		case DOWNLOAD_NEW:
			return getDownloadNewState();
		case DOWNLOAD_UNKNOWN:
			return getDownloadUnKnownState();
		case DOWNLOAD_PAUSED:
			return getDownloadPausedState();
		case DOWNLOAD_PAUSEING:
			return getDownloadPausingState();
		case DOWNLOAD_WAIT:
			return getDownloadWaitState();
		default:
			return getDownloadNewState();
		}
	}

	/**
	 * 获取下载中状态
	 * 
	 * @return
	 */
	public static DownloadingState getDownloadingState() {
		if (downloadingState == null)
			downloadingState = new DownloadingState();
		return downloadingState;
	}

	/**
	 * 获取新建下载状态
	 * 
	 * @return
	 */
	public static DownloadNewState getDownloadNewState() {
		if (downloadNewState == null)
			downloadNewState = new DownloadNewState();
		return downloadNewState;
	}

	/**
	 * 获取暂停中状态
	 * 
	 * @return
	 */
	public static DownloadPausedState getDownloadPausedState() {
		if (downloadPausedState == null)
			downloadPausedState = new DownloadPausedState();
		return downloadPausedState;
	}

	/**
	 * 获取暂停中状态
	 * 
	 * @return
	 */
	public static DownloadPausingState getDownloadPausingState() {
		if (downloadPausingState == null)
			downloadPausingState = new DownloadPausingState();
		return downloadPausingState;
	}

	/**
	 * 获取未知错误状态
	 * 
	 * @return
	 */
	public static DownloadUnKnownState getDownloadUnKnownState() {
		if (downloadUnKnownState == null)
			downloadUnKnownState = new DownloadUnKnownState();
		return downloadUnKnownState;
	}

	/**
	 * 获取下载等待状态
	 * 
	 * @return
	 */
	public static DownloadWaitState getDownloadWaitState() {
		if (downloadWaitState == null)
			downloadWaitState = new DownloadWaitState();
		return downloadWaitState;
	}

	/**
	 * 获取取消中状态
	 * 
	 * @return
	 */
	public static DownloadCancelingState getDownloadCancelingState() {
		if (downloadCancelingState == null)
			downloadCancelingState = new DownloadCancelingState();
		return downloadCancelingState;
	}

	/**
	 * 获取连接中状态
	 * 
	 * @return
	 */
	public static DownloadConnectingState getDownloadConnectingState() {
		if (downloadConnectingState == null)
			downloadConnectingState = new DownloadConnectingState();
		return downloadConnectingState;
	}

	/**
	 * 获取已下载状态
	 * 
	 * @return
	 */
	public static DownloadedState getDownloadedState() {
		if (downloadedState == null)
			downloadedState = new DownloadedState();
		return downloadedState;
	}

	/**
	 * 获取下载失败状态
	 * 
	 * @return
	 */
	public static DownloadFailedState getDownloadFailedState() {
		if (downloadFailedState == null)
			downloadFailedState = new DownloadFailedState();
		return downloadFailedState;
	}

}
