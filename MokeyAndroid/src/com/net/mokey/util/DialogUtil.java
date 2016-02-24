package com.net.mokey.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.itf.DialogCallback;
import com.net.mokeyandroid.R;

public class DialogUtil {
	public void showViewDialog(Context context, String content,
			final DialogCallback back) {
		if (content == null)
			return;
		View view = LayoutInflater.from(context).inflate(R.layout.checkdialog_layout, null);
		final Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();

	}
	public static void feedBackDialog(final Context context,final DialogCallback back) {
			View view = LayoutInflater.from(context).inflate(R.layout.feedback_layout, null);
			final EditText editText = (EditText) view.findViewById(R.id.feeback_edit);
			Button feedback_cancle = (Button) view.findViewById(R.id.feedback_cancle);
			Button feedback_commit = (Button) view.findViewById(R.id.feedback_commit);
			
			RelativeLayout.LayoutParams editTextLayoutParams = (LayoutParams) editText.getLayoutParams();
			editTextLayoutParams.height =  (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.5);
			editText.setLayoutParams(editTextLayoutParams);
			
			RelativeLayout.LayoutParams feedback_cancleLayoutParams = (LayoutParams) feedback_cancle.getLayoutParams();
			feedback_cancleLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
			feedback_cancleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
			feedback_cancle.setLayoutParams(feedback_cancleLayoutParams);
			
			RelativeLayout.LayoutParams feedback_commitLayoutParams = (LayoutParams) feedback_commit.getLayoutParams();
			feedback_commitLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
			feedback_commitLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
			feedback_commit.setLayoutParams(feedback_commitLayoutParams);
			
			final Dialog dialog = new Dialog(context, R.style.dialog);
			dialog.setContentView(view);
			dialog.setCancelable(false);
			dialog.show();
			dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					dialog.cancel();
					return false;
				}
			});
			feedback_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			feedback_commit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(editText.getText().toString().trim().equals("")){
						Toast.makeText(context, "请输入反馈内容", Toast.LENGTH_SHORT).show();
					}else{
						back.commit(editText.getText().toString());
						dialog.cancel();
					}
				}
			});
		}
		public static void updateDialog(final Context context,String title,String content,final DialogCallback back){
			View view = LayoutInflater.from(context).inflate(R.layout.update_layout, null);
			Button update_cancle = (Button) view.findViewById(R.id.update_cancle);
			Button update_commit = (Button) view.findViewById(R.id.update_commit);
			TextView update_title = (TextView) view.findViewById(R.id.update_title);
			TextView update_content = (TextView) view.findViewById(R.id.update_content);
			update_title.setText("新版本"+title);
			update_content.setText(content);
			
			RelativeLayout.LayoutParams update_cancleLayoutParams = (LayoutParams) update_cancle.getLayoutParams();
			update_cancleLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
			update_cancleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
			update_cancle.setLayoutParams(update_cancleLayoutParams);
			
			RelativeLayout.LayoutParams update_commitLayoutParams = (LayoutParams) update_commit.getLayoutParams();
			update_commitLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
			update_commitLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
			update_commit.setLayoutParams(update_commitLayoutParams);
			
			final Dialog dialog = new Dialog(context, R.style.dialog);
			dialog.setContentView(view);
			dialog.setCancelable(false);
			dialog.show();
			dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					dialog.cancel();
					back.cancle();
					return false;
				}
			});
			update_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
					back.cancle();
				}
			});
			update_commit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
					back.commit("");
				}
			});
		}
}
