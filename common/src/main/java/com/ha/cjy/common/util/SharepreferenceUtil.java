package com.ha.cjy.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by cjy on 2018/7/23.
 */

public class SharepreferenceUtil {
    public SharepreferenceUtil() {
    }

    public static <T> void saveArray(Context context, String fileName, String nodeName, List<T> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(list);
            String listBase64 = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0));
            objectOutputStream.close();
            putSharePreStr(context, fileName, nodeName, listBase64);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static <T> List<T> jsonToList(String listString) {
        try {
            byte[] mBytes = Base64.decode(listString.getBytes(), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List<T> list = (List)objectInputStream.readObject();
            objectInputStream.close();
            return list;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static SharedPreferences getSharedPreferences(Context context, String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, 0);
        return sp;
    }

    public static void putSharePreInt(Context context, String spName, String nodeName, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context, spName).edit();
        editor.putInt(nodeName, value).commit();
    }

    public static void putSharePreBoolean(Context context, String spName, String nodeName, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context, spName).edit();
        editor.putBoolean(nodeName, value).commit();
    }

    public static void putSharePreStr(Context context, String spName, String nodeName, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context, spName).edit();
        editor.putString(nodeName, value).commit();
    }

    public static String getSharePreString(Context context, String spName, String nodeName, String value) {
        return getSharedPreferences(context, spName).getString(nodeName, value);
    }

    public static boolean getSharePreBoolean(Context context, String spName, String nodeName, boolean defult) {
        return getSharedPreferences(context, spName).getBoolean(nodeName, defult);
    }

    public static int getSharePreInt(Context context, String spName, String nodeName, int defult) {
        return getSharedPreferences(context, spName).getInt(nodeName, defult);
    }
}
