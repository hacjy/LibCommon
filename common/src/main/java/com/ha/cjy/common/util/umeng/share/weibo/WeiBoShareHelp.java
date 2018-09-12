package com.ha.cjy.common.util.umeng.share.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 微博分享
 */
public class WeiBoShareHelp {


    private static WeiBoShareHelp instance;
    WbShareHandler shareHandler;

    private WeiBoShareHelp() {
    }

    public static WeiBoShareHelp getInstance() {
        if (instance == null) {
            synchronized (WeiBoShareHelp.class) {
                if (instance == null) {
                    instance = new WeiBoShareHelp();
                }
            }
        }
        return instance;
    }

    public void doResultIntent(Intent intent, WbShareCallback wbShareCallback) {
        if (shareHandler == null) {
            return;
        }
        shareHandler.doResultIntent(intent, wbShareCallback);
    }

    private void init(Context context) {
        WbSdk.install(context, new AuthInfo(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }

    public void init(Context context,String appKey,String redirectUrl,String scope) {
        WbSdk.install(context, new AuthInfo(context, appKey, redirectUrl, scope));
    }

    public void login() {
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    public void share(final Activity activity, final String text, final String imagePath, final String title, final String actionUrl) {//,boolean hasText, boolean hasImage
//      Intent intent = activity.getIntent();
//      intent.putExtra("key_share_type", 2);
        shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                weiboMessage.textObject = getTextObj(text, title, actionUrl);
                if (!TextUtils.isEmpty(imagePath)) {
                    weiboMessage.imageObject = getImageObj(imagePath);
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shareHandler.shareMessage(weiboMessage, false);
                    }
                });
            }
        }).start();


    }


    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text, String title, String actionUrl) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        textObject.title = title;
        textObject.actionUrl = actionUrl;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(String url) {
        ImageObject imageObject = new ImageObject();
        imageObject.imagePath = url;
        Bitmap thumb = null;
        try {
//            thumb = BitmapFactory.decodeStream(new URL(url).openStream());
            thumb = getbitmap(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        imageObject.setImageObject(thumb);
        return imageObject;
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     * @throws
     */
    private Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

}
