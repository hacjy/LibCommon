package com.ha.cjy.common.util.download.abst;

import com.ha.cjy.common.util.download.kernel.BaseDownloadInfo;
import com.ha.cjy.common.util.download.kernel.DownloadWorkerSupervisor;
import com.ha.cjy.common.util.download.util.DownloadHttpUtil;
import com.ha.cjy.common.util.download.util.DownloadThreadUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通用下载抽象类，可支持断点续传，自动连接 <br>
 * 下载成功后的文件路径可通过回调函数onDownloadCompleted(String,String)获得
 * 支持自定义下载对象，但自定义下载对象的构造函数必须为XXXX(Context ctx,BaseDownloadInfo info);
 *
 * @author pdw
 * @version 1.0
 */
public abstract class ADownloadWorker<T extends BaseDownloadInfo> implements Runnable {

	/**
	 * 下载对象
	 */
	protected final T mDownloadInfo;
	/**
	 * 下载唯一标识符
	 */
	private final String identification;
	/**
	 * 下载url
	 */
	private String url;
	/**
	 * 下载文件的保存路径
	 */
	private String saveDir;

	/**
	 * 指定的下载文件名
	 */
	private String saveName;

	/**
	 * 下载完成后的文件全路径，eg. /sdcard/pandahome2/XXXX.apt
	 */
	private String saveFile;

	/**
	 * 下载百分比
	 */
	private int progress;

	private long totalSize;

	/**
	 * 下载临时文件后缀
	 */
	public static final String POSTFIX_FILE_NAME = ".temp";

	/**
	 * @see {@link DownloadHttpUtil#HTTP_REQUEST_PAUSE},
	 *      {@link DownloadHttpUtil#HTTP_REQUEST_CANCLE}
	 */
	private int requestType = DownloadHttpUtil.HTTP_REQUEST_NONE;

	private volatile boolean running = false;

	/**
	 * 构造下载线程 由此构造的下载线程将统一由{@link DownloadWorkerSupervisor}管理
	 * 
	 * @param info
	 */
	public ADownloadWorker(T info) {
		this.url = DownloadHttpUtil.utf8URLencode(info.getUrl());
		this.saveDir = info.getSaveDir();
		this.saveName = info.getSaveName();
		this.identification = info.getIdentification();
//		this.identification = info.getUrl();
		mDownloadInfo = info;
		maybeInitDir(saveDir);
	}

	protected void maybeInitDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		saveFile = new File(this.saveDir, saveName).getAbsolutePath();
	}

	@Override
	public void run() {
		download();
	}

	private void download() {
		running = true;
		int retryCount = 0;
		boolean shouldConn = true;
		byte[] buf = new byte[DownloadHttpUtil.BUFFER_SIZE];
		int size = 0; // read byte size per io
		long currentSize; // current size of file
		totalSize = 0; // total size of file
		File file = null;
		String tempFile;

		RandomAccessFile accessFile = null;

		InputStream in = null;
		HttpURLConnection httpConn = null;

		if (saveFile != null) {
			// 下载过程先生成临时文件
			tempFile = saveFile + POSTFIX_FILE_NAME;
		} else {
		
			onDownloadFailed(identification);
			return;
		}
		
		try {
			file = new File(saveFile);
			if (file.exists()) {
				onDownloadCompleted(identification, saveFile, file.length());
				return;
			}
			file = new File(tempFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			accessFile = new RandomAccessFile(tempFile, "rw");
			currentSize = file.length();
			onDownloadConnecting(identification, currentSize);
		} catch (Exception ex) {
			ex.printStackTrace();
			onDownloadFailed(identification);
			try {
				if (accessFile != null) {
					accessFile.close();
				}
			} catch (Exception x) {
				x.printStackTrace();
			}
			return;
		}
		
		/**
		 * 保存的文件名通过构造器传入，不需要处理302跳转 String url =
		 * HttpCommon.getRedirectionURL(this.url);
		 * 
		 * if (url == null) { Log.e(Global.TAG, "url is illegal ->"+this.url);
		 * onDownloadFailedWrap(identification,this.url); return; }
		 */
		while (shouldConn && retryCount < DownloadHttpUtil.MAX_REQUEST_RETRY_COUNTS) {
			if (!running || Thread.interrupted()) {
				if (requestType == DownloadHttpUtil.HTTP_REQUEST_PAUSE) {
					onDownloadPaused(identification);
				} else if (requestType == DownloadHttpUtil.HTTP_REQUEST_CANCLE) {
					if (file.exists())
						file.delete();
					onDownloadCanceled(identification);
				}

				return;
			}
			try {
				httpConn = getConnection(this.url);
				String sProperty = "bytes=" + currentSize + "-";
				// set break point
				httpConn.setRequestProperty("Range", sProperty);
				httpConn.connect();
				totalSize = httpConn.getContentLength();

				if (totalSize == -1) {
					onDownloadFailed(identification);
					return;
				}

				if (totalSize == currentSize) { // there is a trap here
					onDownloadCompleted(identification, saveFile, totalSize);
					break;
				} else {
					totalSize += currentSize;
				}

				// onBeginDownloadWrap(identification,this.url, currentSize,
				// (int) (currentSize * 100 / totalSize));
				onDownloadWorking(identification, totalSize, currentSize, (int) (currentSize * 100 / totalSize));
				accessFile.seek(currentSize);
				in = httpConn.getInputStream();
				// begin to download
				long lastTime = System.currentTimeMillis();
				while ((size = in.read(buf)) != -1) {
					if (!running || Thread.interrupted()) {
						if (requestType == DownloadHttpUtil.HTTP_REQUEST_PAUSE) {
							onDownloadPaused(identification);
						} else if (requestType == DownloadHttpUtil.HTTP_REQUEST_CANCLE) {
							if (file.exists())
								file.delete();
							onDownloadCanceled(identification);
						}
						return;
					}
					accessFile.write(buf, 0, size);
					currentSize += size;
					if (System.currentTimeMillis() - lastTime >= 1000 || currentSize == totalSize) {
						progress = (int) (currentSize * 100 / totalSize);
						onDownloadWorking(identification, totalSize, currentSize, progress);
						lastTime = System.currentTimeMillis();
					}
				}
				renameFile(file, saveFile);
				onDownloadCompleted(identification, saveFile, totalSize);
				shouldConn = false;
			} catch (Exception ex) {
				ex.printStackTrace();
				shouldConn = true;
				retryCount++;
				try {
					Thread.sleep(DownloadHttpUtil.RETRY_SLEEP_TIME); // sleep 2
					// seconds
				} catch (Exception e) {
					e.printStackTrace();
				}
//				if (retryCount == DownloadHttpUtil.MAX_REQUEST_RETRY_COUNTS) {
					onDownloadFailed(identification);
//				}
			} finally {
				try {
					if (in != null)
						in.close();

					if (httpConn != null)
						httpConn.disconnect();
					if (accessFile != null) {
						accessFile.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.yield();
			running = false;
		}
	}

	private void renameFile(File from, String to) {
		File toFile = new File(to);
		if (!toFile.exists())
			from.renameTo(toFile);
		toFile = null;
	}

	/**
	 *取消下载
	 * 需实现者自己管理下载线程的托管，需调用
	 * {@link DownloadWorkerSupervisor#remove(String)}移除当前线程的托管
	 * 
	 * @param identification
	 *        下载唯一标识
	 * @param identification
	 */
	protected abstract void onDownloadCanceled(String identification);

	/**
	 * 暂停下载
	 * 需实现者自己管理下载线程的托管，需调用
	 * {@link DownloadWorkerSupervisor#remove(String)}移除当前线程的托管
	 * 
	 * @param identification
	 *        下载唯一标识
	 */
	protected abstract void onDownloadPaused(String identification);

	/**
	 * 通知下载进度
	 * 
	 * @param identification
	 *        下载唯一标识
	 * @param totalSize
	 *        总字节数
	 * @param downloadSize
	 *        已下载字节数
	 * @param progress
	 *        百分比数字，如 80,90,100
	 */
	protected abstract void onDownloadWorking(String identification, long totalSize, long downloadSize, int progress);

	/**
	 * 下载完成
	 * 需实现者自己管理下载线程的托管，需调用{@link DownloadWorkerSupervisor#remove(String)}
	 * 移除当前线程的托管创建于 2012-7-18 上午09:09:09
	 * 
	 * @param identification
	 *        下载唯一标识
	 * @param file
	 *        下载成功后的文件路径，绝对路径
	 * @param totalSize
	 *        文件大小
	 */
	protected abstract void onDownloadCompleted(String identification, String file, long totalSize);

	/**
	 *
	 * 连接中
	 * 创建于 2012-7-18 上午09:09:35
	 * 
	 * @param identification
	 *        下载唯一标识
	 * @param downloadSize
	 *        已下载大小
	 */
	protected abstract void onDownloadConnecting(String identification, long downloadSize);

	/***
	 * 等待中
	 * 
	 * @param identification
	 */
	protected abstract void onDownloadWait(String identification);

	/***
	 * 暂停中
	 * 
	 * @param identification
	 */
	protected abstract void onDownloadPausing(String identification);

	/***
	 * 取消中
	 * 
	 * @param identification
	 */
	protected abstract void onDownloadCanceling(String identification);

	/**
	 * 下载失败
	 * 需实现者自己管理下载线程的托管，需调用{@link DownloadWorkerSupervisor#remove(String)}
	 * 移除当前线程的托管创建于 2012-7-18 上午09:09:49
	 * 
	 * @param identification
	 *        下载唯一标识
	 */
	protected abstract void onDownloadFailed(String identification);

	/**
	 * 删除临时文件，慎用，导致不能断点下载 ,或者下载异常<br>
	 * 启动线程之前调用
	 */
	public void deleteTempFile() {
		File file = new File(saveFile);
		if (file.exists())
			file.delete();
		if (!DownloadWorkerSupervisor.isDownloading(identification)) {
			try {
				file = new File(saveFile + POSTFIX_FILE_NAME);
				if (file.exists())
					file.delete();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 *
	 * 获取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileName(String url) {
		int lastIndex = url.lastIndexOf('/');
		try {
			return url.substring(lastIndex + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取网络连接
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	private HttpURLConnection getConnection(String urlStr) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		url = new URL(DownloadHttpUtil.utf8URLencode(urlStr));
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setConnectTimeout(15000);// break if it can't fetch connection
										// after 15 seconds
		conn.setReadTimeout(30000);// break connection if reading no data
									// after 30 seconds
		conn.setRequestProperty(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("User-Agent",
		// "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("Connection", "Keep-Alive");
		return conn;
	}

	/**
	 * 暂停下载线程，注意：暂停后会销毁掉线程，继续下载需重新构造下载对象。
	 * 调用完后不要再调用{@link #cancle()}否则会引起异常或者不可预知的错误
	 * 暂停下载会保留已下载的文件缓存
	 * date: 2012-8-21 下午05:23:02
	 */
	public final boolean pause() {
		onDownloadPausing(identification);
		requestType = DownloadHttpUtil.HTTP_REQUEST_PAUSE;
		if (running) {
			Thread.currentThread().interrupt();
			running = false;
		} else {
			DownloadThreadUtil.remove(this);
			onDownloadPaused(identification);
		}
		return true;
	}

	/**
	 * 取消下载线程，注意：取消后会销毁掉线程，继续下载需重新构造下载对象。
	 * 调用完后不要再调用{@link #pause()}否则会引起异常或者不可预知的错误
	 * 取消下载会删除已下载的文件缓存
	 * date: 2012-8-21 下午05:23:02
	 */
	public final boolean cancle() {
		onDownloadCanceling(identification);
		requestType = DownloadHttpUtil.HTTP_REQUEST_CANCLE;
		if (running) {
			Thread.currentThread().interrupt();
			running = false;
		} else {
			DownloadThreadUtil.remove(this);
			deleteTempFile();
			onDownloadCanceled(identification);
		}
		return true;
	}

	/**
	 *
	 * 获取下载的进度，百分比
	 * date: 2012-8-21 下午05:22:39
	 * 
	 * @return
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 设置指定下载文件名
	 * 
	 * @param specifyFileName
	 *        the specifyFileName to set
	 */
	public void setSpecifyFileName(String specifyFileName) {
		this.saveName = specifyFileName;
	}

	public synchronized boolean start() {
		
		if (DownloadWorkerSupervisor.add(identification, this)) {
			onDownloadWait(identification);
			DownloadThreadUtil.excuteDownload(this);
			return true;
		}
		return false;
	}

	public long getTotalSize() {
		return totalSize;
	}

	/**
	 * 获取下载唯一值
	 * 
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 必须在download()之前去替换URL，在download()方法之后替换，出了问题哥不负责!!!!
	 * 
	 * @param url
	 */
	public void updateDownloadUrl(String url) {
		this.url = DownloadHttpUtil.utf8URLencode(url);
	}

	/**
	 * 获取下载对象
	 * 
	 * @return
	 */
	public T getBaseDownloadInfo() {
		return mDownloadInfo;
	}
}
