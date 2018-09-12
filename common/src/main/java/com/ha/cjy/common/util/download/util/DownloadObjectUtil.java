package com.ha.cjy.common.util.download.util;

import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DownloadObjectUtil {

	private final static String KEY_CLASS_NAME = "class_name";
	private final static String FILTER_CREATOR = "CREATOR";

	/**
	 * 将对象转换为HashMap表
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public final static Map object2Map(Object object) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class<? extends Object> clazz = object.getClass();
		Field[] fields = ReflectionUtils.getDeclaredFields(object);
		hashMap.put(KEY_CLASS_NAME, clazz.getName());
		for (Field field : fields) {
			try {
				final String fieldName = field.getName();
				if (fieldName.equals(FILTER_CREATOR))
					continue;
				final Object val = ReflectionUtils.getFieldValue(object, fieldName);
				hashMap.put(fieldName, val);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return hashMap;
	}

	/**
	 * 将HashMap转换为对象
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public final static Object map2Object(Map map) {
		final String className = (String) map.get(KEY_CLASS_NAME);
		try {
			Class<? extends Object> clazz = Class.forName(className);
			Object object = clazz.newInstance();
			Field[] fields = ReflectionUtils.getDeclaredFields(object);
			for (Field field : fields) {
				final String fieldName = field.getName();
				if (fieldName.equals(FILTER_CREATOR))
					continue;
				final Object val = map.get(fieldName);
				ReflectionUtils.setFieldValue(object, fieldName, val);
			}
			return object;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将Map转换为下载信息对象
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public final static BaseDownloadInfo map2DownloadInfo(Map map) {
		Object object = map2Object(map);
		if (object != null && (object instanceof BaseDownloadInfo)) {
			BaseDownloadInfo info = (BaseDownloadInfo) object;
			return info;
		}
		return null;
	}

}
