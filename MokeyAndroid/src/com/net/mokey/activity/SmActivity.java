package com.net.mokey.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokeyandroid.R;

public class SmActivity extends Activity {
	TextView sm_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sm_main);
		sm_tv = (TextView) findViewById(R.id.sm_tv);
		layout();
	}
	private void layout(){
		RelativeLayout.LayoutParams main_title_rlLayoutParams = (LayoutParams) sm_tv.getLayoutParams();
		main_title_rlLayoutParams.setMargins(0, (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]*0.3), 0, 0);
		sm_tv.setLayoutParams(main_title_rlLayoutParams);
	}
}
