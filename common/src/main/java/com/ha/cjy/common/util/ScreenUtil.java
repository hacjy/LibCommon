package com.ha.cjy.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕宽高、分辨率工具
 * Created by cjy on 2018/7/17.
 */

public class ScreenUtil {
    public ScreenUtil() {
    }

    /**
     * 获取横屏的宽
     * @param context
     * @return
     */
    public static int getLandScreenWidth(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        boolean isLand = isOrientationLandscape(context);
        return isLand?metrics.heightPixels:metrics.widthPixels;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.widthPixels;
    }

    /**
     * 获取横屏高度
     * @param context
     * @return
     */
    public static int getLandScreenHeight(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        boolean isLand = isOrientationLandscape(context);
        return isLand?metrics.widthPixels:metrics.heightPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 是否是横屏
     * @param context
     * @return
     */
    public static boolean isOrientationLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    /**
     * dip转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float currentDensity = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * currentDensity + 0.5F);
    }

    /**
     * px转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    /**
     * 获取屏幕密度
     * @param ctx
     * @return
     */
    public static float getScreenDensity(Context ctx) {
        return ctx.getResources().getDisplayMetrics().density;
    }

    public static int[] getScreenResolutionXY(Context ctx) {
        int[] resolutionXY = new int[2];
        if(resolutionXY[0] != 0) {
            return resolutionXY;
        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)ctx.getApplicationContext().getSystemService("window");
            windowManager.getDefaultDisplay().getMetrics(metrics);
            resolutionXY[0] = metrics.widthPixels;
            resolutionXY[1] = metrics.heightPixels;
            return resolutionXY;
        }
    }

    /**
     * 获取屏幕的分辨率
     * @param ctx
     * @return
     */
    public static String getScreenResolution(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)ctx.getApplicationContext().getSystemService("window");
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String resolution = width + "x" + height;
        return resolution;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return statusHeight;
    }
}
