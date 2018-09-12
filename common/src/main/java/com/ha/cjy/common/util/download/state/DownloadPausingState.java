package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 暂停中状态类
 * 
 * @author LinXin
 */
public class DownloadPausingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_PAUSEING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausingDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadPausingState() {

	}

	public DownloadPausingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadPausingState> CREATOR = new Parcelable.Creator<DownloadPausingState>() {

		@Override
		public DownloadPausingState[] newArray(int size) {
			return new DownloadPausingState[size];
		}

		@Override
		public DownloadPausingState createFromParcel(Parcel source) {
			return new DownloadPausingState(source);
		}
	};

}
