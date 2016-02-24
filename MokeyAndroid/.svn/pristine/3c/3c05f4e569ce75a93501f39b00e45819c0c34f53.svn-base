package com.net.mokey.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.bean.AppSaveBean;
import com.net.mokey.bean.ShowAppBean;
import com.net.mokeyandroid.R;

public class ShowAppActivity extends Activity {
	GridView show_app_gv;
	TextView showapp_tv_bg;
	List<AppSaveBean> appSaveBeans;
	RelativeLayout showapp_rl;
	List<ShowAppBean> showAppBeans;
	List<AppSaveBean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		appSaveBeans.clear();
		showAppBeans.clear();
		appSaveBeans = MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().NINE_TABLE_NAME);
		initDta();
	}

	private void init(){
		setContentView(R.layout.showapp);
		show_app_gv = (GridView) findViewById(R.id.show_app_gv);
		showapp_tv_bg = (TextView) findViewById(R.id.showapp_tv_bg);
		showapp_rl = (RelativeLayout) findViewById(R.id.showapp_rl);
		showAppBeans = new ArrayList<ShowAppBean>();
		showapp_tv_bg.getBackground().setAlpha(100);
		appSaveBeans = new ArrayList<AppSaveBean>();
		RelativeLayout.LayoutParams showapp_rlLayoutParams = (LayoutParams) showapp_rl.getLayoutParams();
		showapp_rlLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.8);
		showapp_rlLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]);
		showapp_rl.setLayoutParams(showapp_rlLayoutParams);
		showapp_tv_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		show_app_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(showAppBeans.get(arg2).isCheck()){
					try {
						MoKeyApplication.getInstance().startApp(ShowAppActivity.this, appSaveBeans.get(arg2).getPageName());
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(ShowAppActivity.this, "应用程序已被卸载，请到膜键重新选择", 1).show();
					}
				}else{
					Intent in = new Intent(ShowAppActivity.this,AllAppS.class);
					in.putExtra("num", MoKeyApplication.getInstance().getClick());
					startActivity(in);
				}
			}
		});
	}
	private void initDta(){
		
		if(appSaveBeans.size()==9){
			for(AppSaveBean appSaveBean : appSaveBeans){
				ShowAppBean appBean = new ShowAppBean();
				appBean.setCheck(true);
				appBean.setName(appSaveBean.getName());
				appBean.setPageName(appSaveBean.getPageName());
				appBean.setDrawable(appSaveBean.getIcon());
				showAppBeans.add(appBean);
			}
		}else if(appSaveBeans.size()<9){
			for(int i = 0;i<appSaveBeans.size();i++){
				ShowAppBean appBean = new ShowAppBean();
				appBean.setCheck(true);
				appBean.setName(appSaveBeans.get(i).getName());
				appBean.setPageName(appSaveBeans.get(i).getPageName());
				appBean.setDrawable(appSaveBeans.get(i).getIcon());
				showAppBeans.add(appBean);
			}
			for(int i = appSaveBeans.size();i<9;i++){
				ShowAppBean appBean = new ShowAppBean();
				appBean.setCheck(false);
				appBean.setName("添加");
				appBean.setPageName("");
				appBean.setDrawable(getResources().getDrawable(R.drawable.plus));
				showAppBeans.add(appBean);
			}
		}
	//	MoKeyApplication.getInstance().click(MoKeyApplication.getInstance().listToMap(appSaveBeans), "3");
		showAppAdapter adapter = new showAppAdapter(ShowAppActivity.this,showAppBeans);
		show_app_gv.setAdapter(adapter);
	}
	/*@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}*/
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	class showAppAdapter extends BaseAdapter{
		Context context;
		List<ShowAppBean> showAppBeans;
		
		public showAppAdapter(Context context, List<ShowAppBean> showAppBeans) {
			super();
			this.context = context;
			this.showAppBeans = showAppBeans;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return showAppBeans.size();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.big_item, null);
				holder.big_item_iv = (ImageView) convertView.findViewById(R.id.showapp_icon);
				holder.showapp_name = (TextView) convertView.findViewById(R.id.showapp_name);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			RelativeLayout.LayoutParams big_item_ivLayoutParams = (LayoutParams) holder.big_item_iv.getLayoutParams();
			big_item_ivLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
			big_item_ivLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
			holder.big_item_iv.setLayoutParams(big_item_ivLayoutParams);
			
			ShowAppBean appSaveBean = showAppBeans.get(position);
			holder.big_item_iv.setBackground(appSaveBean.getDrawable());
			holder.showapp_name.setText(appSaveBean.getName());
			return convertView;
		}
		class ViewHolder{
			ImageView big_item_iv;
			TextView showapp_name;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
