package com.net.mokey.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.net.mokey.activity.net.HCKHttpResponseHandler;
import com.net.mokey.activity.net.RequestParams;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.http.HttpUtil;
import com.net.mokey.http.ThermometerHttp;
import com.net.mokey.util.EncryptUtil;
import com.net.mokey.util.FileHelper;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class ShowCodeActivity extends Activity implements OnClickListener{
	String str ;
	EditText show_code;//,show_code_num;
	TextView code_jh,title_tv;
	RelativeLayout relativeLayout1;
	//Button code_scanning;
	FileHelper fileHelper;
	ProgressDialog mSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcode);
		init();
		layout();
	}
	private void init(){
		show_code = (EditText) findViewById(R.id.show_code);
		code_jh= (TextView) findViewById(R.id.code_jh);
		//show_code_num = (EditText) findViewById(R.id.show_code_num);
	//	code_scanning = (Button) findViewById(R.id.code_scanning);
		title_tv = (TextView) findViewById(R.id.title_tv);
		relativeLayout1= (RelativeLayout) findViewById(R.id.relativeLayout1);
		title_tv.setText("验证激活码");
		fileHelper = new FileHelper(this);
		code_jh.setOnClickListener(this);
		mSpinner = new ProgressDialog(this);
		mSpinner.setMessage("正在激活...");
	//	code_scanning.setOnClickListener(this);
		if(getIntent().getExtras()!=null){
			str = getIntent().getExtras().getString("result");
		}
		if(str!=null){
			show_code.setText(str);
		}
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		/*case R.id.code_scanning:
			
			break;*/
		case R.id.code_jh:
			if(show_code.getText().toString().trim().equals("")){
				Toast.makeText(ShowCodeActivity.this, "激活码不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!MoKeyApplication.getInstance().isConnect()){
				Toast.makeText(ShowCodeActivity.this, "无法连接，请检查网络连接", Toast.LENGTH_SHORT).show();
				return;
			}
				/*Intent in = new Intent(ShowCodeActivity.this,MainActivity.class);
				startActivity(in);
				//MoKeyApplication.getInstance().setQrNum(show_code.getText().toString().trim());
				//MoKeyApplication.getInstance().getDatabaseInstance().insertQr(show_code.getText().toString().trim());
				MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, show_code.getText().toString().trim());
				finish();
				appActive(show_code.getText().toString().trim(), "123");
*/			
			//appActive(show_code.getText().toString().trim());	
			//MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, show_code.getText().toString().trim());
			//MoKeyApplication.getInstance().setUserId(jsonObject.getString("userid"));
			//Intent in = new Intent(ShowCodeActivity.this,MainActivity.class);
			////startActivity(in);
			//finish();
			appActive(show_code.getText().toString().trim());
			break;
		}
	}
	/**
	 * 激活
	 * @param activecode 激活码
	 * @param jobnumber 工号
	 */
	public void appActive(String activecode) {
		mSpinner.show();
		/*String nettype;
		if(MoKeyApplication.getInstance().checkNetWork(this)){
			nettype = "1";
		}else{
			nettype = "2";
		}
		RequestInfo info = new RequestInfo();
		info.useCache = false;
		info.showDialog = false;
		String str = "activecode="+activecode+"&jobnumber="+""+
		"&imei="+MoKeyApplication.getInstance().getImei()
		+"&model="+MoKeyApplication.getInstance().device_model
		+"&brand="+MoKeyApplication.getInstance().device_model
		+"&size="+ MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]+"X"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]
		+"&system="+ MoKeyApplication.getInstance().version_release
		+"&nettype="+nettype+"&longitude="+MoKeyApplication.getInstance().Longitude+"&latitude="+MoKeyApplication.getInstance().latitude
		+"&actiondate="+MoKeyApplication.getInstance().getTime();
		info.url = ThermometerHttp.APPACTIVE+str;
		RequestManager.newInstance().requestData(this, info,new IResponse() {
			
			@Override
			public void handleMessage(ResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				if(responseInfo.getResult() != null){
					try {
						JSONObject jsonObject = new JSONObject(responseInfo.getResult());
						if(jsonObject.getString("status").equals("Y")){
							fileHelper.deleteSDFile();
							fileHelper.writeSDFile(show_code.getText().toString().trim());
							MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, show_code.getText().toString().trim());
							MoKeyApplication.getInstance().setUserId(jsonObject.getString("userid"));
							Intent in = new Intent(ShowCodeActivity.this,MainActivity.class);
							startActivity(in);
							finish();
						}else{
							Toast.makeText(ShowCodeActivity.this,jsonObject.getString("info"), Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			@Override
			public void handleException() {
			}
		});*/
		RequestParams parameters = new RequestParams();
		parameters.put("activecode", activecode);
		parameters.put("jobnumber", "");
		parameters.put("imei", MoKeyApplication.getInstance().getImei());
		parameters.put("model", MoKeyApplication.getInstance().device_model);
		parameters.put("brand", MoKeyApplication.getInstance().device_model);
		parameters.put("size", MoKeyApplication.getInstance().getDisplayHightAndWightPx()[0]+"X"+MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]);
		parameters.put("system", MoKeyApplication.getInstance().version_release);
		Log.e("mytag", MoKeyApplication.getInstance().checkNetWork(this)+"panduan");
		if(MoKeyApplication.getInstance().checkNetWork(this)){
			parameters.put("nettype", "1");
		}else{
			parameters.put("nettype", "2");
		}
		parameters.put("longitude", MoKeyApplication.getInstance().Longitude+"");
		parameters.put("latitude", MoKeyApplication.getInstance().latitude+"");
		parameters.put("actiondate", MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.APPACTIVE, parameters,
				new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
						Log.e("mytag", "=finish=="+url);
						mSpinner.dismiss();
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.e("mytag", "----------"+content);
						if(content != null){
							try {
								mSpinner.dismiss();
								JSONObject jsonObject = new JSONObject(content);
								if(jsonObject.getString("status").equals("Y")){
									fileHelper.deleteSDFile(SaveUtil.QRNUM_FILE_NAME);
									fileHelper.writeSDFile(SaveUtil.QRNUM_FILE_NAME,EncryptUtil.toHexString(show_code.getText().toString().trim()));
									MoKeyApplication.getInstance().getAcache().put(SaveUtil.QR_NUM, show_code.getText().toString().trim());
									//MoKeyApplication.getInstance().setUserId(jsonObject.getString("userid"));
									MoKeyApplication.getInstance().getAcache().put(SaveUtil.UID, jsonObject.getString("userid"));
									Intent in = new Intent(ShowCodeActivity.this,MainActivity.class);
									startActivity(in);
									finish();
									if(MipcaActivityCapture.getInstance()!=null){
										MipcaActivityCapture.getInstance().finish();
									}
								}else{
									Toast.makeText(ShowCodeActivity.this,jsonObject.getString("info"), Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						mSpinner.dismiss();
						Toast.makeText(ShowCodeActivity.this, "激活失败，请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
	}
	private void layout(){
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		RelativeLayout.LayoutParams titleLayoutParams = (LayoutParams) title.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		title.setLayoutParams(titleLayoutParams);
		
		RelativeLayout.LayoutParams relativeLayout1LayoutParams = (LayoutParams) relativeLayout1.getLayoutParams();
		relativeLayout1LayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.23);
		relativeLayout1.setLayoutParams(relativeLayout1LayoutParams);
		
		RelativeLayout.LayoutParams show_codeLayoutParams = (LayoutParams) show_code.getLayoutParams();
		show_codeLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
		show_code.setLayoutParams(show_codeLayoutParams);
		
		RelativeLayout.LayoutParams code_jhLayoutParams = (LayoutParams) code_jh.getLayoutParams();
		code_jhLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		code_jh.setLayoutParams(code_jhLayoutParams);
	}
}
