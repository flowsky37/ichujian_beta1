package com.net.mokey.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.application.MoKeyApplication.clearCallBack;
import com.net.mokeyandroid.R;

public class FloatWindowBigView extends LinearLayout {
	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;
	ImageView clear_logo;
	Context context;
	int nowCache;
	ImageView clear_circle;
	public FloatWindowBigView(final Context context) {
		super(context);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		clear_circle = (ImageView) findViewById(R.id.clear_circle);
		MoKeyApplication.getInstance().cache = MyWindowManager.getUsedPercentValue(this.context);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		clear_logo = (ImageView) findViewById(R.id.clear_logo);
		layout();
		Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.sircle);
		LinearInterpolator lin = new LinearInterpolator();
		animation.setInterpolator(lin);
		clear_circle.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				MoKeyApplication.getInstance().cache = MyWindowManager.getUsedPercentValue(FloatWindowBigView.this.context);
				MyWindowManager.removeBigWindow(FloatWindowBigView.this.context);
				MoKeyApplication.getInstance().killBackgroundProcesses(FloatWindowBigView.this.context, new clearCallBack() {
					
					@Override
					public void finish() {
						// TODO Auto-generated method stub
						nowCache = MyWindowManager.getUsedPercentValue(FloatWindowBigView.this.context);
						int clearCsche = MoKeyApplication.getInstance().cache - nowCache;
						if(clearCsche==0){
							Toast.makeText(context, "已经是最佳状态无需清理", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(context, "成功清理"+clearCsche+"%的缓存", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
		/*Button close = (Button) findViewById(R.id.close);
		Button back = (Button) findViewById(R.id.back);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.removeSmallWindow(context);
				Intent intent = new Intent(getContext(), FloatWindowService.class);
				context.stopService(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击返回的时候，移除大悬浮窗，创建小悬浮窗
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.createSmallWindow(context);
			}
		});*/
	}
	private void layout(){
		RelativeLayout.LayoutParams clear_circleLayoutParams = (android.widget.RelativeLayout.LayoutParams) clear_circle.getLayoutParams();
		clear_circleLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.2);
		clear_circleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.2);
		clear_circle.setLayoutParams(clear_circleLayoutParams);
		
		RelativeLayout.LayoutParams clear_logoLayoutParams = (android.widget.RelativeLayout.LayoutParams) clear_logo.getLayoutParams();
		clear_logoLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.18);
		clear_logoLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.18);
		clear_logo.setLayoutParams(clear_logoLayoutParams);
	}
}
