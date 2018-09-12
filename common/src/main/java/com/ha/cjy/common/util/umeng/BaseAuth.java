package com.ha.cjy.common.util.umeng;

import android.content.Context;

import com.ha.cjy.common.ui.constants.ELoginStaute;
import com.ha.cjy.common.ui.constants.ELoginType;
import com.ha.cjy.common.ui.constants.EventCfg;
import com.ha.cjy.common.ui.dialog.LoadingDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 第三方认证接口
 */
public abstract class BaseAuth implements IAuth,UMAuthListener{
    protected Context mContext;//上下问

    public BaseAuth(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void auth() {
        LoadingDialog.showDialog(mContext);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        LoadingDialog.dismissDialog();
        EventBus.getDefault().post(new EventCfg.LoginResultEvent(ELoginStaute.E_FAILD, ELoginType.E_NONE));
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        LoadingDialog.dismissDialog();
        EventBus.getDefault().post(new EventCfg.LoginResultEvent(ELoginStaute.E_CANCEL, ELoginType.E_NONE));
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        LoadingDialog.dismissDialog();
    }
}
