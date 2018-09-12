package com.ha.cjy.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.ui.constants.FilePathCfg;
import com.ha.cjy.common.util.permission.PermissionCallback;
import com.ha.cjy.common.util.permission.PermissionsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * 拍照或者选择图片的帮助类
 */

public class SelectPhotoUtil {

    private SelPopCallBack mCallBack;
    //原始图片文件名
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    //裁剪后的图片文件名
    private static final String PHOTO_FILE_CROP_NAME = "temp_photo_crop.jpg";
    //拍照返回码
    private final int RESULT_CAMERA = 1001;
    //裁剪后的返回码
    private final int RESULT_CAMERA_CROP = 1002;
    //选择相册的返回码
    private final int RESULT_GALLERY = 1003;
    //请求权限的回调的code
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;


    private Uri imageUri;
    private Uri imageCropUri;
    private String photoPath;

    private Activity activity;

    public SelectPhotoUtil(Activity activity, SelPopCallBack mCallBack) {
        this.activity = activity;
        this.mCallBack = mCallBack;
        initPictureUriPath();
    }




    /**
     * 初始化一些图片的存储路径
     */
    private void initPictureUriPath() {
        File file = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        File file1 = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_CROP_NAME);
        imageCropUri = Uri.fromFile(file1);
    }


    /*
 * 判断sdcard是否被挂载
 */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 用户对申请权限之后的处理---同意或者拒绝
     *
     * @param requestPermissionCode
     * @param grantResults
     */
    public void doNext(int requestPermissionCode, int[] grantResults) {
        if (requestPermissionCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.mine_edit_photo_permission_sdcard_warn));
            }
        }
    }

    /**
     * activity的回调result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case RESULT_CAMERA:
                // 从相机返回的数据
                if (hasSdcard()) {
                    selPicForCut(imageUri);
                } else {
                    ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.mine_edit_photo_sdcard_not_find_warn));
                }
                break;
            case RESULT_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    selPicForCut(uri);
                }
                break;
            case RESULT_CAMERA_CROP:
                // 从剪切图片返回的数据
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(BaseApplication.getInstance().getContentResolver().openInputStream(imageCropUri));
                    if (bitmap != null) {
                        //获取用户头像二进制流的base64位字符串
                        photoPath = BitmapUtil.convertIconToString(bitmap);
                        FileUtil.delFile(FilePathCfg.FILE_TEMP_DIR);
                        FileUtil.createDir(FilePathCfg.FILE_TEMP_DIR);
                        String tempPath = FilePathCfg.FILE_TEMP_DIR + UUID.randomUUID().toString() + ".png";
                        BitmapUtil.saveBitmap2file(bitmap, tempPath);
                        mCallBack.callBitmapData(bitmap,photoPath);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 剪切图片
     * @param uri
     */
    private void selPicForCut(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 1000);
        intent.putExtra("outputY", 1000);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-msgData", false);
        intent.putExtra("scale", true);//去除黑边
        intent.putExtra("scaleUpIfNeeded", true);//去除黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri);
        activity.startActivityForResult(intent, RESULT_CAMERA_CROP);
    }

    /**
     *从相册选择图片
     */
    public void selPicForCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, RESULT_CAMERA);
    }

    /**
     * 图库获取图片
     */
    public void selPicForPhoto() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, RESULT_GALLERY);

    }

    /**
     * 申请读写SD卡的权限，6.0以上的系统需要处理，如果没有的话进行下一步的处理
     */
    public void requestPermission() {
        new PermissionsUtil().requestPermission(activity, 1, new PermissionCallback() {
            @Override
            public void grantPermission(String[] s) {
                if (mCallBack != null){
                    mCallBack.callPermissionResult(true);
                }
            }

            @Override
            public void denyPermission(String[] s) {
                ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.mine_edit_photo_permission_sdcard_warn));
                if (mCallBack != null){
                    mCallBack.callPermissionResult(false);
                }
            }

            @Override
            public void denyNotRemindPermission(String[] s) {
                if (mCallBack != null){
                    mCallBack.callPermissionResult(false);
                }
//                ToastUtil.showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.mine_edit_photo_permission_sdcard_warn));
            }
        }, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public interface SelPopCallBack {
        void callBitmapData(Bitmap bitmap, String data);
        void callPermissionResult(boolean isGranted);
    }
}
