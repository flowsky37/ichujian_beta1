package com.net.mokey.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.mokey.service.TouchView;
import com.net.mokeyandroid.R;

public class MyWindowManager {

	/**
	 * 小悬浮窗View的实例
	 */
	private static FloatWindowSmallView smallWindow;

	/**
	 * 大悬浮窗View的实例
	 */
	private static FloatWindowBigView bigWindow;

	static TouchView touchView;
	/**
	 * 小悬浮窗View的参数
	 */
	private static LayoutParams smallWindowParams;

	private static LayoutParams touchWindowParams;

	/**
	 * 大悬浮窗View的参数
	 */
	private static LayoutParams bigWindowParams;

	/**
	 * 用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager mWindowManager;

	/**
	 * 用于获取手机可用内存
	 */
	private static ActivityManager mActivityManager;

	/**
	 * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createSmallWindow(final Context context) {
		WindowManager windowManager = getWindowManager(context);
		// int screenWidth = windowManager.getDefaultDisplay().getWidth();
		// int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (smallWindow == null) {
			smallWindow = new FloatWindowSmallView(context);
			if (smallWindowParams == null) {
				smallWindowParams = new LayoutParams(-1, -2);
				smallWindowParams.format = 1;
				smallWindowParams.dimAmount = 0.0F;
				smallWindowParams.type = LayoutParams.TYPE_SYSTEM_ERROR |LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_LAYOUT_IN_SCREEN;
				smallWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = 1280|LayoutParams.FLAG_NOT_FOCUSABLE| LayoutParams.FLAG_LAYOUT_NO_LIMITS|LayoutParams.FLAG_NOT_TOUCH_MODAL;
//				smallWindowParams.type=2010;
//				smallWindowParams.flags=2|8|1280;
				smallWindowParams.gravity = 48;
				smallWindowParams.width = 1080;
				int height = getStatusBarHeight(context);
				Log.i("FinalHeight", "---->" + height);
				smallWindowParams.height = height;
				/*
				 * smallWindowParams.x = screenWidth; smallWindowParams.y =
				 * screenHeight / 2;
				 */
				/*
				 * smallWindowParams.x = 0; smallWindowParams.y = -80;
				 */
			}
			smallWindow.setParams(smallWindowParams);
			windowManager.addView(smallWindow, smallWindowParams);
		}
	}

	/**
	 * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	/*
	 * public static void createTextSmallWindow(Context context) { WindowManager
	 * windowManager = getWindowManager(context); //int screenWidth =
	 * windowManager.getDefaultDisplay().getWidth(); //int screenHeight =
	 * windowManager.getDefaultDisplay().getHeight(); if (touchView == null) {
	 * touchView = getView(context); if (touchWindowParams == null) {
	 * touchWindowParams = new LayoutParams(); touchWindowParams.type =
	 * LayoutParams.TYPE_PHONE; touchWindowParams.format =
	 * PixelFormat.RGBA_8888; touchWindowParams.flags =
	 * LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
	 * touchWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
	 * touchWindowParams.width = FloatWindowSmallView.viewWidth;
	 * touchWindowParams.height = FloatWindowSmallView.viewHeight;
	 * smallWindowParams.x = screenWidth; smallWindowParams.y = screenHeight /
	 * 2; touchWindowParams.x = 30; touchWindowParams.y = -50; }
	 * //touchView.setParams(touchWindowParams);
	 * windowManager.addView(touchView, touchWindowParams); } }
	 */

	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	private static TouchView getView(Context context) {
		TouchView localSuperTouchView = new TouchView(context);
		localSuperTouchView.setOrientation(1);
		localSuperTouchView.setGravity(17);
		return localSuperTouchView;
	}

	/**
	 * 将小悬浮窗从屏幕上移除。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeSmallWindow(Context context) {
		if (smallWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			smallWindow = null;
		}
	}

	/**
	 * 创建一个大悬浮窗。位置为屏幕正中间。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	/**
	 * 创建一个大悬浮窗。位置为屏幕正中间。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createBigWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (bigWindow == null) {
			bigWindow = new FloatWindowBigView(context);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				// bigWindowParams.x = screenWidth / 2 -
				// FloatWindowBigView.viewWidth / 2;
				// bigWindowParams.y = screenHeight / 2 -
				// FloatWindowBigView.viewHeight / 2;
				bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
				bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
				bigWindowParams.type = LayoutParams.TYPE_PHONE;
//				bigWindowParams.format = 1;
//				bigWindowParams.dimAmount = 0.0F;
//				bigWindowParams.flags = LayoutParams.FLAG_DIM_BEHIND;
				// bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
//				bigWindowParams.width = FloatWindowBigView.viewWidth;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL| LayoutParams.FLAG_NOT_FOCUSABLE;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = FloatWindowBigView.viewWidth; 
				bigWindowParams.height = FloatWindowBigView.viewHeight;
			}
			windowManager.addView(bigWindow, bigWindowParams);
		}
	}

	/**
	 * 将大悬浮窗从屏幕上移除。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeBigWindow(Context context) {
		if (bigWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(bigWindow);
			bigWindow = null;
		}
	}

	/**
	 * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 */
	public static void updateUsedPercent(Context context) {
		if (smallWindow != null) {
			TextView percentView = (TextView) smallWindow
					.findViewById(R.id.touch1);
			percentView.setText(getUsedPercentValue(context));
		}
	}

	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
	 */
	public static boolean isWindowShowing() {
		return smallWindow != null || bigWindow != null;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	/**
	 * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 * @return ActivityManager的实例，用于获取手机可用内存。
	 */
	private static ActivityManager getActivityManager(Context context) {
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return mActivityManager;
	}

	/**
	 * 计算已使用内存的百分比，并返回。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 * @return 已使用内存的百分比，以字符串形式返回。
	 */
	public static int getUsedPercentValue(Context context) {
		String dir = "/proc/meminfo";
		try {
			FileReader fr = new FileReader(dir);
			BufferedReader br = new BufferedReader(fr, 2048);
			String memoryLine = br.readLine();
			String subMemoryLine = memoryLine.substring(memoryLine
					.indexOf("MemTotal:"));
			br.close();
			long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll(
					"\\D+", ""));
			long availableSize = getAvailableMemory(context) / 1024;
			int percent = (int) ((totalMemorySize - availableSize)
					/ (float) totalMemorySize * 100);
			return percent ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取当前可用内存，返回数据以字节为单位。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 * @return 当前可用内存。
	 */
	private static long getAvailableMemory(Context context) {
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		getActivityManager(context).getMemoryInfo(mi);
		return mi.availMem;
	}

	public static void showBigPop(Context context) {
		PopupWindow popupWindow = new PopupWindow(context);

	}
}
