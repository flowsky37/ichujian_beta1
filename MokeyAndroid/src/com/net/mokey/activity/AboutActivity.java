package com.net.mokey.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokeyandroid.R;

public class AboutActivity extends Activity implements OnClickListener{
	TextView title_tv,about_web_tv,about_app_version;
	ImageView about_app_logo;
	RelativeLayout title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		layout();
	}
	private void init(){
		setContentView(R.layout.about_layout);
		title_tv = (TextView) findViewById(R.id.title_tv);
		about_app_logo = (ImageView) findViewById(R.id.about_app_logo);
		title = (RelativeLayout) findViewById(R.id.title_bar);
		about_web_tv= (TextView) findViewById(R.id.about_web_tv);
		//about_phone_tv = (TextView) findViewById(R.id.about_phone_tv);
		about_app_version = (TextView) findViewById(R.id.about_app_version);
		about_web_tv.setOnClickListener(this);
		//about_phone_tv.setOnClickListener(this);
		title_tv.setText("关于膜键");
		about_app_version.setText("版本：Beta V"+MoKeyApplication.getInstance().getAppVersionName(this));
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.about_web_tv:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url;
			content_url = Uri.parse("http://mokey.com.cn");
			intent.setData(content_url);
			startActivity(intent);
			break;
		/*case R.id.about_phone_tv:
//			MoKeyApplication.getInstance().MakeThePhoneCall(AboutActivity.this, "400-777-777");
			break;*/
		}
	}
	private void layout(){
		RelativeLayout.LayoutParams titleLayoutParams = (LayoutParams) title.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		title.setLayoutParams(titleLayoutParams);
		
		RelativeLayout.LayoutParams about_app_logoLayoutParams = (LayoutParams) about_app_logo.getLayoutParams();
		about_app_logoLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
		about_app_logoLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
		about_app_logo.setLayoutParams(about_app_logoLayoutParams);
		
		setTextviewLayout(about_web_tv);
		//setTextviewLayout(about_phone_tv);
	}
	private void setTextviewLayout(TextView textView){
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
		layoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
		textView.setLayoutParams(layoutParams);
	}
}
