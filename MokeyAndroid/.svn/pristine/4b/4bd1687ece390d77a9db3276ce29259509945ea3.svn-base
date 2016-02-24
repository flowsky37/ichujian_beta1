package com.net.mokey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.application.MoKeyApplication.clearCallBack;
import com.net.mokey.view.MyWindowManager;
import com.net.mokeyandroid.R;

public class ClearActivity extends Activity {
	ImageView clear_logo;
	ImageView clear_circle;
	int nowCache;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.clear_layout);
		clear_circle = (ImageView) findViewById(R.id.clear_circle);
		clear_logo = (ImageView) findViewById(R.id.clear_logo);
		layout();
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.sircle);
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
				MoKeyApplication.getInstance().cache = MyWindowManager.getUsedPercentValue(ClearActivity.this);
				MoKeyApplication.getInstance().killBackgroundProcesses(ClearActivity.this, new clearCallBack() {
					
					@Override
					public void finish() {
						// TODO Auto-generated method stub
						nowCache = MyWindowManager.getUsedPercentValue(ClearActivity.this);
						int clearCsche = MoKeyApplication.getInstance().cache - nowCache;
						if(clearCsche==0){
							Toast.makeText(ClearActivity.this, "已经是最佳状态无需清理", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(ClearActivity.this, "成功清理"+clearCsche+"%的缓存", Toast.LENGTH_SHORT).show();
						}
					}
				});
				ClearActivity.this.finish();
			}
		});
		
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
