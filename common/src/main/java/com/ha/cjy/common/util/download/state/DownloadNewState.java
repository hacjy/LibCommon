package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 新下载状态类,取消下载后，状态也变为这个
 * 
 * @author LinXin
 */
public class DownloadNewState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_NEW;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNewClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNewDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadNewState() {

	}

	public DownloadNewState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadNewState> CREATOR = new Parcelable.Creator<DownloadNewState>() {

		@Override
		public DownloadNewState[] newArray(int size) {
			return new DownloadNewState[size];
		}

		@Override
		public DownloadNewState createFromParcel(Parcel source) {
			return new DownloadNewState(source);
		}
	};

}
