package com.ha.cjy.common.ui.update.download;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.constants.FilePathCfg;
import com.ha.cjy.common.util.FileUtil;
import com.ha.cjy.common.util.MD5Util;
import com.ha.cjy.common.util.NetworkUtils;
import com.ha.cjy.common.util.StringUtil;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.common.util.app.PackageUtil;
import com.ha.cjy.common.util.download.BaseDownloadOperate;
import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.BaseDownloadWorker;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

import java.io.File;

/**
 * Created by linbinghuang on 2016/5/16.
 * 下载业务
 */
public class DownloadModel {
    public static final int GET_UNKNOWN_APP_SOURCES = 20000;
    public static final String UNKNOWN_APP_SOURCES_ACTION = "action";

    private static Context mContext;
    private static String mFilePath;
    private static boolean mIsRootInstall;

    public DownloadModel() {
    }

    /**
     * apk下载
     *
     * @param context       上下文
     * @param mDownloadInfo 下载的对象
     */
    public static void downApk(Context context, ApkDownloadInfo mDownloadInfo) {
        mContext = context;
        if (mDownloadInfo.getState().getState().getIntValue() == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
            return;
        }
        if (!isApkDownload(mDownloadInfo, false)) {
            return;
        }
        addNewDownloadTask(context, mDownloadInfo);
    }

    /**
     * aapk 下载前判断是否符合下载条件
     *
     * @param mInfo
     * @param isRootInstall
     * @return
     */
    public static boolean isApkDownload(ApkDownloadInfo mInfo, boolean isRootInstall) {
        //安装包是否还在
        if (FileUtil.isFileExits(mInfo.getSaveDir(), mInfo.getSaveName())) {
            String path = mInfo.getSaveDir() + mInfo.getSaveName();
            mFilePath = path;
            mIsRootInstall = isRootInstall;
            //判断是否是Android8.0系统，因为8.0系统的允许未知应用安装默认是关闭的，我们需要提示用户去设置
            checkIsAndroidO(0);
            return false;
        }
        return true;
    }

    /**
     * 安装应用
     * @param path
     * @param isRootInstall
     */
    private static void installApk(String path,boolean isRootInstall){
        if (isRootInstall && PackageUtil.isRoot()) {
            PackageUtil.installPackage(path);
        } else {
            PackageUtil.installApplicationNormal(BaseApplication.getInstance(), path);
        }
    }

    /**
     * 判断是否是8.0, 8.0需要处理未知应用来源权限问题,否则直接安装
     * action 1 不再提示了，重新启动才会再次提示
     */
    public static void checkIsAndroidO(final int action) {
        try {
            Activity currnetActivity = scanForActivity(mContext);
           final Activity activity = currnetActivity;
            if (Build.VERSION.SDK_INT >= 26) {
                //getPackageManager().canRequestPackageInstalls()判断是否可以安装未知来源的应用
                boolean canInstall = BaseApplication.getInstance().getPackageManager().canRequestPackageInstalls();
                if (canInstall) {
                    installApk(mFilePath,mIsRootInstall);
                } else {
                    if (activity != null) {
                        //请求安装未知应用来源的权限
                        showDialog(activity,action);
                    }
                }
            }else{
                installApk(mFilePath,mIsRootInstall);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 跳转提示对话框
     * @param activity
     * @param action
     */
    private static void showDialog(final Activity activity, final int action){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setTitle("提示");
        dialog.setMessage("安装应用需要打开未知来源权限，请去设置中开启应用权限，以允许安装来自此来源的应用");
        dialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //跳转到设置页面
                toSettingActivity(activity,action);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 获取ContextThemeWrapper的Activity
     * @param cont
     * @return
     */
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());
        return null;
    }

    /**
     * 跳转到设置允许安装未知来源页面
     * @param activity
     * @param action
     */
    private static void toSettingActivity(Activity activity,int action){
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        Bundle bundle = new Bundle();
        bundle.putInt(UNKNOWN_APP_SOURCES_ACTION,action);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
    }

    /**
     * 创建下载
     *
     * @param url         下载地址
     * @param name        名称
     * @param packageName 包名
     * @param iconUrl     apk图标
     * @param gameId      api id
     * @param callBack    下载回调
     * @return
     */
    public static ApkDownloadInfo createApkDownloadInfo(String url, String name, String packageName, String iconUrl, String gameId, BaseDownloadWorker.DownloadCallBack callBack) {
        //先判断文件是否存在，存在就已经证明下载完毕
        ApkDownloadInfo info = getDownloadEd(url, name, packageName, iconUrl, gameId, callBack);
        if (info != null) {
            return info;
        }
        //是否在下载流程
        info = getDownloading(gameId, callBack);
        if (info != null) {
            return info;
        }
        //没有在下载流程中
        info = createApkDownload(url, name, packageName, iconUrl, gameId, null, callBack);
        return info;
    }

    /**
     * 创建下载对象
     *
     * @param url         下载地址
     * @param name        名称
     * @param packageName 包名
     * @param iconUrl     apk图标
     * @param gameId      api id
     * @param state       下载状态
     * @param callBack    下载回调
     * @return
     */
    private static ApkDownloadInfo createApkDownload(String url, String name, String packageName, String iconUrl, String gameId, IDownloadState state,
                                                     BaseDownloadWorker.DownloadCallBack callBack) {
        ApkDownloadInfo info = new ApkDownloadInfo();
        info.setIdentification(url);
        info.setUrl(url);
        info.setSaveDir(FilePathCfg.FILE_DIR);
        info.setSaveName(MD5Util.MD5(url) + ".apk");
        info.packageName = packageName;
        info.appName = name;
        info.appId = gameId;
        if (state != null) {
            info.setState(state);
        }
        info.setCallBack(callBack);
        return info;
    }

    /**
     * Apk下载完成，文件名称是根据id md5加密之后的
     *
     * @param url         下载地址
     * @param name        名称
     * @param packageName 包名
     * @param iconUrl     apk图标
     * @param gameId      api id
     * @param callBack    下载回调
     * @return
     */
    private static ApkDownloadInfo getDownloadEd(String url, String name, String packageName, String iconUrl, String gameId, BaseDownloadWorker.DownloadCallBack callBack) {
        File file = new File(FilePathCfg.FILE_DIR + MD5Util.MD5(gameId) + ".apk");
        if (file.exists()) {
            return createApkDownload(url, name, packageName, iconUrl, gameId, BaseDownloadStateFactory.getDownloadedState(), callBack);
        }
        return null;
    }

    /**
     * apk下载完毕
     *
     * @param info
     */
    public static void downloadCompleteApk(ApkDownloadInfo info) {
        isApkDownload(info, false);
    }

    /**
     * 是否在下载流程
     *
     * @param id
     * @return
     */
    private static ApkDownloadInfo getDownloading(String id, BaseDownloadWorker.DownloadCallBack callBack) {
        //判定是否是在下载流程中
        ApkDownloadInfo info = ApkDownloadDao.getInstance().query(id);
        BaseDownloadInfo downloadInfo = BaseDownloadOperate.getDownloadInfo(BaseApplication.getInstance(), id);
        if (info != null) {
            if (downloadInfo == null) {
                //修正数据库
                int state = info.getState().getState().getIntValue();
                int downloadingState = BaseDownloadStateFactory.State.DOWNLOADING.getIntValue();
                int wiatState = BaseDownloadStateFactory.State.DOWNLOAD_WAIT.getIntValue();
                int pauseingState = BaseDownloadStateFactory.State.DOWNLOAD_PAUSEING.getIntValue();
                int connectingState = BaseDownloadStateFactory.State.DOWNLOAD_CONNECTING.getIntValue();
                if (state == downloadingState || state == wiatState || state == pauseingState || state == connectingState) {
                    info.setState(BaseDownloadStateFactory.getDownloadPausedState());
                    ApkDownloadDao.getInstance().insertOrUpdate(info);
                }
                //如果是取消过程中进程被杀掉 就是删除掉了
                int cancelingState = BaseDownloadStateFactory.State.DOWNLOAD_CANCELING.getIntValue();
                if (state == cancelingState) {
                    info.setState(BaseDownloadStateFactory.getDownloadNewState());
                    ApkDownloadDao.getInstance().delete(info);
                }
            }
            info.setCallBack(callBack);
            return info;
        }
        return null;
    }

    /**
     * 添加下载任务时判断时候有网络
     */
    public static void addNewDownloadTask(Context mContext, BaseDownloadInfo mDownloadInfo) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            BaseDownloadOperate.addNewDownloadTask(mContext, mDownloadInfo);
        } else {
            ToastUtil.showToast(BaseApplication.getInstance(), "无网络连接");
        }
    }


    /**
     * 下载一般文件
     *
     * @param context       上下文
     * @param mDownloadInfo 下载的对象
     */
    public static void downFile(Context context, ApkDownloadInfo mDownloadInfo) {
        if (mDownloadInfo.getState().getState().getIntValue() == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
            return;
        }
        if (!isDownload(mDownloadInfo)) {
            return;
        }
        addNewDownloadTask(context, mDownloadInfo);
    }
    /**
     * 下载一般文件
     *
     * @param context       上下文
     * @param mDownloadInfo 下载的对象
     */
    public static void downFileNoPrompt(Context context, ApkDownloadInfo mDownloadInfo) {
        if (mDownloadInfo.getState().getState().getIntValue() == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
            return;
        }
//        if (!isDownloadNoPrompt(mDownloadInfo)) {
//            return;
//        }
        addNewDownloadTask(context, mDownloadInfo);
    }
    /**
     * 是否可以下载
     *
     * @param info 下载对象
     * @return
     */
    private static boolean isDownload(BaseDownloadInfo info) {
        if (FileUtil.isFileExits(info.getSaveDir(), info.getSaveName())) {
            ToastUtil.showToast(BaseApplication.getInstance(), "文件已经存在" + info.getSaveDir() + info.getSaveName());
            return false;
        }
        return true;
    }
    /**
     * 是否可以下载
     *
     * @param info 下载对象
     * @return
     */
    private static boolean isDownloadNoPrompt(BaseDownloadInfo info) {
        if (FileUtil.isFileExits(info.getSaveDir(), info.getSaveName())) {
            return false;
        }
        return true;
    }
    /**
     * 构建一般文件的下载
     *
     * @return
     */
    public static ApkDownloadInfo createFileDownload(String url, String id, String name,
                                                     String packageName, String saveDir,
                                                     IDownloadState state, BaseDownloadWorker.DownloadCallBack callBack) {
        String suffix = StringUtil.getFileSuffix(url);
        if (StringUtil.isEmpty(suffix)) {
            suffix = "";
        }
        String identification = MD5Util.MD5(url);
        ApkDownloadInfo download = new ApkDownloadInfo();
        //由于历史原因现在appid才是ntification的id
        download.appId = id;
        download.appName = name;
        download.packageName = packageName;
        download.setIdentification(identification);
        download.setUrl(url);
        download.setSaveDir(saveDir);
        download.setSaveName(identification + "." + suffix);
        download.setCallBack(callBack);
        if (state != null) {
            download.setState(state);
        }
        return download;
    }


    /**
     * 构建一般文件的下载
     * 可以自定义存储名字
     *
     * @param url
     * @param id
     * @param name
     * @param packageName
     * @param saveDir
     * @param saveName
     * @param state
     * @param callBack
     * @return
     */
    public static ApkDownloadInfo createFileDownload(String url, String id, String name, String packageName,
                                                     String saveDir, String saveName, IDownloadState state,
                                                     BaseDownloadWorker.DownloadCallBack callBack) {
        String suffix = StringUtil.getFileSuffix(url);
        if (StringUtil.isEmpty(suffix)) {
            suffix = "";
        }
        // TODO: 2017/7/3 测试用临时处理
        String identification = MD5Util.MD5(url + id);
        ApkDownloadInfo download = new ApkDownloadInfo();
        //由于历史原因现在appid才是ntification的id
        download.appId = id;
        download.appName = name;
        download.packageName = packageName;
        download.setIdentification(identification);
        download.setUrl(url);
        download.setSaveDir(saveDir);
        download.setSaveName(saveName + "." + suffix);
        download.setCallBack(callBack);
        if (state != null) {
            download.setState(state);
        }
        return download;
    }
    /**
     * 构建一般文件的下载
     * 可以自定义存储名字
     *
     * @param url
     * @param id
     * @param name
     * @param packageName
     * @param saveDir
     * @param saveName
     * @param state
     * @param callBack
     * @return
     */
    public static ApkDownloadInfo createFileDownload(String url, String id, String name, String packageName,
                                                     String saveDir, String saveName, String versionName,IDownloadState state,
                                                     BaseDownloadWorker.DownloadCallBack callBack) {
        String suffix = StringUtil.getFileSuffix(url);
        if (StringUtil.isEmpty(suffix)) {
            suffix = "";
        }
        // TODO: 2017/7/3 测试用临时处理
        String identification = MD5Util.MD5(url + id);
        ApkDownloadInfo download = new ApkDownloadInfo();
        //由于历史原因现在appid才是ntification的id
        download.appId = id;
        download.appName = name;
        download.packageName = packageName;
        download.versionName = versionName;
        download.setIdentification(identification);
        download.setUrl(url);
        download.setSaveDir(saveDir);
        download.setSaveName(saveName + "." + suffix);
        download.setCallBack(callBack);
        if (state != null) {
            download.setState(state);
        }
        return download;
    }

//    /**
//     * 构建zip的下载
//     *
//     * @param info
//     * @param isIncrementPacket 是否是下载增量包 true 是增量包 false 不是增量包
//     * @return
//     */
//    public static FaceFileDownloadInfo createZipFileDownload(ZipFaceInfo info, boolean isIncrementPacket) {
//        String url = isIncrementPacket ? info.ZZipPath : info.QZipPath;
//        FaceFileDownloadInfo download = new FaceFileDownloadInfo();
//        download.setIdentification(url);
//        download.setUrl(url);
//        download.setSaveDir(FilePathCfg.FACE_ZIP_DIR);
//        download.setSaveName(MD5Util.MD5(url) + ".zip");
//        download.info = info;
//        download.setCallBack(new ZIPFileDownloadCallBackImpl());
//        return download;
//    }


//    /**
//     * 下载zip文件的方法
//     *
//     * @param context
//     * @param downloadInfo
//     */
//    public static void downloadZipFile(Context context, FaceFileDownloadInfo downloadInfo) {
//        if (BaseDownloadOperate.getDownloadInfo(context, downloadInfo.getUrl()) != null) {
//            return;
//        }
//        if (downloadInfo.getState().getState().getIntValue() == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
//            return;
//        }
//        if (!isDownloadZipFile(downloadInfo)) {
//            //TODO 下载成功，要进行一些处理
//            return;
//        }
//        addNewDownloadTask(context, downloadInfo);
//
//    }

    /**
     * 下载图片文件的方法
     *
     * @param context
     * @param downloadInfo
     */
    public static void downloadImgFile(Context context, BaseDownloadInfo downloadInfo) {
        if (BaseDownloadOperate.getDownloadInfo(context, downloadInfo.getUrl()) != null) {
            return;
        }
        if (downloadInfo.getState().getState().getIntValue() == BaseDownloadStateFactory.State.DOWNLOADING.getIntValue()) {
            return;
        }
        if (isDownloadImgFileComplete(downloadInfo)) {
            //TODO 下载成功，要进行一些处理
            ToastUtil.showToast(context, "保存至" + downloadInfo.getSaveDir() + downloadInfo.getSaveName());
            return;
        }
        addNewDownloadTask(context, downloadInfo);

    }

//    /**
//     * 文件是否可以下载
//     *
//     * @param downloadInfo
//     * @return
//     */
//    private static boolean isDownloadZipFile(FaceFileDownloadInfo downloadInfo) {
//        String filePath = FilePathCfg.FACE_ZIP_DIR + MD5Util.MD5(downloadInfo.getUrl());
//        String zipPath = downloadInfo.getSaveDir() + downloadInfo.getSaveName();
//        boolean isFileExits = FileUtil.isFileExits(filePath);
//        if (isFileExits) {
//            return false;
//        }
//        String tempPath = zipPath + ".temp";
//        FileUtil.delFile(tempPath);
//        if (FileUtil.isFileExits(zipPath)) {
//            try {
//                downloadCompleteZipFile(downloadInfo);
//            } catch (Exception e) {
//                //出错了直接删除
//                FileUtil.delFile(zipPath);
//                e.printStackTrace();
//                return true;
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * 图片是否下载完成
     *
     * @param downloadInfo
     * @return
     */
    private static boolean isDownloadImgFileComplete(BaseDownloadInfo downloadInfo) {
        String imgPath = downloadInfo.getSaveDir() + downloadInfo.getSaveName();
        boolean isFileExits = FileUtil.isFileExits(imgPath);
        if (isFileExits) {
            return true;
        }
        return false;
    }

//    /**
//     * 开始下载zip
//     *
//     * @param downloadInfo
//     */
//    public static void downloadCompleteZipFile(FaceFileDownloadInfo downloadInfo) {
//        new UnzipFaceThread(downloadInfo).start();
//    }

//    /**
//     * 表情包下载完毕 处理
//     *
//     * @param faceFileDownloadInfo
//     */
//    private static void handleFaceFileComplete(FaceFileDownloadInfo faceFileDownloadInfo) {
//        //发送广播
//        Intent intent = new Intent();
//        intent.setAction(IntentCfg.BR_FACE_UNZIP_COMPLETE);
//        //这里用QVersion，增量包陆续下载的消息体也是直接构造一个新的表情包信息类，版本号直接用的QVersion
//        intent.putExtra(IntentCfg.BR_FACE_UNZIP_COMPLETE_VERSION, faceFileDownloadInfo.info.QVersion);
//        BaseApplication.getInstance().sendBroadcast(intent);
//
//    }

//    /**
//     * 解压
//     *
//     * @param sourceZip 解压包路径
//     * @param destDir   资源存放路径
//     * @throws Exception
//     */
//    private static void unzip(String sourceZip, String destDir) throws Exception {
//        Project project = new Project();
//        Expand expand = new Expand();
//        expand.setProject(project);
//        expand.setSrc(new File(sourceZip));
//        //设置下可以覆盖...
//        expand.setOverwrite(true);
////        expand.setOverwrite(false);
//        expand.setDest(new File(destDir));
//        expand.setEncoding("gbk");
//        expand.execute();
//    }

//    static class UnzipFaceThread extends Thread {
//        private FaceFileDownloadInfo downloadInfo;
//
//        public UnzipFaceThread(FaceFileDownloadInfo downloadInfo) {
//            super();
//            this.downloadInfo = downloadInfo;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            String zipPah = downloadInfo.getSaveDir() + downloadInfo.getSaveName();
//            try {
//                CLog.e("face", "解压");
//                unzip(zipPah, FilePathCfg.FACE_ZIP_DIR);
//                //解压什么的都完成了，直接更新本地的版本信息
//                CLog.e("face", "解压完成");
//                handleFaceFileComplete(downloadInfo);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    /**
//     * 递归解压
//     *
//     * @param zipPah
//     * @throws Exception
//     */
//    public static void zipRecursion(String zipPah) {
//
//        try {
//            String destDir  = getDestDir(zipPah);
//            if(StringUtil.isEmpty(destDir)){
//                return;
//            }
//            FileUtil.createDir(destDir);
//            unzip(zipPah, destDir);
//            File file = new File(destDir);
//            String[] lists = file.list();
//            if (lists == null || lists.length == 0) {
//                return;
//            }
//            for (String item : lists) {
//                if (item.endsWith("zip")) {
//                    zipRecursion(item);
//                }
//            }
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//    }
//    /**
//     * 递归解压
//     *
//     * @param zipPah
//     * @throws Exception
//     */
//    public static void zip(String zipPah) {
//
//        try {
//            String destDir  = getDestDir(zipPah);
//            if(StringUtil.isEmpty(destDir)){
//                return;
//            }
//            FileUtil.createDir(destDir);
//            unzip(zipPah, destDir);
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//    }

//    /**
//     *  解药和当前
//     * @param zipPah
//     * @return
//     */
//    public static String   getDestDir(String zipPah){
//
//        int index = zipPah.lastIndexOf(".zip");
//
//        return zipPah.substring(0, index);
//    }

}
