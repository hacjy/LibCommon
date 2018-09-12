package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 已暂停状态类
 * 
 * @author LinXin
 */
public class DownloadPausedState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_PAUSED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausedClick();

	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausedDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadPausedState() {

	}

	public DownloadPausedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadPausedState> CREATOR = new Parcelable.Creator<DownloadPausedState>() {

		@Override
		public DownloadPausedState[] newArray(int size) {
			return new DownloadPausedState[size];
		}

		@Override
		public DownloadPausedState createFromParcel(Parcel source) {
			return new DownloadPausedState(source);
		}
	};

}
