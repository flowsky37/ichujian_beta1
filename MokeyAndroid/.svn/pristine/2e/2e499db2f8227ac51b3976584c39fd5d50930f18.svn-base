package com.net.mokey.common;


/**************************************************************************************
 * [Project]
 *       MyProgressDialog
 * [Package]
 *       com.lxd.widgets
 * [FileName]
 *       CustomProgressDialog.java
 **************************************************************************************/


import com.net.mokeyandroid.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

/********************************************************************
 * [Summary] TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要 [Remarks]
 * TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/

public class CustomProgressDialog extends Dialog {
	private Context context = null;

	// private CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public static CustomProgressDialog createDialog(Context context) {
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		View v = View.inflate(context, R.layout.customprogressdialog, null);
		ImageView imageView = (ImageView) v.findViewById(R.id.loadingImageView);
		Animation ani = AnimationUtils.loadAnimation(context,R.anim.progress_round);
		ani.setInterpolator(new LinearInterpolator());
		imageView.startAnimation(ani);
		customProgressDialog.setContentView(v);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {

	}

	@Override
	public void dismiss() {
		try {
			super.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public void setMessage(String strMessage) {
		TextView tvMsg = (TextView) findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

	}
}