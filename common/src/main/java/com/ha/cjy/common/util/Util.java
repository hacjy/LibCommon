package com.ha.cjy.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Outline;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.ha.cjy.common.ui.base.BaseApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 通用工具类
 * Created by cjy on 2018/8/1.
 */

public class Util {

    public static int[] I420toARGB(byte[] yuv, int width, int height) {

        boolean invertHeight = false;
        if (height < 0) {
            height = -height;
            invertHeight = true;
        }

        boolean invertWidth = false;
        if (width < 0) {
            width = -width;
            invertWidth = true;
        }


        int iterations = width * height;
//if ((iterations*3)/2 > yuv.length){throw new IllegalArgumentException();}
        int[] rgb = new int[iterations];

        for (int i = 0; i < iterations; i++) {
    /*int y = yuv[i] & 0x000000ff;
    int u = yuv[iterations+(i/4)] & 0x000000ff;
    int v = yuv[iterations + iterations/4 + (i/4)] & 0x000000ff;*/
            int nearest = (i / width) / 2 * (width / 2) + (i % width) / 2;

            int y = yuv[i] & 0x000000ff;
            int u = yuv[iterations + nearest] & 0x000000ff;


            int v = yuv[iterations + iterations / 4 + nearest] & 0x000000ff;

            //int b = (int)(1.164*(y-16) + 2.018*(u-128));
            //int g = (int)(1.164*(y-16) - 0.813*(v-128) - 0.391*(u-128));
            //int r = (int)(1.164*(y-16) + 1.596*(v-128));

            //double Y = (y/255.0);
            //double Pr = (u/255.0-0.5);
            //double Pb = (v/255.0-0.5);



    /*int b = (int)(1.164*(y-16)+1.8556*(u-128));

    int g = (int)(1.164*(y-16) - (0.4681*(v-128) + 0.1872*(u-128)));
    int r = (int)(1.164*(y-16)+1.5748*(v-128));*/

            int b = (int) (y + 1.8556 * (u - 128));

            int g = (int) (y - (0.4681 * (v - 128) + 0.1872 * (u - 128)));

            int r = (int) (y + 1.5748 * (v - 128));


    /*double B = Y+1.8556*Pb;

    double G = Y - (0.4681*Pr + 0.1872*Pb);
    double R = Y+1.5748*Pr;*/

            //int b = (int)B*255;
            //int g = (int)G*255;
            //int r = (int)R*255;


            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }

    /*rgb[i]=(byte)b;
    rgb[i+1]=(byte)g;
    rgb[i+2]=(byte)r;*/
            int targetPosition = i;

            if (invertHeight) {
                targetPosition = ((height - 1) - targetPosition / width) * width + (targetPosition % width);
            }
            if (invertWidth) {
                targetPosition = (targetPosition / width) * width + (width - 1) - (targetPosition % width);
            }


            rgb[targetPosition] = (0xff000000) | (0x00ff0000 & r << 16) | (0x0000ff00 & g << 8) | (0x000000ff & b);
        }
        return rgb;

    }

    public static boolean isX86() {
        if (Build.CPU_ABI.equals("x86")) {
            return true;
        }
        return false;
    }

    /**
     * 是否在wifi环境下
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取网速
     *
     * @param lastTotalRxBytes 上一次数据
     * @param lastTimeStamp    上一次时间
     * @return
     */
    public static long[] getNetSpeed(long lastTotalRxBytes, long lastTimeStamp) {

        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        long[] result = new long[]{nowTotalRxBytes, nowTimeStamp, speed};
        return result;
    }

    private static long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(BaseApplication.getInstance().getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }


    /**
     * 转换时间格式
     *
     * @param currPosition
     * @param totalPosition
     * @param de
     * @return
     */
    public static long sbPosition(long currPosition, long totalPosition, long de) {
        if (currPosition < de) {
            return 0;
        }
        if (totalPosition - currPosition < de) {
            return totalPosition;
        }
        return currPosition;
    }


    /**
     * 转换时间格式
     *
     * @param time
     * @return
     */
    public static long timeIntToLong(long time) {
//        boolean is = time % 1000 == 0 ? true : false;
//        if (!is) {
//            time = time + 1000;
//        }
        if (time < 1000) {
            return 0;
        }
        return time;
    }

    /**
     * 转换时间格式
     *
     * @param time
     * @return
     */
    public static String timeFormatFor00(long time) {
        if (time < 10) {
            return "0" + time;
        }
        if (time < 60) {
            return "" + time;
        }
        return "00";
    }

    /**
     * 把时间转换成00:00
     *
     * @param position
     * @return
     */
    public static String timeFormat1(long position) {
        //1524550200 1 524 550
        position = timeIntToLong(position);
        long time = position / 1000;
        if (time < 60) {
            return "00:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m < 60) {
            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
        }
        return time + ":" + timeFormatFor00(s);
    }

    /**
     * 把时间转换成00:00
     *
     * @param position
     * @return
     */
    public static String timeFormat(long position) {
        long time = position / 1000;
        if (time < 60) {
            return "00:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m < 60) {
            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
        }
        return time + ":" + timeFormatFor00(s);
    }

    /**
     * 把时间转换成00:00
     *
     * @param time
     * @return
     */
    public static String timeFormat2(long time) {
        if (time < 60) {
            return "00:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m <= 60) {
            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
        }
        return time + ":" + timeFormatFor00(s);
    }

    public static String obtainSuffix(String url) {

        int lastIndex = url.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return url.substring(lastIndex);
    }

    /**
     * 拷贝assets文件下文件到指定路径
     *
     * @param context
     * @param assetDir 源文件/文件夹
     */
    public static void copyAssets(Context context, String assetDir, String targetDir) {
        if (TextUtils.isEmpty(assetDir) || TextUtils.isEmpty(targetDir)) {
            return;
        }
        String separator = File.separator;
        try {
            // 获取assets目录assetDir下一级所有文件以及文件夹
            String[] fileNames = context.getResources().getAssets().list(assetDir);
            // 如果是文件夹(目录),则继续递归遍历
            if (fileNames.length > 0) {
                File targetFile = new File(targetDir);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
//                if (!targetFile.exists() && !targetFile.mkdirs()) {
//                    return;
//                }
                for (String fileName : fileNames) {
                    copyAssets(context, assetDir + separator + fileName, targetDir + separator + fileName);
                }
            } else { // 文件,则执行拷贝
                copy(context, assetDir, targetDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Assets拷贝文件
     *
     * @param context
     * @param zipPath
     * @param targetPath
     */
    public static void copy(Context context, String zipPath, String targetPath) {
        if (TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(targetPath)) {
            return;
        }
        File dest = new File(targetPath);
        dest.getParentFile().mkdirs();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(context.getAssets().open(zipPath));
            out = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static int[] getViewSize(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static boolean cmd(String cmd) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(" su ");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }


    //用shell 身份执行命令
    public static String cmdShell(String cmd) {
        Process process = null;
        DataOutputStream out = null;
        InputStream in = null;
        String str = "";
        try {
            process = Runtime.getRuntime().exec("sh  ");
            out = new DataOutputStream(process.getOutputStream());
            out.writeBytes(cmd + "\n");
            out.writeBytes("exit\n");
            out.flush();
            in = process.getInputStream();
            int len = 0;
            byte[] bs = new byte[1024];

            while (-1 != (len = in.read(bs))) {
                str += new String(bs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
//                if(process!=null){
//                    process.destroy();
//                }
                return str;
            } catch (Exception e) {
            }
        }
        return str;
    }


    /**
     * 获取软件盘的高度
     *
     * @return
     */
    public static int getSupportSoftInputHeight(Activity mActivity) {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight(mActivity);
        }
        if (softInputHeight < 0) {
//            CLog.e("EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (softInputHeight > 0) {
//            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }


    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getSoftButtonsBarHeight(Activity mActivity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void removeList(List list, int count) {

        for (int i = 0; i < list.size(); i++) {
            list.remove(0);
            if (list.size() < 200) {
                return;
            }
        }
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;

    }


    /**
     * 获取网络类型
     */
    public static String getNetworkType() {
        String strNetworkType = "";
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return "";
        }
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();


                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

            }
        }


        return strNetworkType;
    }


    private static String createX(String key) {
        int length = key.length();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += "*";
        }
        return result;
    }

    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        return new String(c);
    }

    public static String transport(String inputStr) {
        char arr[] = inputStr.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') {
                arr[i] = '\u3000';
            } else if (arr[i] < '\177') {
                arr[i] = (char) (arr[i] + 65248);
            }

        }
        return new String(arr);
    }

    //获取布局的宽高
    public static int[] getWH(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    //把数字变成字符串带上单位
    public static String intToString(long num) {
        float y = (float) (1.0 * num / 100000000);
        if (y >= 1) {
            return getPercentString(y) + "亿";
        }
        y = (float) (1.0 * num / 10000);
        if (y >= 1) {
            return getPercentString(y) + "万";
        }
        return String.valueOf(num);
    }

    //把数字变成字符串带上单位 format .00
    public static String intToString2Point(long num) {
        float y = (float) ((1.0 * num) / 100000000);
        if (y >= 1) {
            return getPercentString2Point(y) + "亿";
        }
        y = (float) (1.0 * num / 10000);
//        CLog.e("wulianshu", "y=" + y);
        if (y >= 1) {
            return getPercentString2Point(y) + "万";
        }
        return String.valueOf(num);
    }

    //保留两位小数点
    public static String getPercentString2Point(float num) {
        String temp = num + "";
        String resultStr = "";
        if (temp.contains(".")) {
            int position = temp.indexOf(".");
//            CLog.e("wulianshu", "position:" + position);
            if ((position + 3) <= temp.length()) {
                resultStr = temp.substring(0, position + 3);
            } else {
                resultStr = temp.substring(0, position + 2);
            }
        } else {
            resultStr = temp;
        }
//        CLog.e("wulianshu", "resultStr:" + resultStr);
        if (resultStr.endsWith(".00")) {
            resultStr = resultStr.substring(0, resultStr.length() - 3);
        } else if (resultStr.endsWith(".0")) {
            resultStr = resultStr.substring(0, resultStr.length() - 2);
        } else if (resultStr.endsWith("0")) {
            resultStr = resultStr.substring(0, resultStr.length() - 1);
        }
        return resultStr;
    }

    public static String getPercentString(float num) {
        BigDecimal b = new BigDecimal(num);
        String resultStr = b.setScale(1, BigDecimal.ROUND_DOWN) + "";
        if (resultStr.endsWith("0")) {
            resultStr = resultStr.substring(0, resultStr.length() - 2);
        }
        return resultStr;
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void keyboardShow(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void keyboardHide(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //集合是否是空
    public static boolean isCollectionEmpty(Collection c) {
        if (c == null || c.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        if (size == 0) {
            return "0.0M";
        }

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    //坐等改成boolean
    public static int isRelation(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 0;
        }
        return 0;
    }

    /**
     * 获取内部版本号
     *
     * @param context
     * @return 内部版本号 versionCode
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取内部版本名字
     *
     * @param context
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


//    /**
//     * 解压
//     *
//     * @param sourceZip
//     * @param destDir
//     * @throws Exception
//     */
//    @SuppressWarnings("需要在子线程，主线程耗时较大")
//    public static void unzip(String sourceZip, String destDir) throws Exception {
//        Project project = new Project();
//        Expand expand = new Expand();
//        expand.setProject(project);
//        expand.setSrc(new File(sourceZip));
//        expand.setOverwrite(false);
//        expand.setDest(new File(destDir));
//        expand.setEncoding("gbk");
//        expand.execute();
//    }

    public static String readAssetsTxt(Context context, String fileName) {
        String text = "";
        InputStream is = null;
        try {

            is = context.getAssets().open(fileName);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);

            // Convert the buffer into a string.
            text = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    private static int currVolume = 0;

    /**
     * 打开扬声器
     *
     * @param context
     */
    public static void openSpeaker(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.ROUTE_SPEAKER);
            currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (!audioManager.isSpeakerphoneOn()) {
                //setSpeakerphoneOn() only work when audio mode set to MODE_IN_CALL.
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                        AudioManager.STREAM_VOICE_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭扬声器
     */
    public static void closeSpeaker(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照map的value获取key
     *
     * @param <T>
     * @param map
     * @param o
     * @return
     */
    public static <T> T getMapKeyByValue(HashMap<T, String> map, String o) {
        Iterator<T> it = map.keySet().iterator();
        while (it.hasNext()) {
            T keyString = it.next();
            if (map.get(keyString).equals(o))
                return keyString;
        }
        return null;
    }

    /**
     * 判断是否是横屏
     *
     * @param context true 横屏 false 非横屏
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    /**
     * 随机指定范围内N个不重复的数
     *
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static Object[] randomCommon(int max, int n) {
        HashSet<Integer> hashSet = new HashSet<>();
        while (hashSet.size() < n) {
            hashSet.add(new Random().nextInt(max));
        }
        return hashSet.toArray();
    }


    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static long getCurrentDayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }


    /**
     * @param milliseconds
     * @param abbreviate   是否显示 早上 中午、晚上 这些时间字段
     * @return
     */
    public static String getTimeShowString(long milliseconds, boolean abbreviate) {
        String dataString;
        String timeStringBy24;
        Date currentTime = new Date(milliseconds);
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return getTodayTimeBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString + " " + timeStringBy24;
        }
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }


    public static long getSecondsByMilliseconds(long milliseconds) {
        long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
                BigDecimal.ROUND_HALF_UP).intValue();
        return seconds;
    }

    public static void destroyWebView(final WebView wb) {
        if (wb == null)
            return;
        wb.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (wb.getParent() != null) {
                        try {
                            ((ViewGroup) wb.getParent()).removeView(wb);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    wb.removeAllViews();
                    wb.destroy();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    [0123456789]
    */
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = BaseApplication.getInstance().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 获取文件扩展名
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * 设置控件的轮廓：圆角矩形
     * @param view
     * @param radius
     */
    public static void setRoundRectShape(View view,final int radius){
        //利用5.0 design clipping功能设置按钮的形状为圆角矩形 有sdk版本要求，>= 21
        //获取outLine，我们需要使用ViewoutLineProvider
        ViewOutlineProvider viewOutlineProvider = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewOutlineProvider = new ViewOutlineProvider() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    //修改outLine的形状，这里是设置分别设置左上右下，以及Radius
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),radius);
                }
            };
            //将需要控件重写设置形状
            view.setOutlineProvider(viewOutlineProvider);
        }
    }
}
