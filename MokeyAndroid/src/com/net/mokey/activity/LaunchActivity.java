package com.net.mokey.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.qr_codescan.MipcaActivityCapture;
import com.net.mokey.activity.net.HCKHttpResponseHandler;
import com.net.mokey.activity.net.RequestParams;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.http.HttpUtil;
import com.net.mokey.http.ThermometerHttp;
import com.net.mokey.service.FloatWindowService;
import com.net.mokey.util.EncryptUtil;
import com.net.mokey.util.FileHelper;
import com.net.mokey.util.SaveUtil;
import com.net.mokey.util.ToastUtil;
import com.net.mokey.util.UpdateDialog;
import com.net.mokeyandroid.R;

public class LaunchActivity extends Activity {
	Handler handler;
	//定位相关
	double weidu,jingdu;
	TextView launch_version;
	TextView launch_iv;
	ImageView launch_logo;
	FileHelper fileHelper;
	private LocationClient mLocClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);
		init();
		layout();
		Intent in = new Intent(LaunchActivity.this,FloatWindowService.class);
		startService(in);
		setLocationOption();
		mLocClient.start();
		appStart();
		//location();
		/*new Handler().postDelayed(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				if(MoKeyApplication.getInstance().getQrNum().equals("")){
					Intent in = new Intent(LaunchActivity.this,QrMain.class);
					startActivity(in);
				}else{
					Intent in = new Intent(LaunchActivity.this,MainActivity.class);
					startActivity(in);
				}
			}
		}, 3000);
		finish();*/
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//进入扫描二维码页面
				if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM)==null){
					Intent in = new Intent(LaunchActivity.this,MipcaActivityCapture.class);
					startActivity(in);
				}else{
					Intent in = new Intent(LaunchActivity.this,MainActivity.class);
					startActivity(in);
				}
				finish();
			}
		};
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub\
				handler.sendEmptyMessage(0);
			}
		}, 2000);
	}
	
	public void init(){
		launch_logo = (ImageView) findViewById(R.id.launch_logo);
		launch_version = (TextView) findViewById(R.id.launch_version);
		launch_version.setText("版本：Beta V"+MoKeyApplication.getInstance().getAppVersionName(this));
		fileHelper = new FileHelper(this);
		Log.e("mytag", fileHelper.readSDFile(SaveUtil.QRNUM_FILE_NAME)+"----qrnum--");
		mLocClient = ((MoKeyApplication)getApplication()).mLocationClient;
		/*Animation animation = AnimationUtils.loadAnimation(LaunchActivity.this, R.anim.sircle);
		LinearInterpolator lin = new LinearInterpolator();
		animation.setInterpolator(lin);
		launch_logo.setAnimation(animation);*/
		
		//保存激活码
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM)==null){
			if(!fileHelper.readSDFile(SaveUtil.QRNUM_FILE_NAME).equals("")&&fileHelper.readSDFile(SaveUtil.QRNUM_FILE_NAME)!=null){
				MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, EncryptUtil.toStringHex(fileHelper.readSDFile(SaveUtil.QRNUM_FILE_NAME)));
				//fileHelper.writeSDFile(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM));
			}
		}else{
			fileHelper.deleteSDFile(SaveUtil.QRNUM_FILE_NAME);
			fileHelper.writeSDFile(SaveUtil.QRNUM_FILE_NAME,EncryptUtil.toHexString(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM)));
		}
		//保存uid
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID)==null){
			if(!fileHelper.readSDFile(SaveUtil.USERID_FILE_NAME).equals("")&&fileHelper.readSDFile(SaveUtil.USERID_FILE_NAME)!=null){
				MoKeyApplication.getInstance().getAcache().put(SaveUtil.UID, EncryptUtil.toStringHex(fileHelper.readSDFile(SaveUtil.USERID_FILE_NAME)));
				//fileHelper.writeSDFile(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM));
			}
		}else{
			fileHelper.deleteSDFile(SaveUtil.USERID_FILE_NAME);
			fileHelper.writeSDFile(SaveUtil.USERID_FILE_NAME,EncryptUtil.toHexString(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.QR_NUM)));
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		/*Rect localRect = new Rect();
	    getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
		MoKeyApplication.getInstance().bar_width = localRect.width();
		MoKeyApplication.getInstance().bar_height = localRect.top;*/
		
	}
	//设置定位的参数
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);				//��gps
		option.setCoorType("bd09ll");		//�����������
//		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);	
		if(true)
		{
			option.setAddrType("all");
		}
		option.setScanSpan(3000);
	    option.setPoiNumber(10);
		option.disableCache(true);		
		mLocClient.setLocOption(option);
	}
	/**
	 * 首次启动
	 *
	 * 
	 */
	public void appStart(){
		/*String nettype ;
		if(MoKeyApplication.getInstance().checkNetWork(this)){
			nettype = "1";
		}else{
			nettype = "2";
		}
		RequestInfo info = new RequestInfo();
		info.url = ThermometerHttp.APPSTART;
		info.useCache = false;
		info.showDialog = false;
		info.json = "imei="+MoKeyApplication.getInstance().getImei()+"&model="+
		MoKeyApplication.getInstance().device_model+"&size="+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]+"X"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]
		+"&system="+MoKeyApplication.getInstance().version_release+"&brand="+MoKeyApplication.getInstance().device_model
		+"&nettype="+nettype+"&longitude="+MoKeyApplication.getInstance().Longitude+"&latitude="+MoKeyApplication.getInstance().latitude
		+"&actiondate="+MoKeyApplication.getInstance().getTime();
		RequestManager.newInstance().requestData(this, info,new IResponse() {
			
			@Override
			public void handleMessage(ResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				if(responseInfo.getResult()!= null){
					try {
						JSONObject jsonObject = new JSONObject(responseInfo.getResult());
						if(jsonObject.getString("status").equals("Y")){
							if(!jsonObject.getString("activecode").equals("")){
								MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, jsonObject.getString("activecode"));
								fileHelper.deleteSDFile();
								fileHelper.writeSDFile(jsonObject.getString("activecode"));
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			@Override
			public void handleException() {
				// TODO Auto-generated method stub
			}
		});*/
		RequestParams parameters = new RequestParams();
		parameters.put("imei", MoKeyApplication.getInstance().getImei());
		parameters.put("model", MoKeyApplication.getInstance().device_model);
		parameters.put("size",  MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]+"X"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]);
		parameters.put("system", MoKeyApplication.getInstance().version_release);
		parameters.put("brand", MoKeyApplication.getInstance().device_model);
		if(MoKeyApplication.getInstance().checkNetWork(this)){
			parameters.put("nettype", "1");
		}else{
			parameters.put("nettype", "2");
		}
		parameters.put("longitude", MoKeyApplication.getInstance().Longitude+"");
		parameters.put("latitude", MoKeyApplication.getInstance().latitude+"");
		Log.e("mytag", MoKeyApplication.getInstance().getTime()+"   huoqushijian");
		parameters.put("actiondate", MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.APPSTART, parameters,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
						Log.e("mytag", "---launch_finish----"+url);
						
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.e("mytag", "--启动信息=====--"+content+"========");
						if(content != null){
							try {
								JSONObject jsonObject = new JSONObject(content);
								if(jsonObject.getString("status").equals("Y")){
									if(!jsonObject.getString("activecode").equals("")){
										MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, jsonObject.getString("activecode"));
										fileHelper.deleteSDFile(SaveUtil.QRNUM_FILE_NAME);
										fileHelper.writeSDFile(SaveUtil.QRNUM_FILE_NAME,EncryptUtil.toHexString(jsonObject.getString("activecode")));
										
										fileHelper.deleteSDFile(SaveUtil.USERID_FILE_NAME);
										fileHelper.writeSDFile(SaveUtil.USERID_FILE_NAME,EncryptUtil.toHexString(jsonObject.getString("uid")));
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						Log.e("mytag", "--qid 错误信息--"+content);
					}
				});
	}
	private void layout(){
		RelativeLayout.LayoutParams launch_ivLayoutParams = (LayoutParams) launch_logo.getLayoutParams();
		launch_ivLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.25);
		launch_ivLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.25);
		launch_ivLayoutParams.setMargins(0, (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.3), 0, 0);
		launch_logo.setLayoutParams(launch_ivLayoutParams);
	}
	/**
	 * 版本检测
	 */
	public void update(){
		RequestParams parameters = new RequestParams();
		parameters.put("version", MoKeyApplication.getInstance().getAppVersionName(this));
		HttpUtil.get(ThermometerHttp.UPDATE, parameters,
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
							try {
								final JSONObject jsonObject = new JSONObject(content);
								if(jsonObject.getString("yesorno").equals("Y")){
									UpdateDialog dialog = new UpdateDialog(LaunchActivity.this, jsonObject.getString("version"),jsonObject.getString("description"),jsonObject.getString("url"),"");
									dialog.show();
								}else{
									Toast.makeText(LaunchActivity.this, "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						ToastUtil.showToast(LaunchActivity.this, "检查更新失败，请检查网络");
					}
				});
	}
}
