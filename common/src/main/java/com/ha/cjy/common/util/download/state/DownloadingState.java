package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;


/**
 * 下载中状态类
 * 
 * @author LinXin
 */
public class DownloadingState implements IDownloadState {

	@Override
	public BaseDownloadStateFactory.State getState() {
		return BaseDownloadStateFactory.State.DOWNLOADING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadingDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadingState() {

	}

	public DownloadingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadingState> CREATOR = new Parcelable.Creator<DownloadingState>() {

		@Override
		public DownloadingState[] newArray(int size) {
			return new DownloadingState[size];
		}

		@Override
		public DownloadingState createFromParcel(Parcel source) {
			return new DownloadingState(source);
		}
	};

}
