package com.ha.cjy.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

import java.util.Map;


/**
 * 使用SharedPreferences存储数据 Created by Bining on 15/12/2.
 */
public class SharedPreUtil {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SharedPreUtil.class.getSimpleName(), 0);
    }

    private static SharedPreferences.Editor getEdit(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static Object jsonToClass(Context context, String fileName, String nodeName, Class clazz) {
        String json = SharepreferenceUtil.getSharePreString(context, fileName, nodeName, "");
        return json != null && !json.equals("") ? JsonUtil.parseData(json, clazz) : null;
    }

    public static Object jsonToClass(Context context, String fileName, String nodeName, TypeToken type) {
        String json = SharepreferenceUtil.getSharePreString(context, fileName, nodeName, "");
        return json != null && !json.equals("") ? JsonUtil.parseData(json, type) : null;
    }

    public static void saveClass(Context context, String fileName, String nodeName, Object object) {
        try {
            SharepreferenceUtil.putSharePreStr(context, fileName, nodeName, JsonUtil.objectToString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(Context context, String key, Object object) {
        SharedPreferences.Editor editor = getEdit(context);
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof String[]) {
            StringBuilder datas = new StringBuilder();
            String[] data = (String[]) object;

            for (int i = 0; i < data.length; ++i) {
                if (i != 0) {
                    datas.append(":");
                }

                datas.append(data[i]);
            }
            editor.putString(key, datas.toString());
        }

        editor.commit();
    }

    public static String getString(Context context, String key, String defaultObject) {
        return getSharedPreferences(context).getString(key, defaultObject);
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static int getInt(Context context,String key,int defaultValue){
        return getSharedPreferences(context).getInt(key,defaultValue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, true);
    }

    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key,defaultValue);
    }

    public static float getFloat(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, 0f);
    }

    public static float getFloat(Context context,String key,float defaultValue){
        return getSharedPreferences(context).getFloat(key,defaultValue);
    }

    public static long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, 0l);
    }

    public static String[] getStringArray(Context context, String key) {
        return getString(context, key).split(":");
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getEdit(context);
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEdit(context);
        editor.clear();
        editor.commit();
    }

    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        return getSharedPreferences(context).getAll();
    }
}
