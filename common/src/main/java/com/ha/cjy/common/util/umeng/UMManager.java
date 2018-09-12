package com.ha.cjy.common.util.umeng;

import android.app.Activity;
import android.content.Intent;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.constants.ELogin;
import com.ha.cjy.common.ui.constants.EShare;
import com.ha.cjy.common.ui.dialog.LoadingDialog;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.common.util.app.PackageUtil;
import com.ha.cjy.common.util.umeng.login.UMQQLoginHelp;
import com.ha.cjy.common.util.umeng.login.UMSinaLoginHelp;
import com.ha.cjy.common.util.umeng.login.UMWXLoginHelp;
import com.ha.cjy.common.util.umeng.share.UmShareHelp;
import com.ha.cjy.common.util.umeng.share.weibo.WeiBoShareHelp;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

/**
 * 友盟管理类
 */
public class UMManager {
    private static volatile UMManager manager;
    private UmConfig umConfig;
    private static Map<EShare, SHARE_MEDIA> mShareMap;

    static {
        mShareMap = new HashMap();
        mShareMap.put(EShare.E_PYQ, SHARE_MEDIA.WEIXIN_CIRCLE);
        mShareMap.put(EShare.E_QQ, SHARE_MEDIA.QQ);
        mShareMap.put(EShare.E_SINA, SHARE_MEDIA.SINA);
        mShareMap.put(EShare.E_WEIXI, SHARE_MEDIA.WEIXIN);
    }

    public static UMManager getInstance() {
        UMManager sManager = manager;
        if (manager == null) {
            synchronized (UMManager.class) {
                sManager = manager;
                if (sManager == null) {
                    sManager = new UMManager();
                    manager = sManager;
                }
            }
        }
        return sManager;
    }


    /**
     * 友盟路径统计
     */
    public void onResume(Activity activity) {
        MobclickAgent.onResume(activity);
    }

    /**
     * 友盟路径统计
     */
    public void onPause(Activity activity) {
        MobclickAgent.onPause(activity);
    }

    public void init(UmConfig umConfig) {
        this.umConfig = umConfig;
    }

    /**
     * activity回调 注意：QQ与新浪登录都需要调用回调，而且如果是在Fragment调用的话，需要在其Activity中调用该方法
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 友盟分享（图片）
     */
    public void localShareImg(Activity activity, String imaUrl, String title, String content, EShare eShare) {
        try {
            LoadingDialog.showDialog(activity);
            UmShareHelp.umShare(activity, title, imaUrl, "", content, mShareMap.get(eShare));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 友盟分享（通用）
     */
    public void localShare(Activity activity, String imaUrl, String title, String shareContent, String url, EShare eShare) {
        String titleS = title;
        String shareContentS = shareContent;
        if (eShare == EShare.E_SINA) {
            WeiBoShareHelp.getInstance().share(activity, shareContentS, imaUrl, titleS, url);
            return;
        } else {
            if (isShare(eShare)) {
                UmShareHelp.umShare(activity, titleS, imaUrl, url, shareContentS, mShareMap.get(eShare));
            }
        }
    }

    /**
     * 友盟分享（通用 带类型）
     */
    public void localShare(Activity activity, String imaUrl, String title, String shareContent, String url, EShare eShare, UMShareListener shareListener) {
        String titleS = title;
        String shareContentS = shareContent;
        if (eShare == EShare.E_SINA) {
            WeiBoShareHelp.getInstance().share(activity, shareContentS, imaUrl, titleS, url);
            return;
        } else {
            if (isShare(eShare)) {
                UmShareHelp.umShare(activity, titleS, imaUrl, url, shareContentS, mShareMap.get(eShare), shareListener);
            }
        }
    }

    /**
     * 是否可以分享
     */
    public boolean isShare(EShare eShare) {
        if (eShare == EShare.E_WEIXI || eShare == EShare.E_PYQ) {
            if (!PackageUtil.isPackageInstalled(BaseApplication.getInstance(), "com.tencent.mm")) {
                LoadingDialog.dismissDialog();
                ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.share_wx_uninstall));
                return false;
            }
        }
        if (eShare == EShare.E_QQ) {
            if (!PackageUtil.isPackageInstalled(BaseApplication.getInstance(), "com.tencent.mobileqq")) {
                LoadingDialog.dismissDialog();
                ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.share_qq_uninstall));
                return false;
            }
        }
        return true;
    }

    /**
     * ========第三方登录==========
     */
    /**
     * 友盟第三方登录
     */
    public void login(Activity activity, ELogin eLogin) {
        if (eLogin == ELogin.E_WEIXI) {
            loginWx(activity);
            return;
        }
        if (eLogin == ELogin.E_SINA) {
            loginSina(activity);
            return;
        }
        if (eLogin == ELogin.E_QQ) {
            loginQq(activity);
            return;
        }
    }

    /**
     * 新浪登录
     */
    public void loginSina(Activity activity) {
        IAuth help = new UMSinaLoginHelp(activity);
        help.auth();
    }

    /**
     * QQ登录
     */
    public void loginQq(Activity activity) {
        IAuth help = new UMQQLoginHelp(activity);
        help.auth();
    }

    /**
     * 微信登录
     */
    public void loginWx(Activity activity) {
        IAuth help = new UMWXLoginHelp(activity);
        help.auth();
    }


}
