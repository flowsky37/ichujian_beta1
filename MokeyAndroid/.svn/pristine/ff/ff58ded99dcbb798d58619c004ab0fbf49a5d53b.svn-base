package com.net.mokey.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.net.mokey.activity.net.HCKHttpResponseHandler;
import com.net.mokey.activity.net.RequestParams;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.http.HttpUtil;
import com.net.mokey.http.ThermometerHttp;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class FeedBackActivity extends Activity implements OnClickListener{
	EditText feedback_edit;
	TextView title_tv,feedback_length;
	TextView feedback_submmit;
	ProgressDialog mSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_back);
		feedback_edit = (EditText) findViewById(R.id.feedback_edit);
		title_tv = (TextView) findViewById(R.id.title_tv);
		feedback_length = (TextView) findViewById(R.id.feedback_length);
		feedback_submmit= (TextView) findViewById(R.id.feedback_submmit);
		feedback_submmit.setOnClickListener(this);
		mSpinner = new ProgressDialog(this);
		mSpinner.setMessage("正在提交...");
		title_tv.setText("意见反馈");
		layout();
		feedback_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()>200){
					feedback_length.setText("还可输入"+(0)+"字");
					return;
				}
				feedback_length.setText("还可输入"+(200-s.length())+"字");
			}
		});
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.feedback_submmit:
			if(feedback_edit.getText().toString().trim().equals("")){
				Toast.makeText(FeedBackActivity.this, "请输入您的宝贵意见", Toast.LENGTH_SHORT).show();
				return;
			}
			boolean isConnect = MoKeyApplication.getInstance().isConnect();
			if (!isConnect) {
				Toast.makeText(FeedBackActivity.this, "无法连接，请检查网络连接",Toast.LENGTH_SHORT).show();
			} 
			if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID)!=null){
				feedBack(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.UID), feedback_edit.getText().toString().trim());
			}else{
				feedBack("0", feedback_edit.getText().toString().trim());
			}
			break;
		}
	}
	private void layout(){
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		RelativeLayout.LayoutParams titleLayoutParams = (LayoutParams) title.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		title.setLayoutParams(titleLayoutParams);
		
		RelativeLayout.LayoutParams feedback_editLayoutParams = (LayoutParams) feedback_edit.getLayoutParams();
		feedback_editLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.4);
		feedback_edit.setLayoutParams(feedback_editLayoutParams);
	}
	/**
	 * 意见反馈
	 * @param userid 用户id
	 * @param content   反馈内容
	 */
	public void feedBack(String userid,String content){
		mSpinner.show();
		RequestParams parameters = new RequestParams();
		parameters.put("userid", userid);
		parameters.put("content", content);
		parameters.put("actiondate",  MoKeyApplication.getInstance().getTime());
		HttpUtil.get(ThermometerHttp.FEEDBACK, parameters,new HCKHttpResponseHandler() {

					@Override
					public void onFinish(String url) {
						// TODO Auto-generated method stub
						super.onFinish(url);
						mSpinner.dismiss();
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.e("mytag", "======="+content);
						if(content != null){
							try {
								JSONObject jsonObject = new JSONObject(content);
								if(jsonObject.getString("status").equals("Y")){
									Toast.makeText(FeedBackActivity.this, "反馈意见成功", Toast.LENGTH_SHORT).show();
									mSpinner.dismiss();
									FeedBackActivity.this.finish();
								}else{
									Toast.makeText(FeedBackActivity.this, "反馈意见失败", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(FeedBackActivity.this, "反馈失败，请检查网络", Toast.LENGTH_SHORT).show();
					}
			});
		/*RequestInfo info = new RequestInfo();
		info.url = ThermometerHttp.FEEDBACK;
		info.method = "POST";
		info.useCache = false;
		info.showDialog = false;
		info.json = "userid="+MoKeyApplication.getInstance().getUserId()+"&content="+content+"&actiondate="+MoKeyApplication.getInstance().getTime();
		RequestManager.newInstance().requestData(this,info,new IResponse() {
			
			@Override
			public void handleMessage(ResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				if(responseInfo.getResult() != null){
					try {
						JSONObject jsonObject = new JSONObject(responseInfo.getResult());
						if(jsonObject.getString("status").equals("Y")){
							Toast.makeText(FeedBackActivity.this, "反馈意见成功", Toast.LENGTH_SHORT).show();
							FeedBackActivity.this.finish();
						}
					} catch (Exception e) {
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
	}
}
