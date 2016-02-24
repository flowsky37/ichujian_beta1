package com.net.mokey.request;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import org.apache.cordova.BuildConfig;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.net.mokey.common.AppData;
import com.net.mokey.common.AsyncTask;
import com.net.mokey.common.CustomProgressDialog;
import com.net.mokey.util.ImageUtil2;
import com.net.mokey.util.UpLoad;
import com.net.mokey.util.Utility;

/**
 * RequestManager
 * 
 * @author Andlisoft<br/>
 * 
 *         <pre>
 * 网络请求管理器，还负责显示loading对话框的打开和关闭
 * 
 *  使用说明
 *  1 声明一个接口作为activity的成员属性，用于处理返回数据  responseInfo
 * 
 *  IResponse response = new IResponse() {
 * 
 *  @Override
 *  public void handleMessage(ResponseInfo responseInfo) {
 *  //activity_update();
 *  }
 * 
 *  @Override
 *  public void handleException() {
 *  }
 * 
 *  };
 * 
 *  2 构建	RequestInfo，发起请求
 * 
 *  RequestInfo requestInfo = new RequestInfo();
 *  requestInfo.url = "http../.."; //地址用HttpUrls，参数的格式由HttpParamFormat定义
 *  requestInfo.json = ".."; //json字符串用JsonMaker生成
 *  RequestManager.newInstance().requestData(this, requestInfo,
 *  response);
 * </pre>
 * 
 *         2013-11-14
 */
public class RequestManager {

	/* 唯一实例 */
	// private static RequestManager instance;
	/* 响应的回调接口 */
	private IResponse responsehandle = null;
	/* 上下文 */
	private Context activity;
	/* 网络连接 */
	private HttpURLConnection httpConn = null;
	CustomProgressDialog dialog;

	private String path = Environment.getExternalStorageDirectory().getPath()
			+ "/schoolrun/cache/";
	private String fileName = "downloadfile";

	private static Toast toast = null;

	private RequestManager() {
		dialog = null;
	}

	/**
	 * 
	 * 创建对象
	 * 
	 * @params 无
	 * @return RequestManager 新建对象
	 * @throwsIOException说明发生此异常的条件
	 * @throwsNullPointerException说明发生此异常的条件
	 */
	public static RequestManager newInstance() {
		// if (instance == null) {
		// instance = new RequestManager();
		// }
		// return instance;
		return new RequestManager();
	}

	/**
	 * 
	 * 网络请求的外部调用方法
	 * <p>
	 * 返回时将回调responsehandle
	 * </p>
	 * 
	 * @params context 上下文
	 * @params requestInfo 请求信息
	 * @params responsehandle 回调接口
	 * @return void 无
	 * @throwsIOException说明发生此异常的条件
	 * @throwsNullPointerException说明发生此异常的条件
	 */
	public synchronized void requestData(Context activity,
			RequestInfo requestInfo, IResponse responsehandle) {

		if (dialog == null) {
			dialog = CustomProgressDialog.createDialog(activity);
		}
		this.responsehandle = responsehandle;
		this.activity = activity;
		ResponseInfo responseInfo = new ResponseInfo();

		boolean netSataus = false;
		ConnectivityManager conManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conManager != null && conManager.getActiveNetworkInfo() != null) {
			netSataus = conManager.getActiveNetworkInfo().isAvailable();
		}

		if (requestInfo.useCache) {
			String res = readCache(requestInfo.url);
			if (res != null && !res.equals("")) {
				responseInfo.setUrl(requestInfo.url);
				responseInfo.setResult(res);
				responseInfo.setFromCache(true);
				if (responsehandle != null)
					responsehandle.handleMessage(responseInfo);
			}
		}

		if (netSataus) {
			if (requestInfo.showDialog) {
				try {
					dialog.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// InterNetState=0;
			// Common.showLoadingNoCancel(this);
			new AsyncTaskX(responseInfo).execute(requestInfo);

		} else {
			Log.i("requestData", "netSataus is false");
			responsehandle.handleException();
			// Common.cancelLoading();
			if (isCurrentActivity()) {
				Toast.makeText(activity, "没有可用的网络连接", Toast.LENGTH_LONG).show();
			}
		}

	}

	/**
	 * 取消网络请求
	 */
	public void cancelRequest() {
		if (httpConn != null) {
			System.out.println("disconnect");
			httpConn.disconnect();
		}
	}

	/**
	 * 读缓存
	 */
	private String readCache(String url) {
		String path = Utility.getCacheWebDataPath();
		String md5 = ImageUtil2.md5(url);
		String fileCachePath = path + md5;
		return Utility.readFile(fileCachePath);
	}

	/**
	 * 写缓存
	 */
	private void writeCache(String xmlResult, String url) {
		String path = Utility.getCacheWebDataPath();
		String md5 = ImageUtil2.md5(url);
		String fileCachePath = path + md5;

		JSONObject mJson;
		try {
			mJson = new JSONObject(xmlResult);
			// int retcode = mJson.getInt("retcode");
			int retcode = 0;
			if (retcode != 0) {
				Log.e("", "writeCache result err" + xmlResult);
				return;
			} else {
				Utility.saveFile(fileCachePath, xmlResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void writeFile(String message) {

		BufferedWriter out = null;
		String fileName = getPath();
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
				f.setReadable(true, false);
			}
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName, true)));
			out.write(message);
			out.flush();
			out.close();
		} catch (Exception e) {

			e.printStackTrace();
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static final String SDCARD_CACHE_NET_TEST_PATH = Environment
			.getExternalStorageDirectory().getPath()
			+ "/schoolrun/net_test_cache/";

	public static String getPath() {
		String path = SDCARD_CACHE_NET_TEST_PATH;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		long currentTime = System.currentTimeMillis();
		String format = DateFormat.format("yyyy_MM_dd.log", currentTime)
				.toString();
		return path + format;
	}

	class AsyncTaskX extends AsyncTask<RequestInfo, Void, HttpResponse> {
		// /* 标记，用于对应request和response */
		// private String mark = null;
		ResponseInfo responseInfo = null;
		RequestInfo requestInfo = null;

		public AsyncTaskX(ResponseInfo responseInfo) {

			this.responseInfo = new ResponseInfo();
			this.responseInfo.setUrl(responseInfo.getUrl());
			this.responseInfo.setMark(responseInfo.getMark());
		}

		@Override
		protected HttpResponse doInBackground(RequestInfo... params) {
			this.requestInfo = params[0];
			String url = params[0].url;
			String json = params[0].json;

			responseInfo.setUrl(url);
			StringBuffer writeLog = new StringBuffer();
			long currentTime = System.currentTimeMillis();
			if (BuildConfig.DEBUG) {

				String format = DateFormat.format("yyyy_MM_dd|hh:mm:ss",
						currentTime).toString();
				writeLog.append("\n========================================");
				writeLog.append("\nstartTime:" + format);
				writeLog.append("\nstartTimeMillis:" + currentTime);
				writeLog.append("\nurl= " + url);
				writeLog.append("\njson= " + json);
			}

			HttpResponse res = null;
			if (!requestInfo.external && requestInfo.method.equals("GET")) {
				res = doSendJson(urlWrapper(url), requestInfo.method, json,
						requestInfo.download);
			} else {
				if (!requestInfo.ifUploadFile) {
					res = doSendJson(url, requestInfo.method, json,
							requestInfo.download);
				} else {
					try {
						Log.i("jsonString", "---->进入上传方法");
						String jsonString = UpLoad.post(requestInfo.url,
								requestInfo.params, requestInfo.files);
						Log.i("jsonString", "---->" + jsonString);
						Log.i("jsonString", "---->返回上传json值" + jsonString);
						HttpResponse resNew = new HttpResponse();
						resNew.content = jsonString;
						resNew.resCode = HttpURLConnection.HTTP_OK;
						res = resNew;
					} catch (Exception e) {

					}
				}
			}

			if (res != null) {
				responseInfo.setResult(res.content);
				Log.i("TAG", "---->设置json结果" + res.content);
				if (requestInfo.useCache) {
					writeCache(res.content, url);
				}
			}

			if (BuildConfig.DEBUG) {
				long endTime = System.currentTimeMillis();
				writeLog.append("\nendTimeMillis:" + endTime);
				writeLog.append("\nuseTime:" + (endTime - currentTime));
				writeFile(writeLog.toString());
			}

			return res;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(HttpResponse result) {
			if (requestInfo.showDialog) {
				try {
					if (dialog != null)
						dialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// **********************************************************************
			if (result != null)
				System.out.println("resultInfo=" + result.content);
			// **********************************************************************

			// if (mark.equals(responseInfo.getMark())) {
			try {

				if (result != null
						&& result.resCode == HttpURLConnection.HTTP_OK) { // ok
					if (requestInfo.showErrInfo) {
						try {
							JSONObject mJson = new JSONObject(result.content);
							// int retcode = mJson.getInt("retcode");
							int retcode = 0;

							if (retcode != 0) {
								String retmsg = mJson.getString("msg");
								if (retmsg != null) {
									if (toast == null) {
										toast = Toast.makeText(activity,
												retmsg, Toast.LENGTH_LONG);
									}
									toast.setText(retmsg);
									if (isCurrentActivity()) {
										toast.show();
									}
								}
							}

							JSONObject result_obj = mJson
									.getJSONObject("result");
							String resultCode = result_obj
									.getString("resultCode");
							if (resultCode != null && !resultCode.isEmpty()) {
								String message = result_obj
										.getString("message");
								if (toast == null) {
									toast = Toast.makeText(activity, message,
											Toast.LENGTH_LONG);
								}
								toast.setText(message);
								if (isCurrentActivity()) {
									toast.show();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (responsehandle != null)
						System.out.println("responsehandle="
								+ responsehandle.toString());
					System.out.println("responsehandle="
							+ responseInfo.getResult());
					responsehandle.handleMessage(responseInfo);

				} else { // failed
					if (toast == null) {
						toast = Toast.makeText(activity,
								"很抱歉，数据请求失败，请检查网络", Toast.LENGTH_LONG);
					}
					toast.setText("很抱歉，数据请求失败，请检查网络");
					Log.i("JSON", responseInfo.getResult());
					if (isCurrentActivity()) {
						toast.show();
					}
					Log.e("leo", "!!! Failed !!! " + responseInfo.getUrl());
					// Toast.makeText(context, /* R.string.responseerror */
					// "Failed! " + responseInfo.getUrl(), Toast.LENGTH_LONG)
					// .show();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
			// }
			// Common.cancelLoading();
		}
	}

	private boolean isCurrentActivity() {
		try {
			String actClass = activity.getClass().getCanonicalName();
			String topClass = getTopActivity(activity);
			if (topClass != null && topClass.contains(actClass)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).toString();
		else
			return null;

	}

	private String urlWrapper(String urls) {

		String newUrls = urls;
		if (!urls.contains("?")) {
			newUrls = urls.concat("?");
		}
		if (newUrls.endsWith("?")) {
			newUrls = newUrls.concat("channelid=" + AppData.getChannelid()
					+ "&deviceid=" + Utility.getImei(activity)
					+ "&device=Android" + "&osVersion="
					+ android.os.Build.VERSION.RELEASE + "&appVersion=" + ""
					+ Utility.GetVersion(activity));

		} else {
			newUrls = newUrls.concat("&channelid=" + AppData.getChannelid()
					+ "&deviceid=" + Utility.getImei(activity)
					+ "&device=Android" + "&osVersion="
					+ android.os.Build.VERSION.RELEASE + "&appVersion=" + ""
					+ Utility.GetVersion(activity));
		}
		return newUrls;
	}

	/**
	 * 执行请求json,推送间隔请求
	 * 
	 * @param urls
	 *            url接口路径，不需要封装通用参数
	 * @param json
	 *            请求的json报文
	 * @return String类型的XML
	 */
	private synchronized HttpResponse doSendJson(String urls, String method,
			String json, boolean download) {

		// **********************************************************************
		System.out.println("doInBackground request url=" + urls);
		System.out.println("doInBackground requestInfo=" + json);
		// **********************************************************************
		HttpResponse res = new HttpResponse();
		try {
			URL url = null;
			url = new URL(urls);

			httpConn = (HttpURLConnection) url.openConnection();
			// String method = HttpUrls.getRequestMethod(urls);
			httpConn.setRequestMethod(method);
			httpConn.setDoInput(true);
			if (method.equals("GET")) {
				httpConn.setRequestProperty("Content-Type",
						"text/xml; charset=UTF-8");

			} else if (method.equals("POST")) {
				if (json == null)
					return null;

				byte[] array = json.getBytes("UTF-8");
				httpConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				httpConn.setRequestProperty("Charset", "UTF-8");
				httpConn.setUseCaches(false);// 不使用Cache
				httpConn.setRequestProperty("Content-Length",
						String.valueOf(array.length));
				httpConn.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(
						httpConn.getOutputStream());
				out.write(array);
				out.flush();
				out.close();
			}
			// 连接超时
			httpConn.setConnectTimeout(30000);
			// 读取超时
			httpConn.setReadTimeout(30000);
			httpConn.connect();
			res.resCode = httpConn.getResponseCode();
			res.resMsg = httpConn.getResponseMessage();
			System.out.println("response code =" + res.resCode);
			System.out.println("response msg =" + res.resMsg);

			InputStream stream = httpConn.getInputStream();
			DataInputStream in = new DataInputStream(stream);
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			if (!download) {
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				res.content = strBuffer.toString();
			} else {
				// 创建写入文件内存流，\
				File file = new File(path + fileName);
				File filepath = new File(path);
				if (!filepath.exists()) {
					filepath.mkdirs();
				}
				if (file.exists()) {
					file.delete();
				}
				FileOutputStream fos = new FileOutputStream(file);
				while ((line = bufferedReader.readLine()) != null) {
					fos.write(line.getBytes());
					fos.flush();
				}
				res.content = path + fileName;
				fos.close();
			}
			in.close();
			// httpConn.disconnect();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	private class HttpResponse {
		int resCode;
		String resMsg;
		String content;
	}

}
