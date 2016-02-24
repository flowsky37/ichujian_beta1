package com.weibo.sdk.android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.net.mokeyandroid.R;
import com.weibo.sdk.android.util.Utility;
/**
 * 用来显示用户认证界面的dialog，封装了一个webview，通过redirect地址中的参数来获取accesstoken
 * @author 
 *
 */
public class WeiboDialog extends Dialog implements android.view.View.OnClickListener{
    
	static  FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
	private String mUrl;
	private WeiboAuthListener mListener;
	private ProgressDialog mSpinner;
	private WebView weibiWeb;
	private final static String TAG = "Weibo-WebView";
	private static int theme=0;
	Window window;
	public WeiboDialog(Context context, String url, WeiboAuthListener listener) {
		super(context,theme);
		mUrl = url;
		mListener = listener;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);  
		setContentView(R.layout.weibodialog);
		window = getWindow(); //得到对话框   
      //  window.setWindowAnimations(R.style.AlterDialogAnima); //设置窗口弹出动画   
		weibiWeb = (WebView) findViewById(R.id.weibo_web);
		//layout();
		
		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");
		mSpinner.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				onBack();
				return false;
			}

		});
		setUpWebView();
	}

	protected void onBack() {
		try {
			mSpinner.dismiss();
			if (null != weibiWeb) {
				weibiWeb.stopLoading();
				weibiWeb.destroy();
			}
		} catch (Exception e) {
		}
		dismiss();
	}

	private void setUpWebView() {
		weibiWeb.setWebViewClient(new WeiboDialog.WeiboWebViewClient());
		weibiWeb.setVerticalScrollBarEnabled(false);
		weibiWeb.setHorizontalScrollBarEnabled(false);
		weibiWeb.requestFocus();
	    
	    WebSettings webSettings = weibiWeb.getSettings();
	    webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		weibiWeb.loadUrl(mUrl);
		
		
	}

	private class WeiboWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "Redirect URL: " + url);
			 if (url.startsWith("sms:")) {  //针对webview里的短信注册流程，需要在此单独处理sms协议
	                Intent sendIntent = new Intent(Intent.ACTION_VIEW);  
	                sendIntent.putExtra("address", url.replace("sms:", ""));  
	                sendIntent.setType("vnd.android-dir/mms-sms");  
	                WeiboDialog.this.getContext().startActivity(sendIntent);  
	                return true;  
	            }  
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description,
				String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(new WeiboDialogError(description, errorCode, failingUrl));
			WeiboDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d(TAG, "onPageStarted URL: " + url);
			if (url.startsWith(Weibo.redirecturl)) {
				handleRedirectUrl(view, url);
				view.stopLoading();
				WeiboDialog.this.dismiss();
				return;
			}
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.d(TAG, "onPageFinished URL: " + url);
			super.onPageFinished(view, url);
			if (mSpinner.isShowing()) {
				mSpinner.dismiss();
			}
			weibiWeb.setVisibility(View.VISIBLE);
		}

		@SuppressLint("NewApi")
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}

	}

	private void handleRedirectUrl(WebView view, String url) {
		Bundle values = Utility.parseUrl(url);

		String error = values.getString("error");
		String error_code = values.getString("error_code");

		if (error == null && error_code == null) {
			mListener.onComplete(values);
		} else if (error.equals("access_denied")) {
			// 用户或授权服务器拒绝授予数据访问权限
			mListener.onCancel();
		} else {
			if(error_code==null){
				mListener.onWeiboException(new WeiboException(error, 0));
			}
			else{
				mListener.onWeiboException(new WeiboException(error, Integer.parseInt(error_code)));
			}
			
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*switch (v.getId()) {
		case R.id.menu_back:
			this.dismiss();
			break;
		case R.id.home_back:
			//DaNingApp.getInstance().homeBack(getContext());
			Intent intent = new Intent(getContext(),MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			getContext().startActivity(intent);
			break;
		}*/
	}
}
