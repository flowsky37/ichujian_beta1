package com.net.mokey.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class SettingActivity extends Activity implements OnClickListener {
	TextView setting_switch_tv, setting_update_tv, setting_show_update_tv;
	TextView setting_feedback_tv, setting_about_tv;
	ImageView setting_switch_iv;
	TextView title_tv;
	RelativeLayout title_bar;
	LinearLayout back;
	ProgressDialog mSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		init();
		layout();

	}

	private void init() {
		setting_switch_tv = (TextView) findViewById(R.id.setting_switch_tv);
		setting_update_tv = (TextView) findViewById(R.id.setting_update_tv);
		setting_show_update_tv = (TextView) findViewById(R.id.setting_show_update_tv);
		setting_feedback_tv = (TextView) findViewById(R.id.setting_feedback_tv);
		setting_about_tv = (TextView) findViewById(R.id.setting_about_tv);
		setting_switch_iv = (ImageView) findViewById(R.id.setting_switch_iv);
		title_bar = (RelativeLayout) findViewById(R.id.title_bar);
		back = (LinearLayout) findViewById(R.id.back);
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setText("设置");
		setting_update_tv.setOnClickListener(this);
		setting_feedback_tv.setOnClickListener(this);
		setting_about_tv.setOnClickListener(this);
		setting_switch_iv.setOnClickListener(this);
		setting_show_update_tv.setText("版本：Beta V"
				+ MoKeyApplication.getInstance().getAppVersionName(
						SettingActivity.this));
		if (MoKeyApplication.getInstance().getAcache()
				.getAsString(SaveUtil.CLICK_SWITCH) == null) {
			setting_switch_iv.setImageResource(R.drawable.open);
		} else {
			setting_switch_iv.setImageResource(R.drawable.close);
		}
	}

	private void layout() {
		RelativeLayout.LayoutParams titleLayoutParams = (LayoutParams) title_bar
				.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.13);
		title_bar.setLayoutParams(titleLayoutParams);
		setTextviewLayout(setting_switch_tv);
		setTextviewLayout(setting_update_tv);
		setTextviewLayout(setting_feedback_tv);
		setTextviewLayout(setting_about_tv);
	}

	private void setTextviewLayout(TextView textView) {
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) textView
				.getLayoutParams();
		layoutParams.height = (int) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.15);
		textView.setLayoutParams(layoutParams);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in = null;
		switch (v.getId()) {
		case R.id.setting_switch_iv:
			if (MoKeyApplication.getInstance().getAcache()
					.getAsString(SaveUtil.CLICK_SWITCH) == null) {
				setting_switch_iv.setImageResource(R.drawable.close);
				MoKeyApplication.getInstance().getAcache()
						.put(SaveUtil.CLICK_SWITCH, "");
			} else {
				setting_switch_iv.setImageResource(R.drawable.open);
				MoKeyApplication.getInstance().getAcache()
						.remove(SaveUtil.CLICK_SWITCH);
			}
			break;
		case R.id.setting_about_tv:
			in = new Intent(SettingActivity.this, AboutActivity.class);
			startActivity(in);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.setting_feedback_tv:
			in = new Intent(SettingActivity.this, FeedBackActivity.class);
			startActivity(in);
			break;
		case R.id.setting_update_tv:
			if (!MoKeyApplication.getInstance().isConnect()) {
				Toast.makeText(SettingActivity.this, "无法连接，请检查网络连接",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// update();
			MoKeyApplication.getInstance().update(this,true,true);
			break;
		}
	}
	/**
	 * 版本更新
	 * 
	 * 
	 */
	/*
	 * private void httpGetReCommand() { RequestInfo info = new RequestInfo();
	 * info.url = ThermometerHttp.UPDATE; info.useCache = false; info.showDialog
	 * = true; info.json =
	 * "version="+MoKeyApplication.getInstance().getAppVersionName(this);
	 * RequestManager.newInstance().requestData(this, info, new IResponse() {
	 * 
	 * @Override public void handleMessage(ResponseInfo responseInfo) { // TODO
	 * Auto-generated method stub Log.e("mytag", responseInfo.getResult()); }
	 * 
	 * @Override public void handleException() { // TODO Auto-generated method
	 * stub
	 * 
	 * } }); }
	 */
	/**
	 * 版本更新
	 * 
	 * 
	 */
	/*
	 * public void update(){
	 * 
	 * RequestParams parameters = new RequestParams(); parameters.put("version",
	 * MoKeyApplication.getInstance().getAppVersionName(this));
	 * HttpUtil.get(ThermometerHttp.UPDATE, parameters, new
	 * HCKHttpResponseHandler() {
	 * 
	 * @Override public void onFinish(String url) { // TODO Auto-generated
	 * method stub super.onFinish(url); }
	 * 
	 * @Override public void onSuccess(String content) { // TODO Auto-generated
	 * method stub super.onSuccess(content); Log.e("mytag", "--成功-"+content);
	 * if(content != null){ try { final JSONObject jsonObject = new
	 * JSONObject(content); if(jsonObject.getString("yesorno").equals("Y")){
	 * DialogUtil.updateDialog(SettingActivity.this,
	 * jsonObject.getString("version"),jsonObject.getString("description"),new
	 * DialogCallback() {
	 * 
	 * @Override public void commit(String content) { // TODO Auto-generated
	 * method stub try { Intent intent = new Intent();
	 * intent.setAction("android.intent.action.VIEW"); Uri content_url =
	 * Uri.parse(jsonObject.getString("url")); intent.setData(content_url);
	 * startActivity(intent); } catch (JSONException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * @Override public void cancle() { // TODO Auto-generated method stub
	 * 
	 * } }); }else{ Toast.makeText(SettingActivity.this, "当前版本已经是最新版本",
	 * Toast.LENGTH_SHORT).show(); } } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } }
	 * 
	 * @Override public void onFailure(Throwable error, String content) { //
	 * TODO Auto-generated method stub super.onFailure(error, content);
	 * ToastUtil.showToast(SettingActivity.this, "检查更新失败，请检查网络"); } }); }
	 */
}
