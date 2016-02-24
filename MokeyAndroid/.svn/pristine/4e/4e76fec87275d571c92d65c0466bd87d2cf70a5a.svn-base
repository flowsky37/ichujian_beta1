package com.net.mokey.receiver;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.service.FloatWindowService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ScreenUnlockReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("mytag", "---->监听到解锁屏幕");
		Intent service = new Intent(context, FloatWindowService.class);
		context.startService(service);

	}

}
