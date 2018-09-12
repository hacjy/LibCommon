package com.ha.cjy.common.util.download.util;

/**
 * 网络连接帮助类
 */
public class DownloadHttpUtil {

	/**
	 * 暂停请求
	 */
	public static final int HTTP_REQUEST_PAUSE = 1;

	/**
	 * 取消请求
	 */
	public static final int HTTP_REQUEST_CANCLE = 2;

	/**
	 * 未知请求
	 */
	public static final int HTTP_REQUEST_NONE = 3;

	/**
	 * auto retry to re-connect
	 */
	public static final int MAX_REQUEST_RETRY_COUNTS = 3;

	/**
	 * 重连时间间隔,2秒
	 */
	public final static int RETRY_SLEEP_TIME = 2000;

	/**
	 * 下载缓冲区
	 */
	public static int BUFFER_SIZE = 2048; // 缓冲区大小

	/**
	 * utf8
	 * 
	 * @return
	 */
	public static String utf8URLencode(String url) {
		if (url == null)
			return "";
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < url.length(); i++) {
			char c = url.charAt(i);
			if ((c >= 0) && (c <= 255)) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}
}
