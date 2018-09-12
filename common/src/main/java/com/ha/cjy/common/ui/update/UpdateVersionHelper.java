package com.ha.cjy.common.ui.update;

import android.content.Context;

import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.dialog.LoadingDialog;


/**
 * 检查更新的帮助类
 * Created by willy on 2016/12/16.
 */

public class UpdateVersionHelper {
    private Context context;

    public UpdateVersionHelper(Context context) {
        this.context = context;
    }

    /**
     * TODO 检查更新
     */
    public void checkUpdateVersion() {
        try {
//            HttpUtil.versionUpdate().subscribe(new UpdateVersionObserver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查更新，带dialog
     */
    public void checkUpdateVersionShowDialog() {
        LoadingDialog.showDialog(context);
        checkUpdateVersion();
    }

    /**
     * 是否需要更新
     *
     * @param checkedVersion 服务器传回的版本号
     * @return
     */
    public boolean isUpdateVersion(int checkedVersion) {
        int localVersionCode = 0;
        try {
            localVersionCode = context.getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean result = checkedVersion > localVersionCode;
        //设置新版本的标志

//        ConfigManager.getInstance().setHasNewVersion(result);
//        //发送事件给设置页面的p层，更新状态和提示
//        EventBus.getDefault().post(new Event.CheckUpdateResultEvent(result));

        return result;
    }

    /**
     * TODO 接口回调处理
     */
//    private class UpdateVersionObserver implements Observer<ResultInfo<UpdateVersionResultInfo>> {
//
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(ResultInfo<UpdateVersionResultInfo> resultInfoResultInfo) {
//
//            try {
//                LoadingDialog.dismissDialog();
//                if (context == null) {
//                    return;
//                }
//                Activity activity = (Activity) context;
//                if (activity.isFinishing() || resultInfoResultInfo.Data == null) {
//                    return;
//                }
//                UpdateVersionResultInfo info = resultInfoResultInfo.Data;
//                int versionCode = 0;
//                if (!StringUtil.isEmpty(info.InnerVersion)) {
//                    versionCode = Integer.valueOf(info.InnerVersion);
//                }
//                if (isUpdateVersion(versionCode)) {
//                    UpdateView.showDialog(context, info);
//                } else {
//                    ToastUtil.showToast(context, "当前已是最新版本");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            LoadingDialog.dismissDialog();
//            if (e instanceof ApiThrowable) {
//
//            } else {
//                ToastUtil.showToast(context, "网络异常，请检查网络设置");
//            }
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    }
}
