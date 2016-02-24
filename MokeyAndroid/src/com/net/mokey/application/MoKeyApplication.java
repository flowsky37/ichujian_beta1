package com.net.mokey.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.net.mokey.activity.net.HCKHttpResponseHandler;
import com.net.mokey.activity.net.RequestParams;
import com.net.mokey.bean.AppSaveBean;
import com.net.mokey.database.EntityDAO;
import com.net.mokey.http.HttpUtil;
import com.net.mokey.http.ThermometerHttp;
import com.net.mokey.request.IResponse;
import com.net.mokey.request.RequestInfo;
import com.net.mokey.request.RequestManager;
import com.net.mokey.request.ResponseInfo;
import com.net.mokey.util.ACache;
import com.net.mokey.util.BitmapUtil;
import com.net.mokey.util.SaveUtil;
import com.net.mokey.util.ToastUtil;
import com.net.mokey.util.UpdateDialog;
import com.net.mokey.view.FloatWindowSmallView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MoKeyApplication extends Application {
	static MoKeyApplication application;
	SharedPreferences preferences;
	String LONGCLICK = "longClick";
	String CLICK = "click";
	public String QRNUM = "qrnum";
	String FIRST = "first";
	String LONGCLICKFIRST = "longClickFirst";
	String CHOSETYPE = "choseType";
	//String USERID = "userId";
	String REMIND = "remind";
	String SWITCH = "switch";
	String NEWSCLICK = "newsClick";
	String NEWSCLICKFIRST = "newsClickFirst";
	String MAINFIRST = "mainFirst";
	String CHECK_SWITCH = "check_switch";
	String NEWS_SELECT = "news_select";
	public int mainFirst;
	private DisplayMetrics dm = new DisplayMetrics();
	EntityDAO entityDAO;
	FloatWindowSmallView floatWindowSmallView;
	public double latitude = 0.0;
	public double Longitude = 0.0;
	public int bar_width;
	public int bar_height;
	public String device_model;
	public String version_sdk;
	public String version_release;
	public boolean touch_check = true;
	public int cache;
	private ACache mCache;
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
		dm = getResources().getDisplayMetrics();
		preferences = getApplicationContext().getSharedPreferences("mokey", 0);
		device_model = Build.MODEL;// 设备型号
		version_sdk = Build.VERSION.SDK; // 设备SDK版本
		version_release = Build.VERSION.RELEASE; // 设备的系统版本
		initImageLoader(getApplicationContext());

		mCache = mCache.get(getApplicationContext());
		mLocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );
		
		initSensor();
	}
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			/*StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}*/
			MoKeyApplication.this.latitude = location.getLatitude();
			MoKeyApplication.this.Longitude = location.getLongitude();
		}
		
		public void onReceivePoi(BDLocation poiLocation) {
			/*if (poiLocation == null){
				return ; 
			}
			StringBuffer sb = new StringBuffer(256);
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			} 
			if(poiLocation.hasPoi()){
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			}else{				
				sb.append("noPoi information");
			}
			Log.e("mytag", "======定位的数据222======"+sb.toString());*/
		}
	}
	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "schoolrun/imgcache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())//
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.tasksProcessingOrder(QueueProcessingType.LIFO)//
				.writeDebugLogs()// Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	
	// 获取屏幕信息
	public void getScreenInfo() {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		dm = this.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		str += "屏幕分辨率为:" + dm.widthPixels + " * " + dm.heightPixels + "\n";
		str += "绝对宽度:" + String.valueOf(screenWidth) + "pixels\n";
		str += "绝对高度:" + String.valueOf(screenHeight) + "pixels\n";
		str += "逻辑密度:" + String.valueOf(density) + "\n";
		str += "X 维 :" + String.valueOf(xdpi) + "像素每英尺\n";
		str += "Y 维 :" + String.valueOf(ydpi) + "像素每英尺\n";
		Log.i("1", str);
	}

	public static MoKeyApplication getInstance() {
		return application;
	}

	public EntityDAO getDatabaseInstance() {
		if (entityDAO == null) {
			entityDAO = new EntityDAO(getApplicationContext());
		}
		return entityDAO;
	}

	public ACache getAcache() {
		if (mCache == null) {
			mCache = mCache.get(getApplicationContext());
		}
		return mCache;
	}

	public FloatWindowSmallView getSmallViewInstance() {
		if (floatWindowSmallView == null) {
			floatWindowSmallView = new FloatWindowSmallView(
					getApplicationContext());
		}
		return floatWindowSmallView;
	}

	// 保存长按
	public boolean setLongClick(int longClick) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(LONGCLICK, longClick, editor);
		return editor.commit();
	}

	// 取长按
	public int getLongClick() {
		return preferences.getInt(LONGCLICK, -1);
	}

	// 保存单击
	public boolean setClick(int longClick) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(CLICK, longClick, editor);
		return editor.commit();
	}

	// 取单击
	public int getClick() {
		return preferences.getInt(CLICK, -1);
	}

	// 保存切换
	public boolean setSwitch(int longClick) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(SWITCH, longClick, editor);
		return editor.commit();
	}

	// 取切换
	public int getSwitch() {
		return preferences.getInt(SWITCH, 0);
	}

	// 保存扫描码
	public boolean setQrNum(String qrNum) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(QRNUM, qrNum, editor);
		return editor.commit();
	}

	// 保存新闻的选择
	public boolean setNewsSelect(int newsSelect) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(NEWS_SELECT, newsSelect, editor);
		return editor.commit();
	}

	// 取新闻的选择
	public int getNewsSelect() {
		return preferences.getInt(NEWS_SELECT, 0);
	}

	// //取选择的新闻包名
	// public String getNewsPackageSelect(){
	// return
	//
	// }
	// 取扫描码
	public String getQrNum() {
		return preferences.getString(QRNUM, "");
	}

	public void update() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.commit();
	}

	// 保存单击选项
	public boolean setChoseType(int choseType) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(CHOSETYPE, choseType, editor);
		return editor.commit();
	}

	// 取出单击选项
	public int getChoseType() {
		return preferences.getInt(CHOSETYPE, 0);
	}

	// 保存单击第一次打开
	public boolean setFirst(boolean first) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(FIRST, first, editor);
		return editor.commit();
	}

	// 取出单击第一次打开
	public boolean getFirst() {
		return preferences.getBoolean(FIRST, false);
	}

	// 保存长按第一次打开
	public boolean setLongClickFirst(boolean first) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(LONGCLICKFIRST, first, editor);
		return editor.commit();
	}

	// 取出长按第一次打开
	public boolean getLongClickFirst() {
		return preferences.getBoolean(LONGCLICKFIRST, false);
	}

	/*// 保存用户id
	public boolean setUserId(String userId) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(USERID, userId, editor);
		return editor.commit();
	}

	// 取用户id
	public String getUserId() {
		return preferences.getString(USERID, "0");
	}*/

	// 保存新闻设置
	public boolean setNewsClick(String newsClick) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(NEWSCLICK, newsClick, editor);
		return editor.commit();
	}

	// 取新闻设置
	public String getNewsClick() {
		return preferences.getString(NEWSCLICK, "");
	}

	// 保存新闻按钮第一次打开
	public boolean setNewsClickFirst(boolean newsClickFirst) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(NEWSCLICKFIRST, newsClickFirst, editor);
		return editor.commit();
	}

	// 取新闻按钮第一次打开
	public boolean getNewsClickFirst() {
		return preferences.getBoolean(NEWSCLICKFIRST, false);
	}

	// 保存首页第一次打开
	public boolean setMainFirst(boolean mainFirst) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(MAINFIRST, mainFirst, editor);
		return editor.commit();
	}

	// 取首页第一次打开
	public boolean getMainFirst() {
		return preferences.getBoolean(MAINFIRST, false);
	}

	// 保存开关
	public boolean setCheckSwitch(boolean mainFirst) {
		SharedPreferences.Editor editor = preferences.edit();
		setDB(CHECK_SWITCH, mainFirst, editor);
		return editor.commit();
	}

	// 取开关
	public boolean getCheckSwitch() {
		return preferences.getBoolean(CHECK_SWITCH, true);
	}

	private void setDB(String key, Object value, SharedPreferences.Editor editor) {
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			boolean b = (Boolean) value;
			editor.putBoolean(key, b);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}
	}

	// 启动app
	public void startApp(Context context, String pageName) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(pageName);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				|Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
		context.startActivity(intent);
	}

	// 清除缓存
	public void killBackgroundProcesses(Context context, clearCallBack callBack) {
		ActivityManager activityManger = (ActivityManager) context
				.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> list = activityManger
				.getRunningAppProcesses();
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				ActivityManager.RunningAppProcessInfo apinfo = list.get(i);
				System.out.println("pid--" + apinfo.pid);
				System.out.println("processName--" + apinfo.processName);
				System.out.println("importance--" + apinfo.importance);
				Log.e("mytag", "pid===" + apinfo.pid + "===processname==="
						+ apinfo.processName + "===importance=="
						+ apinfo.importance);
				String[] pkgList = apinfo.pkgList;
				if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
					for (int j = 0; j < pkgList.length; j++) {
						// 2.2以上是过时的,请用killBackgroundProcesses代替
						if (!apinfo.processName.equals("com.net.mokeyandroid")) {
							activityManger.killBackgroundProcesses(pkgList[j]);
						}
						// activityManger.killBackgroundProcesses(pkgList[j]);
					}
				}
			}
		callBack.finish();
	}

	public interface clearCallBack {
		void finish();
	}

	public int dp2px(int dp) {
		return (int) (dp * getDisplayDensity() + 0.5f);
	}

	public int px2dp(int px) {
		return (int) (px / getDisplayDensity() + 0.5f);
	}

	public int pxToSp(int px) {
		return (int) (px / dm.scaledDensity);
	}

	public int spToPx(int sp) {
		return (int) (sp * dm.scaledDensity);
	}

	/**
	 * 获取 屏幕像素 px
	 * 
	 * @return Integer[高度，宽度]
	 */
	public int[] getDisplayHightAndWightPx() {
		return new int[] { dm.heightPixels, dm.widthPixels };
	}

	/**
	 * 获取 屏幕像素 dp
	 * 
	 * @return Integer[高度，宽度]
	 */
	public int[] getDisplayHightAndWightDp() {
		return new int[] { px2dp(getDisplayHightAndWightPx()[0]),
				px2dp(getDisplayHightAndWightPx()[1]) };
	}

	/**
	 * 获取像素密度
	 * 
	 * @return
	 */
	public float getDisplayDensity() {
		return dm.density;
	}

	// 获取数据库app数据
	public List<Integer> getApps(String tabName) {
		List<Integer> integers = new ArrayList<Integer>();
		Cursor cursor = MoKeyApplication.getInstance().getDatabaseInstance()
				.select(tabName);
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getString(0) != null) {
					if (!cursor.getString(0).equals("0")) {
						integers.add(cursor.getInt(0));
					}
				}
			} while (cursor.moveToNext());
		}
		return integers;
	}

	// 获取选择的app
	public List<AppSaveBean> getChoseApp(String tabName) {
		List<AppSaveBean> appSaveBeans = new ArrayList<AppSaveBean>();
		Cursor cursor = MoKeyApplication.getInstance().getDatabaseInstance().select(tabName);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					if (cursor.getString(0) != null) {
						if (!cursor.getString(0).equals("0")) {
							if(isInstalled(cursor.getString(1))){
								Log.e("mytag", cursor.getString(0)+"---"+cursor.getString(1)+"------"+cursor.getString(3));
								AppSaveBean appSaveBean = new AppSaveBean();
								appSaveBean.setPageName(cursor.getString(1));
								appSaveBean.setIcon(BitmapUtil.byteToDrawable(cursor.getBlob(2)));
								appSaveBean.setName(cursor.getString(3));
								appSaveBeans.add(appSaveBean);
							}
							// list.add(cursor.getString(1));
						}
					}
				} while (cursor.moveToNext());
			}
		}
		return appSaveBeans;
	}

	// 打开wifi
	public void openWifi(Context context) {
		// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
		Intent in = new Intent(Settings.ACTION_WIFI_SETTINGS);
		in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(in);
		/*
		 * ComponentName component = new ComponentName("com.android.settings",
		 * "com.android.settings.WirelessSettings");
		 * in.setComponent(component);//打开网络连接不是打开wifi界面
		 * in.setAction("android.intent.action.VIEW");
		 */
	}

	//获取IMEI值
	public String getImei(){
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if(manager.getDeviceId()!=null){
			return manager.getDeviceId();
		}
		return "0";
	}

	// 判断网络类型
	public boolean checkNetWork(Context context) {
		ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); // 检查网络连接，如果无网络可用，就不需要进行连网操作等
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if (info == null || !mConnectivity.getBackgroundDataSetting()) {
			return false;
		}
		// 判断网络连接类型，只有在2G/3G/wifi里进行一些数据更新。
		int netType = info.getType();
		int netSubtype = info.getSubtype();
		if (netType == ConnectivityManager.TYPE_WIFI) {
			return info.isConnected();
		} else if (netType == ConnectivityManager.TYPE_MOBILE
				&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
				&& !mTelephony.isNetworkRoaming()) {
			return info.isConnected();
		} else if (netSubtype == TelephonyManager.NETWORK_TYPE_GPRS
				|| netSubtype == TelephonyManager.NETWORK_TYPE_CDMA
				|| netSubtype == TelephonyManager.NETWORK_TYPE_EDGE) {
			return true;
		} else {
			return false;
		}
	}

	// 获取时间
	public String getTime() {
		Long tsLong = System.currentTimeMillis() / 1000;
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date();//获取当前时间
		return formatter.format(curDate);
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
	// 获取版本号
	public String getAppVersionName(Context context) {
		PackageManager pm = context.getPackageManager();// context为当前Activity上下文
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			String version = pi.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}

	// 判断网络
	public boolean isConnect() {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 横屏 true 竖屏false
	 * 
	 * @return
	 */
	public boolean isScreenChange() {
		Configuration mConfiguration = this.getResources().getConfiguration(); // 获取设置的配置信息
		int ori = mConfiguration.orientation; // 获取屏幕方向
		if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
			// 横屏
			return true;
		} else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
			// 竖屏
			return false;
		}
		return false;
	}

	// 手机是否横屏
	private boolean isHorizonal = false;
	// 手机是否锁屏
	private boolean isSceenLocked = false;
	private SensorManager manager;
	private Sensor mSensor;

	public void initSensor() {
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				
//				Log.i("currentActivity", "=====currentActivity===="+getTopActivityName(context));
//				isHome(getApplicationContext());
//				Log.i("getXY", "=====height:"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]+"=====width:"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]);
				final float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				if ((x < 10 && x > 8) || (x > -10 && x < -8)
						|| (x > 1 && z > 1 && y < 3) || (x < -1 && z > 1 && y <1)) {
					isHorizonal = true;
				} else {
					isHorizonal = false;
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		}, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * 横屏 true 竖屏false
	 * 
	 * @return
	 */
	public boolean isScreenHorizonal() {
		return isHorizonal;
	}

	/**
	 * 锁屏true 未锁屏false
	 * 
	 * @return
	 */
	public boolean isScreenLocked() {
		CheckScreenStatus();
		return isSceenLocked;
	}

	// 监测屏幕是否锁屏
	public void CheckScreenStatus() {
		KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
			isSceenLocked = true;
		} else {
			isSceenLocked = false;
		}
	}
   
	/**
	 * 获取所有的app
	 * 
	 * @return
	 */
	public List<AppSaveBean> getAllApp() {
		List<AppSaveBean> appSaveBeans = new ArrayList<AppSaveBean>();
		PackageManager pm = this.getPackageManager(); // 获得PackageManager对象
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 通过查询，获得所有ResolveInfo对象.
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
		// 调用系统排序 ， 根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
		Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));
		if (appSaveBeans != null) {
			appSaveBeans.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
				String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
				Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
				// 为应用程序的启动Activity 准备Intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(pkgName,activityName));
				// 创建一个AppInfo对象，并赋值
				AppSaveBean bean = new AppSaveBean();
				bean.setIcon(reInfo.loadIcon(pm));
				bean.setName((String) reInfo.loadLabel(pm));
				bean.setPageName(reInfo.activityInfo.packageName);
				appSaveBeans.add(bean);
			}
		}
		/*List<AppSaveBean> appSaveBeans = new ArrayList<AppSaveBean>();
		PackageManager packageManager = getPackageManager();
		List<PackageInfo> list = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		for (PackageInfo packageInfo : list) {
<<<<<<< .mine
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				ApplicationInfo applicationInfo = packageInfo.applicationInfo;
				AppSaveBean bean = new AppSaveBean();
				bean.setIcon(packageInfo.applicationInfo
						.loadIcon(packageManager));
				bean.setName((String) applicationInfo.loadLabel(packageManager));
				bean.setPageName(packageInfo.packageName);
				appSaveBeans.add(bean);
			}
		}
=======
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			Log.e("mytag", applicationInfo.loadLabel(packageManager)+"----"+packageInfo.packageName);
			if(packageInfo.packageName.equals("com.sec.android.app.popupcalculator")
				||packageInfo.packageName.equals("com.android.providers.contacts")
				||packageInfo.packageName.equals("com.android.contacts")
				||packageInfo.packageName.equals("com.sec.android.app.camera")
				||packageInfo.packageName.equals("com.sec.android.app.myfiles")
				||packageInfo.packageName.equals("com.sec.android.app.controlpanel")){
				 AppSaveBean bean = new AppSaveBean();
				 bean.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
				 bean.setName((String) applicationInfo.loadLabel(packageManager));
				 bean.setPageName(packageInfo.packageName);
				 appSaveBeans.add(bean);	
			}
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				 AppSaveBean bean = new AppSaveBean();
				 bean.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
				 bean.setName((String) applicationInfo.loadLabel(packageManager));
				 bean.setPageName(packageInfo.packageName);
				 appSaveBeans.add(bean);	
			}
		}*/
		return appSaveBeans;
	}

	/**
	 * 判断app是否已安装
	 * 
	 * @return
	 */

	public boolean isInstalled(String packageName) {
		PackageInfo packageInfo;
		try {
			packageInfo = this.getPackageManager().getPackageInfo(packageName,
					0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			System.out.println("没有安装");
			return false;
		}
		return true;
	}

	public String copJson(Context context, String name) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context
					.getResources().getAssets().open(name));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 打电话
	public void MakeThePhoneCall(Context context, final String content) {
		new AlertDialog.Builder(context).setTitle("提示")
				.setMessage("确定拨打" + content + "？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent in = new Intent();
						in.setAction(Intent.ACTION_CALL);
						Uri uri = Uri.parse("tel:" + content);
						in.setData(uri);
						in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(in);
					}
				}).setNegativeButton("取消", null).create().show();
	}

	// 获取扫描二维码的数据
		public int checkQr() {
			List<String> list = new ArrayList<String>();
			Cursor cursor = MoKeyApplication.getInstance().getDatabaseInstance()
			.select(MoKeyApplication.getInstance().getDatabaseInstance().QRTABLE);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						if (cursor.getString(0) != null) {
							if (!cursor.getString(0).equals("0")) {
								list.add(cursor.getString(1));
							}
						}
					} while (cursor.moveToNext());
				}
			} else {
				return 0;
			}
			return list.size();
		}
	/**
	 * 单击设置记录接口
	 * 
	 * @param map
	 * @param clickType
	 */
	public void click(Context context,Map<String, String> map,String clickType,String key){
		RequestParams parameters = new RequestParams();
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID)!=null){
			parameters.put("userid", MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID));
		}else{
			parameters.put("userid", "0");
		}
		parameters.put("imei", MoKeyApplication.getInstance().getImei());
		parameters.put("clicktype", clickType);
		parameters.put("key", key);
		try {
			parameters.put("name_package", writeJson(map));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parameters.put("actiondate", MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.CLICK, parameters,
				new HCKHttpResponseHandler() {

			@Override
			public void onFinish(String url) {
				// TODO Auto-generated method stub
				super.onFinish(url);
				Log.e("mytag", "========"+url);
			}

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (content != null) {
					Log.e("mytag", "===CHANGAN记录打开=====" + content);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
			}
		});
	}
//		RequestInfo info = new RequestInfo();
//		
//		info.useCache = false;
//		info.showDialog = false;
//		String str="";
//		try {
//			str = "userid="+MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID)+"&imei="+MoKeyApplication.getInstance().getImei()+"&clicktype="+clickType+"&name_package="+MoKeyApplication.getInstance().writeJson(map)+"&actiondate="+MoKeyApplication.getInstance().getTime()+"&key="+key;
//			Log.e("mytag", "=======浏览器访问====="+info.url+str);
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		info.url = ThermometerHttp.CLICK+str;
//		RequestManager.newInstance().requestData(context,info,new IResponse() {
//			
//			@Override
//			public void handleMessage(ResponseInfo responseInfo) {
//				// TODO Auto-generated method stub
//				Log.e("mytag", "---单击设置--"+responseInfo.getResult());
//			}
//			@Override
//			public void handleException() {
//				// TODO Auto-generated method stub
//			}
//		});
//	}
	/**
	 * 把map转成json
	 * 
	 * @param map
	 * @return
	 * @throws JSONException
	 */
	public String writeJson(Map<String, String> map) throws JSONException {
		JSONArray array = new JSONArray();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("appName", entry.getKey());
			jsonObject.put("appPackage", entry.getValue());
			array.put(jsonObject);
		}
		return array.toString();
	}

	/**
	 * 把list转成map
	 * 
	 * @param appSaveBeans
	 * @return
	 */
	public Map<String, String> listToMap(List<AppSaveBean> appSaveBeans) {
		Map<String, String> map = new HashMap<String, String>();
		for (AppSaveBean appSaveBean : appSaveBeans) {
			map.put(appSaveBean.getName(), appSaveBean.getPageName());
		}
		return map;
	}

	/**
	 * 长按设置记录接口
	 * 
	 * @param map
	 */
	public void hold(Context activity,String type){
		/*RequestInfo info = new RequestInfo();
		info.url = ThermometerHttp.HOLD;
		info.useCache = false;
		info.showDialog = false;
		info.json = "userid="+MoKeyApplication.getInstance().getUserId()+"&imei="+MoKeyApplication.getInstance().getImei()+"&holdtype="+type+"&name_package=com.net.mokeyandroid"+"&actiondate="+MoKeyApplication.getInstance().getTime();
		RequestManager.newInstance().requestData(activity, info,new IResponse() {
			
			@Override
			public void handleMessage(ResponseInfo responseInfo) {
				// TODO Auto-generated method stub
			}
			@Override
			public void handleException() {
				// TODO Auto-generated method stub
			}
		});*/
		RequestParams parameters = new RequestParams();
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID)!=null){
			parameters.put("userid", MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID));
		}else{
			parameters.put("userid", "0");
		}
		parameters.put("imei", MoKeyApplication.getInstance().getImei());
		parameters.put("holdtype", type);
		parameters.put("name_package", "com.net.mokeyandroid");
		parameters.put("actiondate", MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.HOLD, parameters,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						if (content != null) {
							Log.e("mytag", "===CHANGAN记录打开=====" + content);
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
					}
				});
	}
	//获取当前最顶端的程序
	public  String getTopAppName(Context context){
        String topActivityClassName=null;
         ActivityManager activityManager =
        (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
         if(runningTaskInfos != null){
             ComponentName f=runningTaskInfos.get(0).topActivity;
             topActivityClassName=f.getPackageName();
             Log.e("mytag", "----当前activity------"+topActivityClassName);
         }
         return topActivityClassName;
    }
	public  String getTopActivityName(Context context){
        String topActivityClassName=null;
         ActivityManager activityManager =
        (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
         if(runningTaskInfos != null){
             ComponentName f=runningTaskInfos.get(0).topActivity;
             topActivityClassName=f.getClassName();
             Log.e("mytag", "----当前activity------"+topActivityClassName);
         }
         return topActivityClassName;
    }
	//判断当前是否处于系统桌面
	public boolean isHome(Context context)
	{
		String topActivityClassName=null;
        ActivityManager activityManager =
       (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityClassName=f.getClassName();
            
            Log.e("mytag", "----当前activity------"+topActivityClassName);
            if(topActivityClassName.contains(".Launcher")){
            	return true;
            }
        }
        return false;
		
	}
    public String getPackageName(Context context){
         String packageName = context.getPackageName(); 
         return packageName;
    }

	/**
	 * 按键行为记录接口
	 */
	public void useonekey(Context activity, String key, String type) {

//		RequestInfo info = new RequestInfo();
//		info.url = ThermometerHttp.USEONEKEY;
//		info.useCache = false;
//		info.showDialog = false;
//		info.json = "userid=" + MoKeyApplication.getInstance().getAcache() .getAsString(SaveUtil.UID)
//				+ "&imei=" + MoKeyApplication.getInstance().getImei()
//				+ "&type=" + type + "&key=" + key + "&actiondate="
//				+ "aaa";
//		RequestManager.newInstance().requestData(activity, info,
//				new IResponse() {
//
//					@Override
//					public void handleMessage(ResponseInfo responseInfo) {
//						Log.e("CLICKclick", "===记录点击事件=====" + responseInfo.getResult());
//					}
//
//					@Override
//					public void handleException() {
//					}
//				});

		 RequestParams parameters = new RequestParams();
		 if (MoKeyApplication.getInstance().getAcache()
		 .getAsString(SaveUtil.UID) != null) {
		 parameters.put("userid", MoKeyApplication.getInstance().getAcache()
		 .getAsString(SaveUtil.UID));
		 } else {
		 parameters.put("userid", "0");
		 }
		 parameters.put("imei", MoKeyApplication.getInstance().getImei());
		 parameters.put("type", type);
		 parameters.put("key", key);
		 parameters.put("actiondate",
		 MoKeyApplication.getInstance().getTime());
		 HttpUtil.get(ThermometerHttp.USEONEKEY, parameters,
		 new HCKHttpResponseHandler() {
		
		 @Override
		 public void onFinish(String url) {
		 // TODO Auto-generated method stub
		 super.onFinish(url);
		 }
		
		 @Override
		 public void onSuccess(String content) {
		 // TODO Auto-generated method stub
		 super.onSuccess(content);
		 Log.e("CLICK", "===记录点击事件=====" + content);
		 if (content != null) {
		
		 }
		 }
		
		 @Override
		 public void onFailure(Throwable error, String content) {
		 // TODO Auto-generated method stub
		 super.onFailure(error, content);
		 Log.e("mytag", "===记录点击事件222=====" + content);
		 }
		 });
	}

	public void update(final Context context, final boolean isCheckActive,
			final boolean isShowDialog) {
		final ProgressDialog mSpinner = new ProgressDialog(context);
		if (isShowDialog) {
			mSpinner.setMessage("正在检测...");
			mSpinner.show();
		}
		RequestParams parameters = new RequestParams();
		parameters.put("version", MoKeyApplication.getInstance().getAppVersionName(this));
		HttpUtil.get(ThermometerHttp.UPDATE, parameters,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
						if (isShowDialog) {
							mSpinner.dismiss();
					}
					}
					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.e("mytag", "--成功-"+content);
						if(content != null){
							mSpinner.dismiss();
							try {
								final JSONObject jsonObject = new JSONObject(content);
								if(jsonObject.getString("yesorno").equals("Y")){
									UpdateDialog dialog = new UpdateDialog(context, jsonObject.getString("version"),jsonObject.getString("description"),jsonObject.getString("url"),jsonObject.getString("size"));
									dialog.show();
								}else{
									if(isCheckActive){
									Toast.makeText(context, "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						mSpinner.dismiss();
						ToastUtil.showToast(context, "检查更新失败，请检查网络");
					}
				});
		}
	}
