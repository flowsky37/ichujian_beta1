package com.net.mokey.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class MainFirst extends Activity implements OnClickListener{
	int check;
	ImageView first_btn,first_jt;
	RelativeLayout first_rl,first_news_rl,first_sm_rl_1,first_sm_rl_2;
	TextView first_sm_tv;
	TextView first_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.main_first_layout);
		first_btn = (ImageView) findViewById(R.id.first_btn);
		first_jt = (ImageView) findViewById(R.id.first_jt);
		first_sm_tv = (TextView) findViewById(R.id.first_sm_tv);
		first_sm_rl_1 = (RelativeLayout) findViewById(R.id.first_sm_rl_1);
		first_sm_rl_2 = (RelativeLayout) findViewById(R.id.first_sm_rl_2);
		first_rl = (RelativeLayout) findViewById(R.id.first_rl);
		first_tv = (TextView) findViewById(R.id.first_tv);
		first_news_rl = (RelativeLayout) findViewById(R.id.first_news_rl);
		first_news_rl.setVisibility(View.GONE);
		first_sm_rl_1.setVisibility(View.GONE);
		first_sm_rl_2.setVisibility(View.GONE);
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("name");  
        layout();
        //注册广播        
		registerReceiver(broadcastReceiver, myIntentFilter);
	}
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.first_cancle:
			
		}
	}
	public void setClickCallBaCK(int change) {
		// TODO Auto-generated method stub
		setlayout(change);
	}
	private void setlayout(int type){
		//int marginLeft = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
		//RelativeLayout.LayoutParams first_btnLayoutParams = (LayoutParams) first_btn.getLayoutParams();
		//RelativeLayout.LayoutParams first_news_rlLayoutParams = (LayoutParams) first_news_rl.getLayoutParams();
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 100);
		alphaAnimation.setDuration(8000);
		switch (type) {
		case 0:
			//first_news_rlLayoutParams.leftMargin = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
			/*AlphaAnimation alphaAnimation = new AlphaAnimation(100, 0);
			alphaAnimation.setDuration(1000);
			first_btnLayoutParams.setMargins(marginLeft, 25, 0, 0);
			first_btn.setBackgroundResource(R.drawable.news);
			first_btn.setLayoutParams(first_btnLayoutParams);
			first_tv.setText("一键新闻");
			first_rl.startAnimation(alphaAnimation);*/
			first_rl.setVisibility(View.GONE);
			first_sm_rl_1.setVisibility(View.GONE);
			first_sm_rl_2.setVisibility(View.GONE);
			first_news_rl.startAnimation(alphaAnimation);
			first_news_rl.setVisibility(View.VISIBLE);
			break;
		case 1:
			first_news_rl.clearAnimation();
			first_rl.setVisibility(View.GONE);
			first_news_rl.setVisibility(View.GONE);
			first_sm_rl_1.setVisibility(View.VISIBLE);
			first_sm_rl_2.setVisibility(View.GONE);
			first_sm_rl_1.startAnimation(alphaAnimation);
			/*first_sm_rlLayoutParams.setMargins(0, 25, marginLeft, 0);
			first_sm_rl.setVisibility(View.VISIBLE);
			first_rl.setVisibility(View.GONE);
			first_sm_rl.setLayoutParams(first_sm_rlLayoutParams);*/
			//first_sm_rl.startAnimation(alphaAnimation);
			break;
		case 2:
			first_sm_rl_1.clearAnimation();
			first_rl.setVisibility(View.GONE);
			first_news_rl.setVisibility(View.GONE);
			first_sm_rl_1.setVisibility(View.GONE);
			first_sm_rl_2.setVisibility(View.VISIBLE);
			first_sm_rl_2.startAnimation(alphaAnimation);
			/*first_sm_rlLayoutParams.setMargins(0, 25, 25, 0);
			first_sm_rl.setVisibility(View.VISIBLE);
			first_rl.setVisibility(View.GONE);
			first_sm_rl.setLayoutParams(first_sm_rlLayoutParams);*/
			//first_sm_rl.startAnimation(alphaAnimation);
			break;
		case 3:
			MoKeyApplication.getInstance().getAcache().put(SaveUtil.CHECK_SWITCH, "true");
			finish();
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int type = intent.getExtras().getInt("check");
			setlayout(type);
		}
	};
	private void layout(){
		RelativeLayout.LayoutParams first_rlLayoutParams = (LayoutParams) first_rl.getLayoutParams();
		first_rlLayoutParams.leftMargin =  (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.08);
		first_rl.setLayoutParams(first_rlLayoutParams);
		
		RelativeLayout.LayoutParams first_news_rlLayoutParams = (LayoutParams) first_news_rl.getLayoutParams();
		first_news_rlLayoutParams.leftMargin = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.36);
		first_news_rl.setLayoutParams(first_news_rlLayoutParams);
		
		RelativeLayout.LayoutParams first_sm_rl_2LayoutParams = (LayoutParams) first_sm_rl_1.getLayoutParams();
		first_sm_rl_2LayoutParams.rightMargin = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.32);
		first_sm_rl_1.setLayoutParams(first_sm_rl_2LayoutParams);
	}
}
