package com.net.mokey.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.net.mokey.bean.CheckChoseBean;
import com.net.mokey.bean.ImageItem;
import com.net.mokey.service.FloatWindowService;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class AllAppS extends Activity {
	GridView gridView;
	List<AppSaveBean> appBeans;
	List<AppSaveBean> appSaveBeans;
	List<AppSaveBean> getSaveApps;
	AppsAdapter adapter;
	int num;
	int appNum= 0;
	int max = 0;
	String tabName;
	String  FIELD_id;
	String PAGENAME_KEY,NAME_KEY,PICTURE_KEY;
	List<ImageItem> imageItems;
	TreeMap<String, AppSaveBean> treeMap;
	List<AppSaveBean> saveBeans;
	String pageName;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(AllAppS.this, " 最多可选择"+max+"个应用程序", 400).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getapp);
		init();
		layout();
		if(getIntent().getExtras()!=null){
			num = getIntent().getExtras().getInt("num");
		}
		initData(num);
	}
	private void initData(int num){
		switch (num) {
		case 0:
			max = 1;
			PAGENAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_PAGENAME;
			NAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_NAME;
			PICTURE_KEY = MoKeyApplication.getInstance().getDatabaseInstance().START_PICTURE;
		
			FIELD_id = MoKeyApplication.getInstance().getDatabaseInstance().START_FIELD_id;
			tabName = MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME;
			if(MoKeyApplication.getInstance().getChoseApp(tabName).size()==0){
				setAll();
			}else{
				appNum = MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().START_TABLE_NAME).size();
				setChose(tabName);
			}
			break;
		case 1:
			max = 2;
			PAGENAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_PAGENAME;
			NAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_NAME;
			PICTURE_KEY = MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_PICTURE;
		
			FIELD_id = MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_FIELD_id;
			tabName = MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_TABLE_NAME;
			if(MoKeyApplication.getInstance().getChoseApp(tabName).size()==0){
				setAll();
			}else{
				appNum = MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().SWITCH_TABLE_NAME).size();
				setChose(tabName);
			}
			break;
		case 2:
			max = 9;
			PAGENAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().NINE_PAGENAME;
			NAME_KEY = MoKeyApplication.getInstance().getDatabaseInstance().NINE_NAME;
			PICTURE_KEY = MoKeyApplication.getInstance().getDatabaseInstance().NINE_PICTURE;


			FIELD_id = MoKeyApplication.getInstance().getDatabaseInstance().NINE_FIELD_id;
			tabName = MoKeyApplication.getInstance().getDatabaseInstance().NINE_TABLE_NAME;
			if(MoKeyApplication.getInstance().getChoseApp(tabName).size()==0){
				setAll();
			}else{
				appNum = MoKeyApplication.getInstance().getChoseApp(MoKeyApplication.getInstance().getDatabaseInstance().NINE_TABLE_NAME).size();
				setChose(tabName);
			}
			break;
		}
	}
	private void init(){
		gridView = (GridView) findViewById(R.id.getapp_lv);
		appSaveBeans = new ArrayList<AppSaveBean>();
		saveBeans= new ArrayList<AppSaveBean>();
		appBeans = new ArrayList<AppSaveBean>();
		getSaveApps= new ArrayList<AppSaveBean>();
		treeMap = new TreeMap<String, AppSaveBean>();
		imageItems = new ArrayList<ImageItem>();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void setAll(){
		getAllApp();
		adapter = new AppsAdapter(appBeans, AllAppS.this,mHandler);
		gridView.setAdapter(adapter);
	}
	public void setChose(String tabName){
		appSaveBeans = MoKeyApplication.getInstance().getChoseApp(tabName);
		List<AppSaveBean> list = MoKeyApplication.getInstance().getAllApp();
		List<AppSaveBean> beans = new ArrayList<AppSaveBean>();
		beans.addAll(appSaveBeans);
		for(AppSaveBean appSaveBean : appSaveBeans){
			treeMap.put(appSaveBean.getName(), appSaveBean);
			saveBeans.add(appSaveBean);
			for(int i = 0;i<list.size();i++){
				if(appSaveBean.getName().equals(list.get(i).getName())&&appSaveBean.getPageName().equals(list.get(i).getPageName())){
					list.remove(i);
				}else{
					
				}
			}
		}
		beans.addAll(list);
		adapter = new AppsAdapter(beans, AllAppS.this,mHandler);
		gridView.setAdapter(adapter);
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.allapp_submit:
			saveData();
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
		List<AppSaveBean> appSaveBeansList = new ArrayList<AppSaveBean>();
		if(num == 1){
			if(saveBeans.size()<2){
				Toast.makeText(AllAppS.this, "请选择2个应用程序", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		if(treeMap.size()==0){
			if(apps.size()!=0){
				Toast.makeText(AllAppS.this, "请选择要启动的程序", 1).show();
			}
			return;
		}
		for(int j = 0;j<apps.size();j++){
			MoKeyApplication.getInstance().getDatabaseInstance().delete(apps.get(j),FIELD_id,tabName);
		}
		for(AppSaveBean appSaveBean : saveBeans){
			appSaveBeans.add(appSaveBean);
	        appSaveBeansList.add(appSaveBean);
	        MoKeyApplication.getInstance().getDatabaseInstance()
			.insert(PAGENAME_KEY,PICTURE_KEY,NAME_KEY,tabName,appSaveBean.getPageName(), 
			appSaveBean.getName(),appSaveBean.getIcon());
		}
		Intent in = new Intent(AllAppS.this,FloatWindowService.class);
		startService(in);
		if(num == 1){
			MoKeyApplication.getInstance().click(this,MoKeyApplication.getInstance().listToMap(appSaveBeansList),"2","1");
			MoKeyApplication.getInstance().getAcache().put(SaveUtil.SWITCH, "1");
		}else{
			MoKeyApplication.getInstance().click(this,MoKeyApplication.getInstance().listToMap(appSaveBeansList),"3","1");
		}
		MoKeyApplication.getInstance().getAcache().put("click", num+"");
		finish();
	}
	/** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, AppSaveBean> sortMapByKey(Map<String, AppSaveBean> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, AppSaveBean> sortMap = new TreeMap<String, AppSaveBean>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    } 
	/*//比较器类  
	public class MapComparator implements Comparator<String>{  
	    public int compare(String str1, String str2) {  
	        return str1.compareTo(str2);  
	    }  
	} */
	//获取所有app
	public void getAllApp(){
		/*PackageManager packageManager = getPackageManager();
		List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		for (PackageInfo packageInfo : list) {
			AppSaveBean appBean = new AppSaveBean();
			//if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {  
				ApplicationInfo applicationInfo = packageInfo.applicationInfo;
				String version = packageInfo.versionName; 
				Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager); 
				appBean.setName((String) applicationInfo.loadLabel(packageManager));
				appBean.setPageName(packageInfo.packageName);
				//appBean.setVersion(version);
				appBean.setIcon(drawable);
				appBeans.add(appBean);
        //    }
		}*/
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
		private Handler mHandler;
		List<CheckChoseBean> checkChoseBeans;
		public AppsAdapter(List<AppSaveBean> appSaveBeans, Context context,Handler mHandler) {
			super();
			this.appSaveBeans = appSaveBeans;
			this.context = context;
			this.checks = new boolean[appSaveBeans.size()];
			if(appNum>0){
				for(int i = 0;i<appNum;i++){
					this.checks[i] = true;
				}
				for(int i = appNum;i<appSaveBeans.size();i++){
					this.checks[i] = false;
				}
			}
			this.mHandler = mHandler;
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
			if(checks[position]){
				holder.allapp_select.setVisibility(View.VISIBLE);
			}else{
				holder.allapp_select.setVisibility(View.GONE);
			}
			holder.allapp_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if ((appNum) < max) {
						checks[position] = !checks[position];
						if (checks[position]) {
							holder.allapp_select.setVisibility(View.VISIBLE);
							appNum++;
							saveBeans.add(appBean);
							treeMap.put(appBean.getName(), appBean);
						} else if (!checks[position]) {
							holder.allapp_select.setVisibility(View.GONE);
							appNum--;
							treeMap.remove(appBean.getName());
							//saveBeans.remove(position);
							pageName = appBean.getPageName();
							updateSaveData(appBean.getPageName());
						}
					} else if ((appNum) >= max) {
						if (checks[position]) {
							checks[position] = !checks[position];
							holder.allapp_select.setVisibility(View.GONE);
							appNum--;
							treeMap.remove(appBean.getName());
							pageName = appBean.getPageName();
							updateSaveData(appBean.getPageName());
							//saveBeans.remove(position);
						} else {
							Message message = Message.obtain(mHandler, 0);
							message.sendToTarget();
						}
					}
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
	private void updateSaveData(String pageName){
		List<AppSaveBean> appSaveBeans = new ArrayList<AppSaveBean>();
		for(AppSaveBean appSaveBean : saveBeans){
			if(!appSaveBean.getPageName().equals(pageName)){
					appSaveBeans.add(appSaveBean);
					Log.e("mytag", appSaveBean.getPageName()+"----"+pageName);
			}
		}
		Log.e("mytag", "---保存数据--"+appSaveBeans.size());
		this.saveBeans = appSaveBeans;
	}
}
