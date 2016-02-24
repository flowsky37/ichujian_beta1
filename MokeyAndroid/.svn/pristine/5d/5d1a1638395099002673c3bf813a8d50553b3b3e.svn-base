package com.net.mokey.view;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.net.mokey.activity.ClearActivity;
import com.net.mokey.activity.MainActivity;
import com.net.mokey.activity.ShowAppActivity;
import com.net.mokey.activity.SmActivity;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.util.SaveUtil;
import com.net.mokey.utils.TipHelper;

public class FloatWindowSmallView extends LinearLayout implements
		GestureDetector.OnGestureListener {
	Context context;
	/**
	 * 用于更新小悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 * 小悬浮窗的参数
	 */
	private WindowManager.LayoutParams mParams;

	private GestureDetector mGestureDetector;
	Paint paint;
	Intent in;

	public FloatWindowSmallView(Context context) {
		super(context);
		this.context = context;
		this.mGestureDetector = new GestureDetector(this);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
		paint.setAlpha(1);
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		collapseStatusBar(context);
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		if (MoKeyApplication.getInstance().isScreenHorizonal()) {
			return ;
		}
		if (MoKeyApplication.getInstance().isScreenLocked()) {
			return ;
		}
		if (!MoKeyApplication.getInstance().touch_check)
			return;
		if (MoKeyApplication.getInstance().isScreenChange())
			return;
		if (e.getX() < (float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.23) && e.getY() < 75) {
			TipHelper.Vibrate(context, 100);
			MoKeyApplication.getInstance().useonekey(context,"1","2");
			if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM) == null) {
				in = new Intent(context, MipcaActivityCapture.class);
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
				return;
			}
			if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK) == null) {
				in = new Intent(context, MainActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
				return;
			}
			if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("0")) {
				//MyWindowManager.createBigWindow(context);
				in = new Intent(context, ClearActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
				MoKeyApplication.getInstance().hold(context,"2");
			} else if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("1")) {
				MoKeyApplication.getInstance().openWifi(context);
				MoKeyApplication.getInstance().hold( context,"1");
			}
		}
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float paramFloat2) {
		// TODO Auto-generated method stub
		if (Math.abs(paramFloat2) >= 15.0F) {
			int currentApiVersion = android.os.Build.VERSION.SDK_INT;
			try {
				Object service = context.getSystemService("statusbar");
				Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
				Method expand = null;
				if (service != null) {
					if (currentApiVersion <= 16) {
						expand = statusbarManager.getMethod("expand");
						expand.setAccessible(true);
						expand.invoke(service);
					} else {
						expand = statusbarManager.getMethod("expandNotificationsPanel");
					}
					expand.setAccessible(true);
					expand.invoke(service);
				}
			} catch (Exception e) {
			}
		}
		return true;// TODO Auto-generated method stub

	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("TIME", "====enter===="+System.currentTimeMillis()/1000);
		if (MoKeyApplication.getInstance().isScreenHorizonal()) {
			return false;
		}
		if (MoKeyApplication.getInstance().isScreenLocked()) {
			return false;
		}
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK_SWITCH)!=null){
			return false;
		}
		if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM) == null) {
			in = new Intent(context, MipcaActivityCapture.class);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(in);
		} else {
			if (MoKeyApplication.getInstance().getAcache()
					.getAsString(SaveUtil.CHECK_SWITCH) == null) {
				TipHelper.Vibrate(context, 100);
				Intent intent = new Intent("name");
				switch (MoKeyApplication.getInstance().mainFirst) {
				case 0:
					intent.putExtra("check", 0);
					context.sendBroadcast(intent);
					MoKeyApplication.getInstance().mainFirst = 1;
					break;
				case 1:
					intent.putExtra("check", 1);
					context.sendBroadcast(intent);
					MoKeyApplication.getInstance().mainFirst = 2;
					break;
				case 2:
					intent.putExtra("check", 2);
					context.sendBroadcast(intent);
					MoKeyApplication.getInstance().mainFirst = 3;
					break;
				case 3:
					intent.putExtra("check", 3);
					context.sendBroadcast(intent);
					MoKeyApplication.getInstance().mainFirst = 4;
					break;
				}
			} else {
				if (MoKeyApplication.getInstance().touch_check) {
					if (!MoKeyApplication.getInstance().isScreenChange()) {
						collapseStatusBar(context);
						if (e.getX() < (float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.23)&& e.getY() < 75) {
							TipHelper.Vibrate(context, 100);
							MoKeyApplication.getInstance().useonekey(context,"1","1");
							Intent in = null;
							Log.e("TAG", "----最顶端的class1-----"+MoKeyApplication.getInstance().getTopActivityName(context));
							Log.i("TIME", "====enter1===="+System.currentTimeMillis()/1000);
							if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM) == null) {
								Log.e("TAG", "----最顶端的class2-----"+MoKeyApplication.getInstance().getTopActivityName(context));
								in = new Intent(context,MipcaActivityCapture.class);
								in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(in);
							} else {
								if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK) == null) {
									Log.e("TAG", "----最顶端的class3-----"+MoKeyApplication.getInstance().getTopActivityName(context));
									if(!MoKeyApplication.getInstance().getTopActivityName(context).equals("com.net.mokey.activity.MainActivity")){
										in = new Intent(context, MainActivity.class);
										in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
										context.startActivity(in);
									}
								} else {
									if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("0")) {
										if (MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME).size() == 1) {
											if(MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME).get(0).getPageName().equals("com.net.mokeyandroid")&&MoKeyApplication.getInstance().getTopAppName(context).equals("com.net.mokeyandroid")){
												return false;
											}else{
												try {
													Log.i("TIME", "====enter2===="+System.currentTimeMillis()/1000);
													MoKeyApplication.getInstance().startApp(context,
													MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.
													getInstance().getDatabaseInstance().START_TABLE_NAME).get(0).
													getPageName());
													Log.i("TIME", "====enter3===="+System.currentTimeMillis()/1000);
													
												} catch (Exception e2) {
													// TODO: handle exception
													Toast.makeText(context, "应用程序已被卸载，请到膜键重新选择", 1).show();
												}
											}
										}
									} else if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("1")) {
										if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.SWITCH) != null) {
											if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.SWITCH).equals("1")) {
												try {
													MoKeyApplication.getInstance().startApp(context,MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_TABLE_NAME).get(0).getPageName());
													MoKeyApplication.getInstance().getAcache().put(SaveUtil.SWITCH,"2");
												} catch (Exception e2) {
													// TODO: handle exception
													Toast.makeText(context, "应用程序已被卸载，请到膜键重新选择", 1).show();
												}
											} else if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.SWITCH).equals("2")) {
												try {
													MoKeyApplication.getInstance().startApp(context,MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_TABLE_NAME).get(1).getPageName());
													MoKeyApplication.getInstance().getAcache().put(SaveUtil.SWITCH,"1");
												} catch (Exception e2) {
													// TODO: handle exception
													Toast.makeText(context, "应用程序已被卸载，请到膜键重新选择", 1).show();
												}
											}
										}
									} else if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("2")) {
										if(!MoKeyApplication.getInstance().getTopActivityName(context).equals("com.net.mokey.activity.ShowAppActivity")){
											Log.e("TAG", "----最顶端的class4-----"+MoKeyApplication.getInstance().getTopActivityName(context));
											in = new Intent(context, ShowAppActivity.class);
											if(MoKeyApplication.getInstance().isHome(context))
											{
												in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
											}else{
												
											in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
											}
											context.startActivity(in);
										  }
									}
								}
							}
						}
						if ((float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.24) < e.getX()&& e.getX() < (float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.47)&& e.getY() < 75) {
							TipHelper.Vibrate(context, 100);
							MoKeyApplication.getInstance().useonekey(context,"2","1");
							if (MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM) == null) {
								in = new Intent(context, MipcaActivityCapture.class);
								in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(in);
							}else{
								if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.NEWS_PAGENAME)!=null&&MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.NEWS_NAME)!=null){
									Map<String, String> map = new HashMap<String, String>();
									map.put(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.NEWS_NAME), MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.NEWS_PAGENAME));
									MoKeyApplication.getInstance().click(context, map, "1", "2");
									try {
										MoKeyApplication.getInstance().startApp(context, MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.NEWS_PAGENAME));
									} catch (Exception e2) {
										// TODO: handle exception
										Toast.makeText(context, "应用程序已被卸载，请到膜键重新选择", 1).show();
									}
								}else{
									in = new Intent(context, MainActivity.class);
									in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
									context.startActivity(in);
								}
							}
						}
						if ((float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.48) < e.getX()&& e.getX() < (float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.71)&& e.getY() < 75) {
							MoKeyApplication.getInstance().useonekey(context,"3","1");
							  collapseStatusBar(context);TipHelper.Vibrate(context
							  , 100); 
							  if(!MoKeyApplication.getInstance().getTopActivityName(context).equals("com.net.mokey.activity.SmActivity")){
									in = new Intent(context, SmActivity.class);
									in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
									context.startActivity(in);
							  }
							//Toast.makeText(context, "神秘功能敬请期待",Toast.LENGTH_SHORT).show();
						}
						if ((float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1] * 0.72) < e
								.getX()&& e.getX() < (float) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1])
								&& e.getY() < 75) {
							MoKeyApplication.getInstance().useonekey(context,"4","1");
							 collapseStatusBar(context);TipHelper.Vibrate(context, 100); 
							 if(!MoKeyApplication.getInstance().getTopActivityName(context).equals("com.net.mokey.activity.SmActivity")){
								in = new Intent(context, SmActivity.class);
								in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(in);
							  }
						}
					}
				}
			}
		}
		return false;
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		this.mGestureDetector.onTouchEvent(paramMotionEvent);
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawRect(new RectF(0, 0, (float) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.23), 100), paint);
		canvas.drawRect(new RectF((float) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.24), 0,
				(float) (MoKeyApplication.getInstance()
						.getDisplayHightAndWightPx()[1] * 0.47), 100), paint);
		canvas.drawRect(new RectF((float) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.48), 0,
				(float) (MoKeyApplication.getInstance()
						.getDisplayHightAndWightPx()[1] * 0.71), 100), paint);
		canvas.drawRect(new RectF((float) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.72), 0,
				(float) (MoKeyApplication.getInstance()
						.getDisplayHightAndWightPx()[1]), 100), paint);
		Paint localPaint = new Paint();
		localPaint.setColor(Color.RED);
		localPaint.setStyle(Paint.Style.FILL);
		localPaint.setAlpha(1);
		int i = getWidth();
		int j = getHeight();
		Log.e("mytag", getWidth() + "------" + getHeight());
		canvas.drawRect(new Rect((int) (0.039D * i), 0, (int) (0.139D * i), j),
				localPaint);
		// canvas.drawRect(new Rect((int)(0.313D * i), 0, (int)(0.413D * i), j),
		// localPaint);
		// canvas.drawRect(new Rect((int)(0.5870000000000001D * i), 0,
		// (int)(0.6870000000000001D * i), j), localPaint);
		// canvas.drawRect(new Rect((int)(0.861D * i), 0,
		// (int)(0.9610000000000001D * i), j), localPaint);
	}

	public static void collapseStatusBar(Context context) {
		try {
			Object statusBarManager = context.getSystemService("statusbar");
			Method localMethod = statusBarManager.getClass().getMethod(
					"collapsePanels", new Class[0]);
			for (Object localObject2 = statusBarManager.getClass().getMethod(
					"collapse", new Class[0]);; localObject2 = localMethod) {
				((Method) localObject2).invoke(statusBarManager, new Object[0]);
				// statusBarManager =
				// statusBarManager.getClass().getMethod("collapsePanels", new
				// Class[0]);
			}
			/*
			 * if (Build.VERSION.SDK_INT <= 16) { collapse =
			 * statusBarManager.getClass().getMethod("collapse"); } else {
			 * collapse =
			 * statusBarManager.getClass().getMethod("collapsePanels"); }
			 * collapse.invoke(statusBarManager);
			 */
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void showChoseApps() {

	}
}
