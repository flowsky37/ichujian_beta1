package com.net.mokey.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.net.mokey.service.FloatWindowService;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("mytag","岁哦哦平=============");
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
				|| Intent.ACTION_USER_PRESENT.equals(intent.getAction())
				|| Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())) {
			Intent service = new Intent(context, FloatWindowService.class);
			context.startService(service);
		}
	}

}
