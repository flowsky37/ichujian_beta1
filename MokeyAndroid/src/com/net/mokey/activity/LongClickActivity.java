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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.bean.LongClickBean;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class LongClickActivity extends Activity {
	ListView longclick_lv;
	List<LongClickBean> clickBeans;
	private int remindPosition = -1;
	LongClickAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longclick_layout);
		init();
		layout();
		initData();
	}
	private void init(){
		longclick_lv = (ListView) findViewById(R.id.longclick_lv);
		remindPosition = MoKeyApplication.getInstance().getLongClick();
		if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK)!=null){
			if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("0")){
				remindPosition = 0;
			}else if(MoKeyApplication.getInstance().getAcache().getAsString(SaveUtil.LONGCLICK).equals("1")){
				remindPosition = 1;
			}
		}
		if(!MoKeyApplication.getInstance().getLongClickFirst()){
			Intent in = new Intent(this,FirstActivity.class);
			in.putExtra("first", 1);
			startActivity(in);
			//overridePendingTransition(R.anim.first_admin_in, R.anim.first_admin_out);
		}
		longclick_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				remindPosition = arg2;
				adapter.notifyDataSetChanged();
			}
		});
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.first_iv:
			Intent in = new Intent(this,FirstActivity.class);
			in.putExtra("first", 1);
			startActivity(in);
			//overridePendingTransition(R.anim.first_admin_in, R.anim.first_admin_out);
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
	private  void initData(){
		clickBeans = new ArrayList<LongClickBean>();
		LongClickBean longclickClearBean = new LongClickBean();
		/*if(MoKeyApplication.getInstance().getLongClick()==0){
			longclickClearBean.setType(true);
		}else{
			longclickClearBean.setType(false);
		}*/
		longclickClearBean.setIcon(R.drawable.clean);
		longclickClearBean.setTitle("一键清理");
		LongClickBean longClickWiFiBean = new LongClickBean();
		/*if(MoKeyApplication.getInstance().getLongClick()==1){
			longClickWiFiBean.setType(true);
		}else{
			longClickWiFiBean.setType(false);
		}*/
		longClickWiFiBean.setIcon(R.drawable.wifi);
		longClickWiFiBean.setTitle("一键WiFi");
		clickBeans.add(longclickClearBean);
		clickBeans.add(longClickWiFiBean);
		adapter = new LongClickAdapter(clickBeans, this);
		longclick_lv.setAdapter(adapter);
	}
	private void layout(){
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) title.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.13);
		title.setLayoutParams(titleLayoutParams);
	}
	class LongClickAdapter extends BaseAdapter{
		List<LongClickBean> clickBeans;
		Context context;
		
		public LongClickAdapter(List<LongClickBean> clickBeans, Context context) {
			super();
			this.clickBeans = clickBeans;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return clickBeans.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.longclickitem, null);
				holder = new ViewHolder();
				holder.icon = (TextView) convertView.findViewById(R.id.longclick_item_icon);
				holder.title = (TextView) convertView.findViewById(R.id.longclick_item_title);
				holder.click_item_check = (ImageView) convertView.findViewById(R.id.longclick_item_check_iv);
				holder.longclick_item_rl = (RelativeLayout) convertView.findViewById(R.id.longclick_item_rl);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			RelativeLayout.LayoutParams longclick_item_rlLayoutParams = (LayoutParams) holder.longclick_item_rl.getLayoutParams();
			longclick_item_rlLayoutParams.height = (int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]*0.12);
			holder.longclick_item_rl.setLayoutParams(longclick_item_rlLayoutParams);
			LongClickBean clickBean = clickBeans.get(position);
			holder.icon.setBackgroundResource(clickBean.getIcon());
			holder.title.setText(clickBean.getTitle());
			/*if(clickBean.getType()){
				holder.click_item_check.setBackgroundResource(R.drawable.checked);
			}*/
			if (position == remindPosition) {
				holder.click_item_check.setBackgroundResource(R.drawable.checked);
				MoKeyApplication.getInstance().getAcache().put(SaveUtil.LONGCLICK,remindPosition+"");
			}else{
				holder.click_item_check.setBackgroundResource(R.drawable.nocheck);
			}
			return convertView;
		}
		class ViewHolder{
			TextView icon;
			TextView title;
			ImageView click_item_check;
			RelativeLayout longclick_item_rl;
		}
	}
}
