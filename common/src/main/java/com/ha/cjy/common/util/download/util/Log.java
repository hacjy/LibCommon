package com.ha.cjy.common.util.download.util;


import com.ha.cjy.common.BuildConfig;

/**
 * 调试类
 * 
 * @author linxin
 */
public class Log {

	/**
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(tag, msg);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void v(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(tag, msg, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, msg);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, msg, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(tag, msg);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(tag, msg, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(tag, msg);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void i(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(tag, msg, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, msg);
	}

	/**
	 * @param tag
	 * @param tr
	 */
	public static void w(String tag, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void w(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, msg, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 */
	public static void wtf(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, msg);
	}

	/**
	 * @param tag
	 * @param tr
	 */
	public static void wtf(String tag, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, tr);
	}

	/**
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void wtf(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, msg, tr);
	}
}
