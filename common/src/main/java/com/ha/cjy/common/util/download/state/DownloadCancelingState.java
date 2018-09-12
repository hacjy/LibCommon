package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/***
 * 取消下载中状态类
 * 
 * @author LinXin
 */
public class DownloadCancelingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_CANCELING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadCancelingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadCancelingDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadCancelingState() {

	}

	public DownloadCancelingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadCancelingState> CREATOR = new Parcelable.Creator<DownloadCancelingState>() {

		@Override
		public DownloadCancelingState[] newArray(int size) {
			return new DownloadCancelingState[size];
		}

		@Override
		public DownloadCancelingState createFromParcel(Parcel source) {
			return new DownloadCancelingState(source);
		}
	};

}
