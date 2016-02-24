package com.net.mokey.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.net.mokey.common.AppData;
import com.net.mokey.util.FileService;
import com.net.mokeyandroid.R;

public class AppDownLoadDialog extends Dialog implements OnClickListener {

	// 下载完成
	private final int DOWNLOAD_FINISH = 1;
	// 下载失败
	private final int DOWNLOAD_FAIL = 0;
	// 更新下载速度
	private final int DOWNLOAD_UPDATE_VELOCITY = 2;

	private int NOTIFICATION_ID = (int) System.currentTimeMillis();

	private final int DOWNLOAD_UPDATE_NOTIFY = 3;
	Activity context;
	private String name;
	private Drawable logodDrawable;
	private String size;
	private String packageName;
	private ProgressBar bar;
	private ProgressBar barStatusBar;

	private TextView progressSize;
	private TextView velocity;

	String apkUrl;
	// 线程停止标记
	boolean taskAbolish = false;
	private Notification notification = null;
	private NotificationManager manager = null;
	// 下载速度
	double velocityNum = 0;
	// 是否正在下载
	boolean isDownLoading = false;
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
				if (!taskAbolish) {
					String str = getAPKName();
					String fileName = Environment.getExternalStorageDirectory()
							+ "/mokey/apk/" + str;
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(new File(fileName)),
							"application/vnd.android.package-archive");
					context.startActivity(intent);
				} else {
					if (FileService.isExistFile(getAPKName(), "mokey/apk")) {
						FileService.deleteFileFromSdcard("mokey/apk",
								getAPKName());
					}
				}
				break;
			case DOWNLOAD_FAIL:
				break;

			case DOWNLOAD_UPDATE_VELOCITY:
				int velocity_num = (int) (Downloaded - Downloaded_old) / 1024;
				velocityNum = velocity_num;
				velocity.setText(velocity_num + "");
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

	public AppDownLoadDialog(Activity context, String name,
			Drawable logodDrawable, String size, String packageName) {
		super(context, R.style.customerDialog);
		this.context = context;
		this.name = name;
		this.logodDrawable = logodDrawable;
		this.size = size;
		this.packageName = packageName;
		AppData.isDownLoadingPackageApp.add(packageName);
	}

	DownLoadTask downLoadTask = new DownLoadTask();
	// 是否后台下载
	boolean isBackGround = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appdownload_dialog);
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(name);

		TextView tv_size = (TextView) findViewById(R.id.tv_size);
		tv_size.setText(size + "M");

		TextView tv_totalsize = (TextView) findViewById(R.id.tv_total_size);
		tv_totalsize.setText("/" + size + "M");

		progressSize = (TextView) findViewById(R.id.tv_progress_size);
		velocity = (TextView) findViewById(R.id.tv_velocity);

		ImageView iv_logo = (ImageView) findViewById(R.id.iv_logo);
		iv_logo.setImageDrawable(logodDrawable);

		Button btn_backdownload = (Button) findViewById(R.id.btn_backdownload);
		btn_backdownload.setOnClickListener(this);

		Button btn_cancle = (Button) findViewById(R.id.btn_cancel);
		btn_cancle.setOnClickListener(this);

		bar = (ProgressBar) findViewById(R.id.pb_progress);
		downLoadTask.execute(apkUrl);
		isDownLoading = true;

	}

	Timer time = new Timer();
	Timer timeBackGround = new Timer();
	// 已下载的部分
	double Downloaded;

	// 前一秒已下载的部分
	double Downloaded_old;

	int progressStatusSize = 0;
	double progressSizeValue = 0;

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
			bar.setMax(100);
			bar.setProgress(values[0]);
			double size = (double) (values[1]) / 1024 / 1024;
			progressStatusSize = values[0];
			progressSize.setText(getDouble(size) + "M");
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
				if (!taskAbolish&&response.getStatusLine().getStatusCode() == 200) {
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
					while ((len = inputStream.read(data)) != -1&&!taskAbolish) {
						outputStream.write(data, 0, len);
						length += len;
						int value = (int) (length / (double) file_length * 100);
						publishProgress(value, length);
					}

					if (!taskAbolish&&FileService.saveFileToSDCard(getAPKName(), "mokey/apk",
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
		String apkName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);

		if (apkName != null) {
			return apkName;
		}
		return null;
	}

	public String getDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		// DecimalFormat format = new DecimalFormat("#.00");
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
		notification = new Notification(R.drawable.download, name + "后台下载提醒",
				System.currentTimeMillis());

		notification.contentView = new RemoteViews(context.getApplication()
				.getPackageName(), R.layout.background_downstatus);

		// xxxnotification.contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(context, MainActivity.class), 0);

		notification.contentView.setTextViewText(R.id.tv_total_size, "/" + size
				+ "M");
		notification.contentView.setTextViewText(R.id.tv_name, "下载" + name);
		notification.contentView
				.setProgressBar(R.id.pb_progress, 100, 0, false);
		manager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
	}

	private void updateNotify() {
		if (isBackGround) {
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

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_backdownload:
			dismiss();
			initNotify();
			isBackGround = true;
			// manager.notify(NOTIFICATION_ID, notification);
			timeBackGround.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					myHandler.sendEmptyMessage(DOWNLOAD_UPDATE_NOTIFY);
				}
			}, 0, 1000);

			break;
		case R.id.btn_cancel:

			AppData.isDownLoadingPackageApp.remove(packageName);
			time.cancel();
			timeBackGround.cancel();
			if (downLoadTask.cancel(true)) {
				Log.i("TAG", "====>停止成功");
				taskAbolish = true;
				isDownLoading = false;
			}

			dismiss();
			break;

		default:
			break;
		}

	}

}
