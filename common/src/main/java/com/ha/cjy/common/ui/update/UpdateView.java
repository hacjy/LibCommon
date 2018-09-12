package com.ha.cjy.common.ui.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.ActivitysManager;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.base.BaseDialog;
import com.ha.cjy.common.ui.constants.Constants;
import com.ha.cjy.common.ui.update.download.DownloadModel;
import com.ha.cjy.common.ui.update.download.UpdateVersionDownloadCallBackImpl;
import com.ha.cjy.common.util.ScreenUtil;
import com.ha.cjy.common.util.app.PackageUtil;

import java.util.Arrays;

/**
 * 版本更新视图
 */
public class UpdateView extends BaseDialog implements View.OnClickListener {
    public static final int STATUS_PREPARE_DOWNLOAD = 0;
    public static final int STATUS_DOWNLOADING = 1;
    public static final int STATUS_FAILED_DOWNLOAD = 2;
    public static final int STATUS_INSTALL_APP = 3;
    private TextView updateVersionCloseBtn;
    private TextView updateVersionTitle;
    private RecyclerView updateVersionContentList;
    private TextView updateVersionHint;
    private ProgressBar updateProgress;
    private UpdateVersionButton updateVersionBtn;
    private View viewLine;


    private UpdateVersionResultInfo versionResultInfo;
    private Context context;

    private static UpdateView dialog;
    private UpdateVersionContentAdapter adapter;
    private BroadcastReceiver updateStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int statusFlag = intent.getIntExtra(Constants.UPDATE_VERSION_BROADCAST_FILTER_STATUS_FLAG, 0);
            int progressNum = intent.getIntExtra(Constants.UPDATE_VERSION_BROADCAST_FILTER_PROGRESS_NUM, 0);
            changeDownloadStatus(statusFlag, progressNum);
        }
    };

    public UpdateView(Context context) {
        super(context);
    }

    public UpdateView(Context context, int theme) {
        super(context, theme);
    }

    public UpdateView(Context context, UpdateVersionResultInfo updateResultInfo) {
        super(context, R.style.MyDialog);
        this.versionResultInfo = updateResultInfo;
        this.context = context;
    }

    public static UpdateView showDialog(Context context, UpdateVersionResultInfo updateResultInfo) {
        if (dialog == null) {
            dialog = new UpdateView(context, updateResultInfo);
        }
        dialog.show();
        return dialog;
    }

    public static boolean isDialogShowing() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    //銷毀dialog
    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.updateVersionCloseBtn) {
            if (versionResultInfo.UpdateType == 1) {
                ActivitysManager.getInstance().AppExit(getContext());
            } else {
                dismiss();
            }
        }
    }

    @Override
    public void initDataBeforeView() {
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);//间距为0
        win.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ScreenUtil.dip2px(BaseApplication.getInstance(), 282);
        lp.height = ScreenUtil.dip2px(BaseApplication.getInstance(), 376);
        win.setAttributes(lp);
    }


    @Override
    public void initView() {
        setContentView(R.layout.dialog_update_layout);
        updateVersionCloseBtn = findViewById(R.id.updateVersionCloseBtn);
        updateVersionTitle = findViewById(R.id.updateVersionTitle);
        updateVersionContentList = findViewById(R.id.updateVersionContentList);
        updateVersionHint = findViewById(R.id.updateVersionHint);
        updateProgress = findViewById(R.id.updateProgress);
        updateVersionBtn = findViewById(R.id.updateVersionBtn);
        viewLine = findViewById(R.id.view_line);

        //强制更新
        if (versionResultInfo.UpdateType == 1) {
            setCancelable(false);
            updateVersionCloseBtn.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        } else {
            //可选更新
            setCancelable(true);
            updateVersionCloseBtn.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        adapter = new UpdateVersionContentAdapter(getContext(), Arrays.asList(versionResultInfo.UpdateContent.split("\r\n")));
        updateVersionContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        updateVersionContentList.setAdapter(adapter);
        updateVersionBtn.setDownloadInfo(DownloadModel.createApkDownloadInfo(versionResultInfo.UpdateUrl, versionResultInfo.AppName,
                PackageUtil.getPackageName(getContext()), "", versionResultInfo.VersionNumber, new UpdateVersionDownloadCallBackImpl()));
        SpannableStringBuilder sb = new SpannableStringBuilder("有版本更新(v"+versionResultInfo.VersionNumber+")");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#fa326e"));
        sb.setSpan(colorSpan,5,sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        updateVersionTitle.setText(sb);
    }

    @Override
    public void initListener() {
        registerBroadcast();
        updateVersionBtn.setOnClickListener(null);
        updateVersionCloseBtn.setOnClickListener(this);
    }

    private void registerBroadcast() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.UPDATE_VERSION_BROADCAST_FILTER);
            getContext().registerReceiver(updateStatusReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unRegisterBroadcast() {
        try {
            getContext().unregisterReceiver(updateStatusReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialog = null;
        unRegisterBroadcast();
    }

    /**
     * 修改下载的状态，用来改变UI
     *
     * @param statusCode
     */
    private void changeDownloadStatus(int statusCode, int progressNum) {
        switch (statusCode) {
            case STATUS_PREPARE_DOWNLOAD:
                updateProgress.setVisibility(View.INVISIBLE);
                updateVersionHint.setVisibility(View.INVISIBLE);
                updateVersionBtn.setVisibility(View.VISIBLE);
                break;
            case STATUS_DOWNLOADING:
                updateProgress.setVisibility(View.VISIBLE);
                updateVersionHint.setVisibility(View.VISIBLE);
                updateVersionBtn.setVisibility(View.VISIBLE);
                updateProgress.setProgress(progressNum);
                updateVersionHint.setText(String.format(getContext().getString(R.string.update_version_progress_title), progressNum));
                break;
            case STATUS_FAILED_DOWNLOAD:
                updateProgress.setVisibility(View.INVISIBLE);
                updateVersionHint.setVisibility(View.VISIBLE);
                updateVersionBtn.setVisibility(View.VISIBLE);
                updateVersionHint.setText(getContext().getString(R.string.update_version_failed));
                break;
            case STATUS_INSTALL_APP:
                updateProgress.setVisibility(View.INVISIBLE);
                updateVersionHint.setVisibility(View.INVISIBLE);
                updateVersionBtn.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        ButterKnife.unbind(this);
    }
}


