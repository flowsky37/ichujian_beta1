package com.net.mokey.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.net.mokey.bean.ClickBean;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;

public class ClickActivity extends Activity {
	ListView click_lv;
	List<ClickBean> clickBeans;
	private int remindPosition = -1;
	ClickAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_layout);
		init();
		layout();
		initData();
	}

	private void init() {
		click_lv = (ListView) findViewById(R.id.click_lv);
		remindPosition = MoKeyApplication.getInstance().getClick();
		if (!MoKeyApplication.getInstance().getFirst()) {
			Intent in = new Intent(this, FirstActivity.class);
			in.putExtra("first", 0);
			startActivity(in);
			// overridePendingTransition(R.anim.first_admin_in,
			// R.anim.first_admin_out);
		}
		click_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					Intent in = new Intent(ClickActivity.this,
							StartAppActivity.class);
					startActivity(in);
				} else {
					Intent in = new Intent(ClickActivity.this, AllAppS.class);
					in.putExtra("num", arg2);
					startActivity(in);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (MoKeyApplication.getInstance().getAcache()
				.getAsString(SaveUtil.CLICK) != null) {
			if (MoKeyApplication.getInstance().getAcache()
					.getAsString(SaveUtil.CLICK).equals("0")) {
				remindPosition = 0;
			} else if (MoKeyApplication.getInstance().getAcache()
					.getAsString(SaveUtil.CLICK).equals("1")) {
				remindPosition = 1;
			} else if (MoKeyApplication.getInstance().getAcache()
					.getAsString(SaveUtil.CLICK).equals("2")) {
				remindPosition = 2;
			}
		}
		adapter.notifyDataSetChanged();
	}

	private void initData() {
		clickBeans = new ArrayList<ClickBean>();
		ClickBean clickBeanStart = new ClickBean();
		clickBeanStart.setInteger(R.drawable.start);
		clickBeanStart.setTitle("一键启动");
		clickBeanStart.setContent("启动一个应用程序");
		ClickBean clickBeanSwitch = new ClickBean();
		clickBeanSwitch.setInteger(R.drawable.arrows_horizontal);
		clickBeanSwitch.setTitle("一键切换");
		clickBeanSwitch.setContent("两个应用之间一键切换");
		ClickBean clickBeanCommon = new ClickBean();
		clickBeanCommon.setInteger(R.drawable.start_switch);
		clickBeanCommon.setTitle("一键启动常用");
		clickBeanCommon.setContent("选择9个常用的应用程序");
		clickBeans.add(clickBeanStart);
		clickBeans.add(clickBeanSwitch);
		clickBeans.add(clickBeanCommon);
		adapter = new ClickAdapter(clickBeans, this);
		click_lv.setAdapter(adapter);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.first_iv:
			Intent in = new Intent(this, FirstActivity.class);
			in.putExtra("first", 0);
			startActivity(in);
			// overridePendingTransition(R.anim.first_admin_in,
			// R.anim.first_admin_out);
			break;
		}
	}

	class ClickAdapter extends BaseAdapter {
		List<ClickBean> clickBeans;
		Context context;

		public ClickAdapter(List<ClickBean> clickBeans, Context context) {
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.click_item, null);
				holder = new ViewHolder();
				holder.icon = (TextView) convertView
						.findViewById(R.id.click_item_icon);
				holder.title = (TextView) convertView
						.findViewById(R.id.click_item_title);
				holder.content = (TextView) convertView
						.findViewById(R.id.click_item_content);
				holder.click_item_rl = (RelativeLayout) convertView
						.findViewById(R.id.click_item_rl);
				holder.click_item_check_iv = (ImageView) convertView
						.findViewById(R.id.click_item_check_iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			RelativeLayout.LayoutParams click_item_rlLayoutParams = (LayoutParams) holder.click_item_rl
					.getLayoutParams();
			click_item_rlLayoutParams.height = (int) (MoKeyApplication
					.getInstance().getDisplayHightAndWightPx()[1] * 0.13);
			holder.click_item_rl.setLayoutParams(click_item_rlLayoutParams);
			ClickBean clickBean = clickBeans.get(position);
			holder.icon.setBackgroundResource(clickBean.getInteger());
			holder.title.setText(clickBean.getTitle());
			holder.content.setText(clickBean.getContent());
			if (position == remindPosition) {
				holder.click_item_check_iv
						.setBackgroundResource(R.drawable.checked);
				MoKeyApplication.getInstance().setClick(position);
			} else {
				holder.click_item_check_iv.setBackgroundResource(0);
			}
			return convertView;
		}

		class ViewHolder {
			TextView icon;
			TextView title;
			TextView content;
			ImageView click_item_check_iv;
			RelativeLayout click_item_rl;
		}
	}

	private void layout() {
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) title
				.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.13);
		title.setLayoutParams(titleLayoutParams);
	}
}
