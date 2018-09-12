package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 连接中状态
 * 
 * @author LinXin
 */
public class DownloadConnectingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_CONNECTING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadConnectingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadConnectDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadConnectingState() {

	}

	public DownloadConnectingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadConnectingState> CREATOR = new Parcelable.Creator<DownloadConnectingState>() {

		@Override
		public DownloadConnectingState[] newArray(int size) {
			return new DownloadConnectingState[size];
		}

		@Override
		public DownloadConnectingState createFromParcel(Parcel source) {
			return new DownloadConnectingState(source);
		}
	};

}
