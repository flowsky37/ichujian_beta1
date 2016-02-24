package com.net.mokey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class MainActivity extends Activity implements OnClickListener{
	//Map<String, String> map;
	TextView main_start_click,main_start_longclick;
	TextView main_news_click,main_news_longclick;
	TextView main_games_click,main_games_longclick;
	TextView main_preferential_click,main_preferential_longclick;
	RelativeLayout main_title_rl;
	ImageView main_check_click,main_check_longclick;
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		layout();
		/*map = new HashMap<String, String>();
		map.put("name1", "package1");
		map.put("name2", "package2");
		map.put("name3", "package3");
		map.put("name4", "package4");
		map.put("name5", "package5");
		map.put("name6", "package6");*/
	//	update();
		//click(map);
		//useApp("weixin", "5.0");
	//	useonekey();
		Log.e("mytag", MoKeyApplication.getInstance().device_model+"设备型号"+MoKeyApplication.getInstance().version_sdk+"设备SDK版本"+MoKeyApplication.getInstance().version_release+"设备的系统版本");
		/*main_set_btn.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				longClickDialog.show();
				longClickDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				return false;
			}
		});*/
		/*set_gn = (Button) findViewById(R.id.set_gn);
		show_content = (TextView) findViewById(R.id.show_content);*/
		//mTencent = Tencent.createInstance("1101495826", MainActivity.this);
		MoKeyApplication.getInstance().update(this,true,true);
	}

	private void init(){
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CHECK_SWITCH)==null){
			Intent in = new Intent(this,MainFirst.class);
			startActivity(in);
			//overridePendingTransition(R.anim.first_admin_in, R.anim.first_admin_out);
		}
		main_check_click = (ImageView) findViewById(R.id.main_check_click);
		main_check_longclick = (ImageView) findViewById(R.id.main_check_longclick);
		main_title_rl = (RelativeLayout) findViewById(R.id.main_title_rl);
		main_start_click = (TextView) findViewById(R.id.main_start_click);
		main_start_longclick = (TextView) findViewById(R.id.main_start_longclick);
		main_news_click = (TextView) findViewById(R.id.main_news_click);
		main_news_longclick = (TextView) findViewById(R.id.main_news_longclick);
		main_games_click = (TextView) findViewById(R.id.main_games_click);
		main_games_longclick = (TextView) findViewById(R.id.main_games_longclick);
		main_preferential_click = (TextView) findViewById(R.id.main_preferential_click);
		main_preferential_longclick = (TextView) findViewById(R.id.main_preferential_longclick);
		main_start_click.setOnClickListener(this);
		main_start_longclick.setOnClickListener(this);
		main_news_click.setOnClickListener(this);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK)!=null){
			if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("0")){
				main_check_click.setImageResource(R.drawable.start);
			}else if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("1")){
				main_check_click.setImageResource(R.drawable.arrows_horizontal);
			}else if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.CLICK).equals("2")){
				main_check_click.setImageResource(R.drawable.start_switch);
			}
		}
		/*switch (MoKeyApplication.getInstance().getClick()) {
		case 0:
			main_check_click.setImageResource(R.drawable.start);
			break;
		case 1:
			main_check_click.setImageResource(R.drawable.arrows_horizontal);
			break;
		case 2:
			main_check_click.setImageResource(R.drawable.start_switch);
			break;
		}*/
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK)!=null){
			if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("0")){
				main_check_longclick.setImageResource(R.drawable.clean);
			}else if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("1")){
				main_check_longclick.setImageResource(R.drawable.wifi);
			}
		}
		/*switch (MoKeyApplication.getInstance().getLongClick()) {
		case 0:
			main_check_longclick.setImageResource(R.drawable.clean);
			break;
		case 1:
			main_check_longclick.setImageResource(R.drawable.wifi);
			break;
		}*/
	}

	public void onClick(View view) {
		Intent in = null;
		switch (view.getId()) {
		case R.id.main_start_click:
			//appActive("111", "106");
			in = new Intent(this,ClickActivity.class);
			startActivity(in);
			break;
		case R.id.main_about:
			in = new Intent(this,SettingActivity.class);
			startActivity(in);
			break;
		case R.id.main_start_longclick:
			in = new Intent(this,LongClickActivity.class);
			startActivity(in);
			break;
		case R.id.main_news_click:
			in = new Intent(this,NewsActivity.class);
			startActivity(in);
			break;
			/*clickDialog.show();
			clickDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);*/
		/*case R.id.set_gn:
			Intent in = new Intent(MainActivity.this,AllAppS.class);
			startActivity(in);
		break;
		case R.id.qr:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.qq_share:
			Log.e("mytag", mTencent+"------判断是否为空");
			if(mTencent!=null){
				if (!mTencent.isSessionValid()) {
					IUiListener listener = new BaseUiListener() {
						@Override
						protected void doComplete(JSONObject values) {
						    Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
							updateUserInfo();
							//updateLoginButton();
						}
					};
					mTencent.login(MainActivity.this, "all", listener);
					Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
				} else {
				    mTencent.logout(MainActivity.this);
					updateUserInfo();
					//updateLoginButton();
				}
			}
			break;
		case R.id.buttononlongclick:
			new AlertDialog.Builder(this).setItems(str, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (which == 0) {
					MoKeyApplication.getInstance().setLongClick(1);
				} else {
					MoKeyApplication.getInstance().setLongClick(2);
				}
			}
		}).create().show();
			break;*/
		}
	}
	/*private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {
				
				public void onError(UiError e) {
				}
				public void onComplete(final Object response) {
					Log.e("mytag", "====个人信息======"+response);
				}
				
				public void onCancel() {
					
				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
			
		} else {
			mUserInfo.setText("");
			mUserInfo.setVisibility(android.view.View.GONE);
			mUserLogo.setVisibility(android.view.View.GONE);
		}
	}
	private class BaseUiListener implements IUiListener {

		public void onComplete(Object response) {
			Util.showResultDialog(MainActivity.this, response.toString(), "登录成功");
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		public void onError(UiError e) {
			Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		public void onCancel() {
			Util.toastMessage(MainActivity.this, "onCancel: ");
			Util.dismissDialog();
		}
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				
				show_content.setText(bundle.getString("result"));
				//��ʾ
				//mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }*/	
	/**
	 * 版本更新
	 *
	 *
	 *//*
	public void update(){
		RequestParams parameters = new RequestParams();
		parameters.put("version", MoKeyApplication.getInstance().getAppVersionName(this));
		HttpUtil.get(ThermometerHttp.UPDATE, null,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.e("mytag", "--成功-"+content);
						if(content != null){
							
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						Log.e("mytag", "-失败-"+content);
					}
				});
	}
	public String writeJson(Map<String, String> map) throws JSONException{
		JSONArray array = new JSONArray();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("appName", entry.getKey());
	        jsonObject.put("appPackage", entry.getValue());
	        array.put(jsonObject);
	    }
		return array.toString();
	}
	*//**
	 * 长按设置记录接口
	 *  
	 * 
	 *//*
	public void useApp(String appname,String appversion){
		RequestParams parameters = new RequestParams();
		parameters.put("userid", MoKeyApplication.getInstance().getUserId());
		parameters.put("appname", "appname");
		parameters.put("appversion", "appversion");
		if(MoKeyApplication.getInstance().checkNetWork(this)){
			parameters.put("nettype", "1");
		}else{
			parameters.put("nettype", "2");
		}
		parameters.put("actiondate", MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.USEAPP, parameters,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						if(content != null){
							
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
					}
				});
	}*/
	public void openChose(int num,boolean choseType)
	{
		Intent in = new Intent(MainActivity.this,AllAppS.class);
		in.putExtra("num", num);
		in.putExtra("choseType", choseType);
		startActivity(in);
	}
	private void layout(){
		RelativeLayout.LayoutParams main_title_rlLayoutParams = (LayoutParams) main_title_rl.getLayoutParams();
		main_title_rlLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		main_title_rl.setLayoutParams(main_title_rlLayoutParams);
		textViewLayout(main_start_click,true);
		textViewLayout(main_start_longclick,true);
		textViewLayout(main_news_click,true);
		textViewLayout(main_news_longclick,true);
		textViewLayout(main_games_click,true);
		textViewLayout(main_games_longclick,true);
		textViewLayout(main_preferential_click,true);
		textViewLayout(main_preferential_longclick,true);
		
		textViewLayout((TextView) findViewById(R.id.main_set_title),false);
		textViewLayout((TextView) findViewById(R.id.main_news_title),false);
		textViewLayout((TextView) findViewById(R.id.main_games_title),false);
		textViewLayout((TextView) findViewById(R.id.main_preferential_title),false);
	}
	private void textViewLayout(TextView textView,boolean setHeigh){
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
		if(setHeigh){
			layoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
		}else{
			layoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
		}
		textView.setLayoutParams(layoutParams);
	}
}
