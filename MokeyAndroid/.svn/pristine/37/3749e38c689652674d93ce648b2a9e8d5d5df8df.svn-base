package com.net.mokey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokeyandroid.R;

public class FirstActivity extends Activity implements OnClickListener{
	Button first_cancle;
	int check;
	ImageView first_btn,first_jt;
	TextView first_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.first_layout);
		first_cancle = (Button) findViewById(R.id.first_cancle);
		first_btn = (ImageView) findViewById(R.id.first_btn);
		first_jt = (ImageView) findViewById(R.id.first_jt);
		first_tv = (TextView) findViewById(R.id.first_tv);
		first_cancle.setOnClickListener(this);
		if(getIntent().getExtras()!=null){
			check = getIntent().getExtras().getInt("first");
		}
		RelativeLayout.LayoutParams first_btnLayoutParams = (LayoutParams) first_btn.getLayoutParams();
		first_btnLayoutParams.leftMargin = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.05);
		first_btn.setLayoutParams(first_btnLayoutParams);
		//按钮适配
		RelativeLayout.LayoutParams first_cancleLayoutParams = (LayoutParams) first_cancle.getLayoutParams();
		first_cancleLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.3);
		first_cancleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
		first_cancle.setLayoutParams(first_cancleLayoutParams);
		if(check == 0){
			first_tv.setText("一键操控，单击设置");
		}
		if(check == 1){
			first_tv.setText("一键操控，长按设置");
		}
		//指示适配
		if(check == 2){
			first_btnLayoutParams.leftMargin = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.30);
			first_btn.setLayoutParams(first_btnLayoutParams);
			first_btn.setBackgroundResource(R.drawable.news);
			first_tv.setText("一键新闻");
			/*RelativeLayout.LayoutParams first_jtLayoutParams = (LayoutParams) first_jt.getLayoutParams();
			first_jtLayoutParams.setMargins((int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12), 0, 0, 0);
			first_jt.setLayoutParams(first_jtLayoutParams);*/
		}
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.first_cancle:
			/*if(check){
				MoKeyApplication.getInstance().setFirst(true);
			}else{
				MoKeyApplication.getInstance().setLongClickFirst(true);
			}*/
			switch (check) {
			case 0:
				MoKeyApplication.getInstance().setFirst(true);
				break;
			case 1:
				MoKeyApplication.getInstance().setLongClickFirst(true);
				break;
			case 2:
				MoKeyApplication.getInstance().setNewsClickFirst(true);
				break;
			}
			finish();
			break;
		}
	}
}
