package com.net.mokeyandroid.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	/** Called when the activity is first created. */
	private IWXAPI api;
	public static final String APP_ID = "wx38b008f83184cfd3";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(WXEntryActivity.this, APP_ID, false);
    	api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }
	public void onReq(BaseReq req) {
		// TODO Auto-generated method stub
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			//goToGetMsg();		
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			//goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		final AlertDialog.Builder build = new AlertDialog.Builder(WXEntryActivity.this);
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			build.setTitle("提示");
			new AlertDialog.Builder(WXEntryActivity.this).setTitle("提示").setMessage("分享成功")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					WXEntryActivity.this.finish();
				}
			}).create().show();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			//result = R.string.errcode_cancel;
			new AlertDialog.Builder(WXEntryActivity.this).setTitle("提示").setMessage("分享失败")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					WXEntryActivity.this.finish();
				}
			}).create().show();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			//result = R.string.errcode_deny;
			break;
		default:
			//result = R.string.errcode_unknown;
			break;
		}
	}
}
