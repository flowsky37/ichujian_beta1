package com.net.mokey.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.bean.AppSaveBean;
import com.net.mokey.bean.CheckChoseBean;
import com.net.mokey.service.FloatWindowService;
import com.net.mokeyandroid.R;

public class StartAppActivity extends Activity {
	GridView gridView;
	List<AppSaveBean> appBeans;
	List<AppSaveBean> appSaveBeans;
	List<AppSaveBean> getSaveApps;
	AppsAdapter adapter;
	int appNum= 0;
	String tabName;
	String  FIELD_id;
	String PAGENAME_KEY,NAME_KEY,PICTURE_KEY;
	String name,pageName;
	Drawable drawable;
	int lastPosition = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startapplayout);
		init();
		layout();
		initData();
	}
	private void initData(){
			PAGENAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_PAGENAME;
			NAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_NAME;
			PICTURE_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_PICTURE;
		
			FIELD_id = MoKeyApplication.getInstance().getDatabaseInstance().START_FIELD_id;
			tabName = MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME;
			if(MoKeyApplication.getInstance().getChoseApp(tabName).size()==0){
				setAll();
			}else{
				lastPosition = 0;
				appNum = MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME).size();
				setChose(tabName);
			}
	}
	private void init(){
		gridView = (GridView) findViewById(R.id.getapp_lv);
		appSaveBeans = new ArrayList<AppSaveBean>();
		appBeans = new ArrayList<AppSaveBean>();
		getSaveApps= new ArrayList<AppSaveBean>();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void setAll(){
		appBeans = MoKeyApplication.getInstance().getAllApp();
		adapter = new AppsAdapter(appBeans, StartAppActivity.this);
		gridView.setAdapter(adapter);
	}
	public void setChose(String tabName){
		appSaveBeans = MoKeyApplication.getInstance().getChoseApp(tabName);
		List<AppSaveBean> list = MoKeyApplication.getInstance().getAllApp();
		List<AppSaveBean> beans = new ArrayList<AppSaveBean>();
		beans.addAll(appSaveBeans);
		for(AppSaveBean appSaveBean : appSaveBeans){
			for(AppSaveBean bean : list){
				if(!appSaveBean.getName().equals(bean.getName())&&!appSaveBean.getPageName().equals(bean.getPageName())){
					AppSaveBean saveBean = new AppSaveBean();
					saveBean.setName(bean.getName());
					saveBean.setPageName(bean.getPageName());
					saveBean.setIcon(bean.getIcon());
					beans.add(saveBean);
				}
			}
		}
		adapter = new AppsAdapter(beans, StartAppActivity.this);
		gridView.setAdapter(adapter);
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.allapp_submit:
			saveData();
			finish();
			break;
		case R.id.back:
			finish();
			break;
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
	//保存选择的app
	public void saveData(){
		List<Integer> apps = MoKeyApplication.getInstance().getApps(tabName);
		for(int j = 0;j<apps.size();j++){
			MoKeyApplication.getInstance().getDatabaseInstance().delete(apps.get(j),FIELD_id,tabName);
		}
		if(name==null||pageName == null||drawable==null){
			return;
		}
		MoKeyApplication.getInstance().getDatabaseInstance()
		.insert(PAGENAME_KEY,PICTURE_KEY,NAME_KEY,tabName,pageName,name,drawable);
		Intent in = new Intent(StartAppActivity.this,FloatWindowService.class);
		startService(in);
		MoKeyApplication.getInstance().setClick(0);
		MoKeyApplication.getInstance().getAcache().put("click", "0");
		Map<String, String> map = new HashMap<String, String>();
		map.put(name, pageName);
		MoKeyApplication.getInstance().click(this,map,"1","1");
	}
	//获取所有app
	public void getAllApp(){
		appBeans = MoKeyApplication.getInstance().getAllApp();
	}
	private void layout(){
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		RelativeLayout.LayoutParams titleLayoutParams = (LayoutParams) title.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		title.setLayoutParams(titleLayoutParams);
	}
	class AppsAdapter extends BaseAdapter{
		List<AppSaveBean> appSaveBeans;
		Context context;
		public boolean[] checks;
		List<CheckChoseBean> checkChoseBeans;
		// 定义一个向量作为选中与否容器
		private Vector<Boolean> mImage_bs = new Vector<Boolean>();
		public AppsAdapter(List<AppSaveBean> appSaveBeans, Context context){
			super();
			this.appSaveBeans = appSaveBeans;
			this.context = context;
			this.checks = new boolean[appSaveBeans.size()];
			if(appNum>0){
				for(int i = 0;i<appNum;i++){
					this.checks[i] = true;
					mImage_bs.add(true);
				}
				for(int i = appNum;i<appSaveBeans.size();i++){
					mImage_bs.add(false);
				}
			}else{
				for(int i = 0;i<appSaveBeans.size();i++){
					mImage_bs.add(false);
				}
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appSaveBeans.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.allappitem, null);
				holder = new ViewHolder();
				holder.allapp_icon = (ImageView) convertView.findViewById(R.id.all_app_icon_iv);
				holder.allapp_name = (TextView) convertView.findViewById(R.id.allapp_name);
				holder.allapp_select = (TextView) convertView.findViewById(R.id.allapp_check_tv);
				holder.allapp_rl = (RelativeLayout) convertView.findViewById(R.id.allapp_rl);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			RelativeLayout.LayoutParams allapp_rlLayoutParams = (LayoutParams) holder.allapp_rl.getLayoutParams();
			allapp_rlLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.19);
			allapp_rlLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.24);
			holder.allapp_rl.setLayoutParams(allapp_rlLayoutParams);
			
			
			RelativeLayout.LayoutParams allapp_iconLayoutParams = (LayoutParams) holder.allapp_icon.getLayoutParams();
			allapp_iconLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
			allapp_iconLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.15);
			holder.allapp_icon.setLayoutParams(allapp_iconLayoutParams);
			
			RelativeLayout.LayoutParams allapp_nameLayoutParams = (LayoutParams) holder.allapp_name.getLayoutParams();
			allapp_nameLayoutParams.width = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.2);
			holder.allapp_name.setLayoutParams(allapp_nameLayoutParams);
			
			final AppSaveBean appBean = appSaveBeans.get(position);
			holder.allapp_icon.setBackground(appBean.getIcon());
			holder.allapp_name.setText(appBean.getName());
			if(mImage_bs.elementAt(position)){
				holder.allapp_select.setVisibility(View.VISIBLE);
				name = appBean.getName();
				pageName = appBean.getPageName();
				drawable = appBean.getIcon();
			}else{
				holder.allapp_select.setVisibility(View.GONE);
			}
			holder.allapp_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (lastPosition != -1){
						// 取消上一次的选中状态
						mImage_bs.setElementAt(false, lastPosition);
					}
					// 直接取反即可
					mImage_bs.setElementAt(!mImage_bs.elementAt(position), position);
					lastPosition = position; // 记录本次选中的位置
					notifyDataSetChanged(); // 通知适配器进行更新
				}
			});
			return convertView;
		}
		class ViewHolder{
			ImageView allapp_icon;
			TextView allapp_name;
			TextView allapp_select;
			RelativeLayout allapp_rl;
		}
	}
}
