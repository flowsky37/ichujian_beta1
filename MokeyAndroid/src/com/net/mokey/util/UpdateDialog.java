package com.net.mokey.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.common.AppData;
import com.net.mokeyandroid.R;

public class UpdateDialog extends Dialog implements android.view.View.OnClickListener{
	// 下载完成
		private final int DOWNLOAD_FINISH = 1;
		// 下载失败
		private final int DOWNLOAD_FAIL = 0;
		// 更新下载速度
		private final int DOWNLOAD_UPDATE_VELOCITY = 2;

		private int NOTIFICATION_ID = (int) System.currentTimeMillis();

		private final int DOWNLOAD_UPDATE_NOTIFY = 3;
		private String size;
		private String packageName="com.net.mokeyandroid";
		private ProgressBar barStatusBar;

		// 线程停止标记
//		boolean taskAbolish = false;
		private Notification notification = null;
		private NotificationManager manager = null;
		// 下载速度
		double velocityNum = 0;
		// 是否正在下载
		boolean isDownLoading = false;
		
		Timer time = new Timer();
		Timer timeBackGround = new Timer();
		// 已下载的部分
		double Downloaded;

		// 前一秒已下载的部分
		double Downloaded_old;

		int progressStatusSize = 0;
		double progressSizeValue = 0;
	String title;
	String content;
	Context context;
	String url;
	
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWNLOAD_FINISH:
				dismiss();
				time.cancel();
				timeBackGround.cancel();
				updateNotify();
				if (manager != null) {
					manager.cancel(NOTIFICATION_ID);
				}
				AppData.isDownLoadingPackageApp.remove(packageName);
//				if (!taskAbolish) {
					String str = getAPKName();
					String fileName = Environment.getExternalStorageDirectory()
							+ "/mokey/apk/" + str;
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(new File(fileName)),
							"application/vnd.android.package-archive");
					context.startActivity(intent);
//				} else {
//					if (FileService.isExistFile(getAPKName(), "mokey/apk")) {
//						FileService.deleteFileFromSdcard("mokey/apk",
//								getAPKName());
//					}
//				}
				break;
			case DOWNLOAD_FAIL:
				break;

			case DOWNLOAD_UPDATE_VELOCITY:
				int velocity_num = (int) (Downloaded - Downloaded_old) / 1024;
				velocityNum = velocity_num;
				Downloaded_old = Downloaded;
				break;

			case DOWNLOAD_UPDATE_NOTIFY:

				updateNotify();
				break;
			default:
				break;
			}

		}

	};
	public UpdateDialog(Context context,String title,String content,String url,String size) {
		super(context,R.style.customerDialog);
		// TODO Auto-generated constructor stub
		this.content = content;
		this.context = context;
		this.title = title;
		this.url = url;
		this.size=size;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_layout);
		Button update_cancle = (Button) findViewById(R.id.update_cancle);
		Button update_commit = (Button) findViewById(R.id.update_commit);
		TextView update_title = (TextView) findViewById(R.id.update_title);
		TextView update_content = (TextView) findViewById(R.id.update_content);
		update_title.setText("新版本"+title);
		update_content.setText(content);
//		RelativeLayout.LayoutParams update_cancleLayoutParams = (LayoutParams) update_cancle.getLayoutParams();
//		update_cancleLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
//		update_cancleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
//		update_cancle.setLayoutParams(update_cancleLayoutParams);
//		
//		RelativeLayout.LayoutParams update_commitLayoutParams = (LayoutParams) update_commit.getLayoutParams();
//		update_commitLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
//		update_commitLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
//		update_commit.setLayoutParams(update_commitLayoutParams);
		update_cancle.setOnClickListener(this);
		update_commit.setOnClickListener(this);
	}
	class DownLoadTask extends AsyncTask<String, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
//			bar.setMax(100);
//			bar.setProgress(values[0]);
			double size = (double)(values[1]) / 1024 / 1024;
			progressStatusSize = values[0];
			progressSizeValue = size;
			Downloaded = values[1];

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			// 声明客户端
			HttpClient httpClient = new DefaultHttpClient();
			// 使用Get请求指定url
			HttpGet httpGet = new HttpGet(params[0]);
			// 获得响应的结果
			HttpResponse response = null;
			byte[] result = null;
			try {
				response = httpClient.execute(httpGet);
				// 获得状态码
				if (response.getStatusLine().getStatusCode() == 200) {
					InputStream inputStream = null;
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					long file_length = response.getEntity().getContentLength();
					int length = 0;
					int len = 0;
					byte[] data = new byte[1024 * 400];
					inputStream = response.getEntity().getContent();
					Downloaded_old = 0;
					time.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							myHandler
									.sendEmptyMessage(DOWNLOAD_UPDATE_VELOCITY);
						}
					}, 1000, 1000);
					while ((len = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, len);
						length += len;
						int value = (int) (length / (double) file_length * 100);
						publishProgress(value, length);
					}

					if (FileService.saveFileToSDCard(getAPKName(), "mokey/apk",
							outputStream.toByteArray())) {

						myHandler.sendEmptyMessage(DOWNLOAD_FINISH);
					} else {
						myHandler.sendEmptyMessage(DOWNLOAD_FAIL);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				myHandler.sendEmptyMessage(DOWNLOAD_FAIL);
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
	public String getAPKName() {
		String apkName = url.substring(url.lastIndexOf("/") + 1);

		if (apkName != null) {
			return apkName;
		}
		return null;
	}
	public String getDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
//		DecimalFormat format = new DecimalFormat("#.00");
		return nf.format(d);

	}

	/**
	 * 时间戳转换时分(24小时制)
	 */
	public static String getStrTime() {
		Long tsLong = System.currentTimeMillis() / 1000;
		String time = tsLong.toString();
		String re_StrTime = null;
		SimpleDateFormat sdf = null;
		if (time.equals("")) {
			return "";
		}
		sdf = new SimpleDateFormat("HH:mm");
		long loc_time = Long.valueOf(time);
		re_StrTime = sdf.format(new Date(loc_time * 1000L));
		return re_StrTime;
	}

	private void initNotify() {
		notification = new Notification(R.drawable.download, "膜键后台下载提醒",
				System.currentTimeMillis());

		notification.contentView = new RemoteViews(context
				.getPackageName(), R.layout.background_downstatus);

		// xxxnotification.contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(context, MainActivity.class), 0);

		notification.contentView.setTextViewText(R.id.tv_total_size, "/" + size
				+ "M");
		notification.contentView.setTextViewText(R.id.tv_name, "下载膜键");
		notification.contentView
				.setProgressBar(R.id.pb_progress, 100, 0, false);
		manager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
	}

	private void updateNotify() {
			notification.contentView.setProgressBar(R.id.pb_progress, 100,
					progressStatusSize, false);
			notification.contentView.setTextViewText(R.id.tv_progress_size,
					getDouble(progressSizeValue) + "M");
			if (progressStatusSize == 100) {
				notification.contentView.setTextViewText(R.id.tv_velocity,
						0 + "");
			} else {
				notification.contentView.setTextViewText(R.id.tv_velocity,
						velocityNum + "");
			}
			notification.contentView
					.setTextViewText(R.id.tv_time, getStrTime());
			manager.notify(NOTIFICATION_ID, notification);
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.update_cancle:
			this.dismiss();
			
			break;
		case R.id.update_commit:
			if(url!=null){
				AppData.isDownLoadingPackageApp.add(packageName);
				Log.i("URLTAG", "------>"+url);
				new DownLoadTask().execute(url);
				initNotify();
				// manager.notify(NOTIFICATION_ID, notification);
				timeBackGround.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						myHandler.sendEmptyMessage(DOWNLOAD_UPDATE_NOTIFY);
					}
				}, 0, 1000);
//				Intent intent = new Intent();
//				intent.setAction("android.intent.action.VIEW");
//				Uri content_url;
//				content_url = Uri.parse(url);
//				intent.setData(content_url);
//				context.startActivity(intent);
				
			}
			this.dismiss();
			break;
		}
	}

	
}
