package com.net.mokey.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RestartService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			
		return START_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		/*timer.cancel();
		timer = null;*/
		Log.e("mytag", "======kill======");
		stopForeground(false);
	}
	interface KillOneService{
		void resatart();
	}
}
