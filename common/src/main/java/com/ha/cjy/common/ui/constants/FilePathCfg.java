package com.ha.cjy.common.ui.constants;

import android.os.Environment;

import java.io.File;

/**
 * 所有文件路径都会在这里
 */
public class FilePathCfg {
    //assets的头部路径
    public final static String ASSETS_HEAD_PATH = "file:///android_asset/";
    //总路径
    public final static String FILE_DIR = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "myapp" + File.separatorChar;
    //配置文件路径
    public final static String FILE_CONFIG_DIR = FILE_DIR + "config" + File.separatorChar;
    // 存放表情包的文件夹--临时
    public final static String FACE_ZIP_DIR = FILE_DIR + "face" + File.separator;
    //临时存储路径
    public final static String FILE_TEMP_DIR = FILE_DIR + "temp" + File.separatorChar;
    //图片资源路径
    public final static String FILE_IMG_DIR = FILE_DIR + "img" + File.separatorChar;
    //用户头像
    public final static String USER_HEAD_IMAGE_FILE_NAME = "userhead";
}
