package com.net.mokey.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.qr_codescan.MipcaActivityCapture;
import com.net.mokey.activity.MainActivity;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.util.SaveUtil;
import com.net.mokey.view.MyWindowManager;
import com.net.mokeyandroid.R;

public class FloatWindowService extends Service {

	/**
	 * 用于在线程中创建或移除悬浮窗
	 */
	private Handler handler = new Handler();

	/**
	 * 定时器，定时进行当前应该创建还是移除悬浮窗
	 */
	private Timer timer;
	private Notification notification;  
    private NotificationManager nManager;
    private Intent intent;  
    private PendingIntent pIntent;
    private static final int ID = 1;  
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	PhoneStateListener listener = new PhoneStateListener() {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// 注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("挂断");
				MoKeyApplication.getInstance().touch_check = true;
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("接听");
				MoKeyApplication.getInstance().touch_check = false;
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("响铃:来电号码" + incomingNumber);
				MoKeyApplication.getInstance().touch_check = false;
				// 输出来电号码
				break;
			}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 定时器，每隔0.5秒刷新 if (timer == null) {
		timer = new Timer();
		timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
		nManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);  
		notification = new Notification();  
		notification.tickerText = "膜键";
		notification.icon = R.drawable.logo;
		 // 单击通知后会跳转到NotificationResult类 
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM) == null){
			intent = new Intent(this,MipcaActivityCapture.class);
		}else{
			intent = new Intent(this,MainActivity.class);
		}
        // 获取PendingIntent,点击时发送该Intent  
        pIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_CANCEL_CURRENT);  
        // 设置通知的标题和内容  
        notification.setLatestEventInfo(this, "膜键", "已开启膜键服务", pIntent);  
        // 发出通知  
        nManager.notify(ID, notification); 
		
		/*Notification notification = new Notification(R.drawable.logo,"膜 键", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
		notification.setLatestEventInfo(this, "膜键", "已开启膜键服务", pendingIntent);*/
		startForeground(1, notification);
		// 监听电话
		/*TelephonyManager tm = (TelephonyManager) this.getSystemService(Service.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);*/
		registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		return START_STICKY;
	}

	public void onStart(Intent paramIntent, int paramInt) {
		stopSelf();
		super.onStart(paramIntent, paramInt);
	}

	private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("TAG", "---->ReceiveBattery");
			
//			Intent intentNew = new Intent();
//			intentNew.setClass(FloatWindowService.this,
//					FloatWindowService.class);
//			startService(intentNew);
		}

	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		/*
		 * timer.cancel(); timer = null;
		 */
		Log.e("mytag", "------->kill======");
		stopForeground(false);
		Intent localIntent = new Intent();
		localIntent.setClass(this, FloatWindowService.class); // 销毁时重新启动Service
		this.startService(localIntent);
	}

	class RefreshTask extends TimerTask {

		@Override
		public void run() {
			// 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗�?
			if (!MyWindowManager.isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.createSmallWindow(getApplicationContext());
					}
				});
			}
			// 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗�?
			/*
			 * else if (!isHome() && MyWindowManager.isWindowShowing()) {
			 * handler.post(new Runnable() {
			 * 
			 * @Override public void run() {
			 * MyWindowManager.removeSmallWindow(getApplicationContext());
			 * MyWindowManager.removeBigWindow(getApplicationContext()); } }); }
			 */
			// 当前界面是桌面，且有悬浮窗显示，则更新内存数据�?
			else if (isHome() && MyWindowManager.isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.createSmallWindow(getApplicationContext());
					}
				});
			}
		}
	}

	/**
	 * 判断当前界面是否是桌面
	 */
	private boolean isHome() {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return getHomes().contains(rti.get(0).topActivity.getPackageName());
	}

	/**
	 * 获得属于桌面的应用的应用包名 *
	 * 
	 * @return 返回包含包名的字符串列表
	 */
	private List<String> getHomes() {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}

	public void collapseStatusBar(Context paramContext) {
		try {
			Object localObject1 = paramContext.getSystemService("statusbar");
			if (Build.VERSION.SDK_INT <= 16)
				;
			Method localMethod;
			for (Object localObject2 = localObject1.getClass().getMethod(
					"collapse", new Class[0]);; localObject2 = localMethod) {
				((Method) localObject2).invoke(localObject1, new Object[0]);
				localMethod = localObject1.getClass().getMethod(
						"collapsePanels", new Class[0]);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}
