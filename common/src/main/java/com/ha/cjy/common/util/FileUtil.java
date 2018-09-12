package com.ha.cjy.common.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 文件工具
 * Created by cjy on 2018/7/14.
 */

public class FileUtil {
    private static final String TAG = "FileUtil";

    public static void writeFile(InputStream in, String filePath) {
        byte[] buffer = new byte[1000];
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));

            int size;
            while((size = in.read(buffer)) > -1) {
                bufferedOutputStream.write(buffer, 0, size);
            }

            bufferedOutputStream.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static boolean writeFile(File file, List<byte[]> datas) {
        BufferedOutputStream bos = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            Iterator var3 = datas.iterator();

            while(var3.hasNext()) {
                byte[] data = (byte[])var3.next();
                bos.write(data);
            }

            boolean var15 = true;
            return var15;
        } catch (Exception var13) {
            var13.printStackTrace();
            Log.e("FileUtil", var13.getMessage());
        } finally {
            if(bos != null) {
                try {
                    bos.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                    Log.e("FileUtil", var12.getMessage());
                }
            }

        }

        return false;
    }

    public static boolean writeFile(File file, byte[]... datas) {
        RandomAccessFile rfile = null;

        try {
            rfile = new RandomAccessFile(file, "rw");
            rfile.seek(file.length());
            byte[][] var3 = datas;
            int var4 = datas.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                byte[] data = var3[var5];
                rfile.write(data);
            }

            boolean var17 = true;
            return var17;
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            if(rfile != null) {
                try {
                    rfile.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

        }

        return false;
    }

    public static boolean writeFile(String path, byte[]... datas) {
        RandomAccessFile rfile = null;
        File file = null;

        try {
            if(!isFileExits(path)) {
                file = createFile(path);
            }

            rfile = new RandomAccessFile(file, "rw");
            rfile.seek(file.length());
            byte[][] var4 = datas;
            int var5 = datas.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte[] data = var4[var6];
                rfile.write(data);
            }

            boolean var18 = true;
            return var18;
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            if(rfile != null) {
                try {
                    rfile.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return false;
    }

    public static void writeFile(String path, String content, boolean append) {
        try {
            File f = new File(path);
            if(!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            if(!f.exists()) {
                f.createNewFile();
                f = new File(path);
            }

            FileWriter fw = new FileWriter(f, append);
            if(content != null && !"".equals(content)) {
                fw.write(content);
                fw.flush();
            }

            fw.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void createDir(String dir) {
        File f = new File(dir);
        if(!f.exists()) {
            f.mkdirs();
        }

    }

    public static File createFile(String filePath) {
        File f = new File(filePath);
        if(!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        if(!f.exists()) {
            try {
                f.createNewFile();
                f = new File(filePath);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

        return f;
    }

    public static void delFile(String path) {
        try {
            File f = new File(path);
            if(f.exists()) {
                f.delete();
            }
        } catch (Exception var2) {
            ;
        }

    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath.toString();
            File f = new File(filePath);
            f.delete();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if(!file.exists()) {
            return flag;
        } else if(!file.isDirectory()) {
            return flag;
        } else {
            String[] tempList = file.list();
            File temp = null;

            for(int i = 0; i < tempList.length; ++i) {
                if(path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }

                if(temp.isFile()) {
                    temp.delete();
                }

                if(temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);
                    delFolder(path + "/" + tempList[i]);
                    flag = true;
                }
            }

            return flag;
        }
    }

    public static boolean copyFile(String srcFile, String destFile) {
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];

            int c;
            while((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }

            in.close();
            out.close();
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static void copyFolder(String oldPath, String newPath) {
        (new File(newPath)).mkdirs();
        File a = new File(oldPath);
        String[] file = a.list();
        if(null != file) {
            File temp = null;

            for(int i = 0; i < file.length; ++i) {
                try {
                    if(oldPath.endsWith(File.separator)) {
                        temp = new File(oldPath + file[i]);
                    } else {
                        temp = new File(oldPath + File.separator + file[i]);
                    }

                    if(temp.isFile()) {
                        FileInputStream input = new FileInputStream(temp);
                        FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                        byte[] b = new byte[5120];

                        int len;
                        while((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }

                        output.flush();
                        output.close();
                        input.close();
                    }

                    if(temp.isDirectory()) {
                        copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                }
            }

        }
    }

    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public static boolean renameFile(String resFilePath, String newFilePath) {
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    public static File[] getFilesFromDir(String dirPath, FileFilter fileFilter) {
        File dir = new File(dirPath);
        return dir.isDirectory()?(fileFilter != null?dir.listFiles(fileFilter):dir.listFiles()):null;
    }

    public static List<String> getExistsFileNames(String dir, FileFilter fileFilter, boolean hasSuffix) {
        File file = new File(dir);
        File[] files = file.listFiles(fileFilter);
        List<String> fileNameList = new ArrayList();
        if(null != files) {
            File[] var7 = files;
            int var8 = files.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                File tmpFile = var7[var9];
                String tmppath = tmpFile.getAbsolutePath();
                String fileName = getFileName(tmppath, hasSuffix);
                fileNameList.add(fileName);
            }
        }

        return fileNameList;
    }

    public static List<String> getAllExistsFileNames(String dir, boolean hasSuffix, String[] suffix) {
        File file = new File(dir);
        File[] files = file.listFiles();
        List<String> fileNameList = new ArrayList();
        if(null != files) {
            File[] var7 = files;
            int var8 = files.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                File tmpFile = var7[var9];
                if(tmpFile.isDirectory()) {
                    fileNameList.addAll(getAllExistsFileNames(tmpFile.getPath(), hasSuffix, suffix));
                } else {
                    String tmp = tmpFile.getName().toLowerCase();
                    if(suffix != null) {
                        String[] var12 = suffix;
                        int var13 = suffix.length;

                        for(int var14 = 0; var14 < var13; ++var14) {
                            String s = var12[var14];
                            if(tmp.endsWith(s)) {
                                fileNameList.add(tmpFile.getAbsolutePath());
                            }
                        }
                    } else {
                        fileNameList.add(tmpFile.getAbsolutePath());
                    }
                }
            }
        }

        return fileNameList;
    }

    public static String getFileName(String path, boolean hasSuffix) {
        return null != path && -1 != path.lastIndexOf("/") && -1 != path.lastIndexOf(".")?(!hasSuffix?path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")):path.substring(path.lastIndexOf("/") + 1)):null;
    }

    public static String getPath(String path) {
        File file = new File(path);

        try {
            if(!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }

            return file.getAbsolutePath();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static boolean isFileExits(String dir, String fileName) {
        fileName = fileName == null?"":fileName;
        dir = dir == null?"":dir;
        int index = dir.lastIndexOf("/");
        String filePath;
        if(index == dir.length() - 1) {
            filePath = dir + fileName;
        } else {
            filePath = dir + "/" + fileName;
        }

        File file = new File(filePath);
        return file.exists();
    }

    public static boolean isFileExits(String filePath) {
        try {
            File file = new File(filePath);
            if(file.exists()) {
                return true;
            }
        } catch (Exception var2) {
            ;
        }

        return false;
    }

    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp) {
        try {
            File dir = new File(dirPath);
            if(!dir.exists()) {
                boolean flag = dir.mkdirs();
                if(!flag) {
                    return false;
                }
            }

            if(fileName == null || fileName.trim().length() == 0) {
                fileName = System.currentTimeMillis() + ".jpg";
            }

            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp, Bitmap.CompressFormat format) {
        try {
            File dir = new File(dirPath);
            if(!dir.exists()) {
                boolean flag = dir.mkdirs();
                if(!flag) {
                    return false;
                }
            }

            format = format == null? Bitmap.CompressFormat.JPEG:format;
            if(fileName == null || fileName.trim().length() == 0) {
                fileName = System.currentTimeMillis() + "";
                if(format.equals(Bitmap.CompressFormat.PNG)) {
                    fileName = fileName + ".png";
                } else {
                    fileName = fileName + ".jpg";
                }
            }

            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(format, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    public static long getFileAllSize(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return 0L;
        } else if(!file.isDirectory()) {
            long size = file.length();
            return size;
        } else {
            File[] children = file.listFiles();
            long size = 0L;
            File[] var5 = children;
            int var6 = children.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                File f = var5[var7];
                size += getFileAllSize(f.getPath());
            }

            return size;
        }
    }

    public static String readFileContent(String path) {
        StringBuffer sb = new StringBuffer();
        if(!isFileExits(path)) {
            return sb.toString();
        } else {
            FileInputStream ins = null;

            try {
                ins = new FileInputStream(new File(path));
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
                String line = null;

                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            } finally {
                if(ins != null) {
                    try {
                        ins.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }
                }

            }

            return sb.toString();
        }
    }

    public static boolean copyAssetsFile(Context context, String srcFileName, String targetDir, String targetFileName) {
        AssetManager asm = null;
        FileOutputStream fos = null;
        DataInputStream dis = null;

        try {
            asm = context.getAssets();
            dis = new DataInputStream(asm.open(srcFileName));
            createDir(targetDir);
            File targetFile = new File(targetDir, targetFileName);
            if(targetFile.exists()) {
                targetFile.delete();
            }

            fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            boolean var9 = false;

            int len;
            while((len = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }

            fos.flush();
            boolean var10 = true;
            return var10;
        } catch (Exception var20) {
            Log.w("FileUtil", "copy assets file failed:" + var20.toString());
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }

                if(dis != null) {
                    dis.close();
                }
            } catch (Exception var19) {
                ;
            }

        }

        return false;
    }

    public static String readAssetsFile(Context context, String srcFileName) {
        AssetManager asm = null;
        FileOutputStream fos = null;
        DataInputStream dis = null;

        try {
            asm = context.getAssets();
            dis = new DataInputStream(asm.open(srcFileName));
            StringBuffer sb = new StringBuffer();
            byte[] buffer = new byte[1024];
            boolean var7 = false;

            while(dis.read(buffer) != -1) {
                sb.append(buffer);
            }

            ((FileOutputStream)fos).flush();
            String var8 = sb.toString();
            return var8;
        } catch (Exception var18) {
            Log.w("FileUtil", "copy assets file failed:" + var18.toString());
        } finally {
            try {
                if(fos != null) {
                    ((FileOutputStream)fos).close();
                }

                if(dis != null) {
                    dis.close();
                }
            } catch (Exception var17) {
                ;
            }

        }

        return null;
    }

    public static int upZipFile(String zipPath, String folderPath) throws ZipException, IOException {
        File zipFile = new File(zipPath);
        if(!zipFile.exists()) {
            return 1;
        } else {
            File file = new File(folderPath);
            if(file.exists()) {
                file.delete();
            }

            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];

            while(true) {
                while(zList.hasMoreElements()) {
                    ze = (ZipEntry)zList.nextElement();
                    if(ze.isDirectory()) {
                        Log.d("upZipFile", "ze.getName() = " + ze.getName());
                        String dirstr = folderPath + ze.getName();
                        dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                        Log.d("upZipFile", "str = " + dirstr);
                        File f = new File(dirstr);
                        f.mkdirs();
                    } else {
                        Log.d("upZipFile", "ze.getName() = " + ze.getName());
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                        InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                        boolean var10 = false;

                        int readLen;
                        while((readLen = is.read(buf, 0, 1024)) != -1) {
                            os.write(buf, 0, readLen);
                        }

                        is.close();
                        os.close();
                    }
                }

                zfile.close();
                return 0;
            }
        }
    }

    public static File getRealFileName(String baseDir, String absFileName) {
        File ret = new File(baseDir);
        if(!ret.exists()) {
            ret.mkdirs();
        }

        String suffixStr = absFileName.substring(absFileName.lastIndexOf("."), absFileName.length());
        absFileName = "a" + suffixStr;
        ret = new File(ret, absFileName);
        return ret;
    }
}
