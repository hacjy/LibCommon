package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 未知状态类
 * 
 * @author LinXin
 */
public class DownloadUnKnownState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_UNKNOWN;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNoneClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNoneDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadUnKnownState() {

	}

	public DownloadUnKnownState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadUnKnownState> CREATOR = new Parcelable.Creator<DownloadUnKnownState>() {

		@Override
		public DownloadUnKnownState[] newArray(int size) {
			return new DownloadUnKnownState[size];
		}

		@Override
		public DownloadUnKnownState createFromParcel(Parcel source) {
			return new DownloadUnKnownState(source);
		}
	};

}
