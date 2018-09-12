package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 下载结束状态类
 * 
 * @author LinXin
 */
public class DownloadedState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOADED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadedClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadedDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadedState() {

	}

	public DownloadedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadedState> CREATOR = new Parcelable.Creator<DownloadedState>() {

		@Override
		public DownloadedState[] newArray(int size) {
			return new DownloadedState[size];
		}

		@Override
		public DownloadedState createFromParcel(Parcel source) {
			return new DownloadedState(source);
		}
	};

}
