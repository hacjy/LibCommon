package com.ha.cjy.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Json工具类
 * Created by cjy on 2018/7/17.
 */

public class JsonUtil {

    public JsonUtil() {
    }

    /**
     * json字符串转化为对象
     * @param json
     * @param clazz
     * @return
     */
    public static Object parseData(String json, Class clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转化为对象
     * @param json
     * @param type
     * @return
     */
    public static Object parseData(String json, TypeToken type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转化为对象
     * @param json
     * @param type
     * @return
     */
    public static Object parseData(String json, Type type) {
        try {
            Gson gson = new Gson();
            Object result = gson.fromJson(json, type);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象转化为json字符串
     * @param o
     * @return
     */
    public static String objectToString(Object o) {
        try {
            Gson gson = new Gson();
            return gson.toJson(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转化为List对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object parseListData(String json, Class clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, (new TypeToken<List<T>>() {
            }).getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    /**
     * json转list 解决解析出来的数据解析出来是LinkTreeMap问题
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseToList(String json, Class clazz) {
        try {
            Type type = new ParameterizedTypeImpl(clazz);
            List<T> list = new Gson().fromJson(json, type);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * HashMap<String,Integer>为例
     */
    private  static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        /**
         * 返回实际类型组成的数据，即new Type[]{String.class,Integer.class}
         * @return
         */
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        /**
         *  返回原生类型，即 HashMap
         * @return
         */
        @Override
        public Type getRawType() {
            return List.class;
        }

        /**
         *  返回 Type 对象，表示此类型是其成员之一的类型。
         *  例如，如果此类型为 O<T>.I<S>，则返回 O<T> 的表示形式。 如果此类型为顶层类型，则返回 null。这里就直接返回null就行了。
         * @return
         */
        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
