package com.net.mokey.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.mokeyandroid.R;

public class AppInfoDialog extends Dialog implements OnClickListener {
	Activity context;
	private String name;
	private Drawable logodDrawable;
	private String size;
	private String info;
	private String packageName;

	String apkUrl;

	public AppInfoDialog(Activity context, String name, Drawable logodDrawable,
			String size, String info, String packgeName) {
		super(context, R.style.customerDialog);
		this.context = context;
		this.name = name;
		this.logodDrawable = logodDrawable;
		this.size = size;
		this.packageName = packgeName;
		this.info = info;

		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appinfo_dialog);
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(name);

		TextView tv_size = (TextView) findViewById(R.id.tv_size);
		tv_size.setText(size + "M");

		TextView tv_info = (TextView) findViewById(R.id.tv_info);
		tv_info.setText(info);

		ImageView iv_logo = (ImageView) findViewById(R.id.iv_logo);
		iv_logo.setImageDrawable(logodDrawable);

		Button btn_ok = (Button) findViewById(R.id.btn_download);
		btn_ok.setOnClickListener(this);

		Button btn_cancle = (Button) findViewById(R.id.btn_cancel);
		btn_cancle.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_download:
			dismiss();
			AppDownLoadDialog dialog = new AppDownLoadDialog(context, name,
					logodDrawable, size,packageName);
			dialog.apkUrl = apkUrl;
			dialog.show();
			break;
		case R.id.btn_cancel:
			dismiss();
			break;

		default:
			break;
		}

	}

}
