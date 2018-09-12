package com.ha.cjy.common.util.umeng.share;

import android.app.Activity;

import com.ha.cjy.common.ui.constants.EShare;
import com.ha.cjy.common.ui.constants.EventCfg;
import com.ha.cjy.common.ui.dialog.LoadingDialog;
import com.ha.cjy.common.util.StringUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * 友盟分享
 */
public class UmShareHelp {

    /**
     * 分享
     * @param activity
     * @param title
     * @param imaUrl
     * @param url
     * @param content
     * @param shareMedia
     */
    public static void umShare(Activity activity, String title, String imaUrl, String url, String content, SHARE_MEDIA shareMedia) {

        if (StringUtil.isEmpty(imaUrl)) {
            share(activity, title, null, url, content, shareMedia);
            return;
        }
        UMImage umImage = new UMImage(activity, imaUrl);
        share(activity, title, umImage, url, content, shareMedia);

    }

    /**
     * 自己处理回调的分享
     * @param activity
     * @param title
     * @param imgUrl
     * @param url
     * @param content
     * @param shareMedia
     * @param shareListener
     */
    public static void umShare(Activity activity, String title, String imgUrl, String url, String content, SHARE_MEDIA shareMedia, UMShareListener shareListener) {

        if (StringUtil.isEmpty(imgUrl)) {
            share(activity, title, null, url, content, shareMedia);
            return;
        }
        UMImage umImage = new UMImage(activity, imgUrl);
        share(activity, title, umImage, url, content, shareMedia,shareListener);

    }

    /**
     * 分享文件
     * @param activity
     * @param title
     * @param file
     * @param url
     * @param content
     * @param shareMedia
     */
    public static void shareForFile(Activity activity, String title, File file, String url, String content, SHARE_MEDIA shareMedia) {

        ShareAction shareAction = new ShareAction(activity);
        shareAction.withTitle(title);
        shareAction.withText(content);
        if (file != null) {
            UMImage umImage = new UMImage(activity, file);
            shareAction.withMedia(umImage);
        }
        shareAction.setPlatform(shareMedia).setCallback(umShareListener).share();
    }

    /**
     * 分享
     * @param activity
     * @param title
     * @param umImage
     * @param url
     * @param content
     * @param shareMedia
     */
    //分享
    private static void share(Activity activity, String title, UMImage umImage, String url, String content, SHARE_MEDIA shareMedia) {
        ShareAction shareAction = new ShareAction(activity);
        shareAction.withTitle(title);
        shareAction.withTargetUrl(url);
        shareAction.withText(content);
        if (umImage != null) {
            shareAction.withMedia(umImage);
        }
        shareAction.setPlatform(shareMedia).setCallback(umShareListener).share();
    }
    /**
     * 分享 自己实现回调
     * @param activity
     * @param title
     * @param umImage
     * @param url
     * @param content
     * @param shareMedia
     * @param shareListener
     */
    //分享
    private static void share(Activity activity, String title, UMImage umImage, String url, String content, SHARE_MEDIA shareMedia, UMShareListener shareListener) {
        ShareAction shareAction = new ShareAction(activity);
        shareAction.withTitle(title);
        shareAction.withTargetUrl(url);
        shareAction.withText(content);
        if (umImage != null) {
            shareAction.withMedia(umImage);
        }
        shareAction.setPlatform(shareMedia).setCallback(shareListener).share();
    }

    //默认的分享回调
    private static UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA platform) {
            EShare eShare = EShare.E_NON;
            if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                eShare = EShare.E_PYQ;
            }
            LoadingDialog.dismissDialog();
            EventBus.getDefault().post(new EventCfg.ShareResultEvent(EventCfg.ShareResultEvent.success, eShare));
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LoadingDialog.dismissDialog();
            EventBus.getDefault().post(new EventCfg.ShareResultEvent(EventCfg.ShareResultEvent.error));
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            LoadingDialog.dismissDialog();
            EventBus.getDefault().post(new EventCfg.ShareResultEvent(EventCfg.ShareResultEvent.cancel));
        }
    };

}
