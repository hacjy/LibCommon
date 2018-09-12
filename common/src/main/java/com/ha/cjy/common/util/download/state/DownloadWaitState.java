package com.ha.cjy.common.util.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.BaseDownloadStateFactory.State;
import com.ha.cjy.common.util.download.intf.IDownloadClickHelper;
import com.ha.cjy.common.util.download.intf.IDownloadDisplayHelper;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;


/**
 * 等待下载状态类
 *
 * @author LinXin
 */
public class DownloadWaitState implements IDownloadState {

	@Override
	public State getState() {
		return BaseDownloadStateFactory.State.DOWNLOAD_WAIT;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadWaitClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadWaitDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadWaitState() {

	}

	public DownloadWaitState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadWaitState> CREATOR = new Parcelable.Creator<DownloadWaitState>() {

		@Override
		public DownloadWaitState[] newArray(int size) {
			return new DownloadWaitState[size];
		}

		@Override
		public DownloadWaitState createFromParcel(Parcel source) {
			return new DownloadWaitState(source);
		}
	};

}
