package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

/**
 * 下载失败状态类
 * 
 * @author LinXin
 */
public class DownloadFailedState implements IDownloadState {

	@Override
	public BaseDownloadStateFactory.State getState() {
		return BaseDownloadStateFactory.State.DOWNLOAD_FAILED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadFailedClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadFailedDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadFailedState() {

	}

	public DownloadFailedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadFailedState> CREATOR = new Parcelable.Creator<DownloadFailedState>() {

		@Override
		public DownloadFailedState[] newArray(int size) {
			return new DownloadFailedState[size];
		}

		@Override
		public DownloadFailedState createFromParcel(Parcel source) {
			return new DownloadFailedState(source);
		}
	};

}
