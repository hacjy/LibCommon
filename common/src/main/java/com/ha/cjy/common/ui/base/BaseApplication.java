package com.ha.cjy.common.ui.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.ha.cjy.common.ui.ActivitysManager;
import com.ha.cjy.common.util.app.AppException;
import com.ha.cjy.common.util.download.kernel.DownloadServiceConnection;
import com.ha.cjy.common.util.push.PushManager;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by cjy on 2018/7/19.
 */

public class BaseApplication extends Application {
    protected static BaseApplication baseApplication;
    //用户id
    protected static long userId;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        init();
    }

    private void init(){
        //崩溃日志写入本地
        AppException.getInstance().init(this);
        //ZXing初始化
        ZXingLibrary.initDisplayOpinion(this);
        //忽略https的证书校验
        handleSSLHandshake();
        //推送初始化
        PushManager.getInstance().initPushService(this);
        //解决android 7.0系统解决拍照的问题android.os.FileUriExposedException:file:///storage/emulated/0/xxx.jpg
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //下载初始化
        DownloadServiceConnection connection = new DownloadServiceConnection(this);
        connection.bindDownloadService(null);
    }


    /**
     * 忽略https的证书校验
     * 避免Glide加载https图片报错：
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */
    public void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    public void exitApp(){
        try {
            ActivitysManager.getInstance().AppExit(BaseApplication.getInstance());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public long getUserId(){
        return userId;
    }
}
