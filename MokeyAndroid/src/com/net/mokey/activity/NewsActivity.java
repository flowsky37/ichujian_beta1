package com.net.mokey.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.net.mokey.application.MoKeyApplication;
import com.net.mokey.bean.AppInfo;
import com.net.mokey.bean.AppSaveBean;
import com.net.mokey.bean.NewsBean;
import com.net.mokey.common.AppData;
import com.net.mokey.http.ThermometerHttp;
import com.net.mokey.request.IResponse;
import com.net.mokey.request.RequestInfo;
import com.net.mokey.request.RequestManager;
import com.net.mokey.request.ResponseInfo;
import com.net.mokey.util.FileService;
import com.net.mokey.util.SaveUtil;
import com.net.mokeyandroid.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

//新闻设置界面
public class NewsActivity extends Activity implements OnClickListener {
	LinearLayout back;
	List<NewsBean> getSaveApps;
	ListView news_yaz_lv, news_tj_lv;
	private int remindPosition = 0;
	private String savePackageName = "";
	YazAdapter adapter;
	List<AppSaveBean> list;
	List<AppSaveBean> listRecord;
	TextView news_yaz, news_yaz_no;
	ImageView news_first_iv;
	RelativeLayout news_yaz_rl;
	TjAdapter tjAdapter;
	ScrollView news_sc;
	List<AppInfo> infosRecommend;

	AppSaveBean maybeIntsalled;
	private IResponse response = new IResponse() {

		@Override
		public void handleMessage(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub
			Log.i("jsonTest", "---->" + responseInfo.getResult());
			infosRecommend = responseInfo.getAppInfos();
			Log.i("jsonObjectTest", "---->" + infosRecommend.toString());
			updateRecommend();
			// tjAdapter = new TjAdapter(infosRecommend, NewsActivity.this);
			// news_tj_lv.setAdapter(tjAdapter);
		}

		@Override
		public void handleException() {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newslayout);
		final float scale = getResources().getDisplayMetrics().density;
		Log.i("scale", "---->" + scale + "");
		init();
		layout();
		initData();
		boolean isConnect = MoKeyApplication.getInstance().isConnect();
		if (!isConnect) {
			TextView tv_nowifi = (TextView) findViewById(R.id.tv_nowifi);
			tv_nowifi.setVisibility(View.VISIBLE);

		} else {
			httpGetReCommand();
		}
	}

	private void httpGetReCommand() {
		RequestInfo info = new RequestInfo();
		info.url = ThermometerHttp.GOODNEWS;
		info.useCache = false;
		info.showDialog = false;
		RequestManager.newInstance().requestData(this, info, response);
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		if (!MoKeyApplication.getInstance().getNewsClickFirst()) {
			Intent in = new Intent(this, FirstActivity.class);
			in.putExtra("first", 2);
			startActivity(in);
		}
		remindPosition = MoKeyApplication.getInstance().getNewsSelect();

		// if(savePackageName.equals("")){
		// remindPosition=-1;
		// }else
		back = (LinearLayout) findViewById(R.id.back);
		news_sc = (ScrollView) findViewById(R.id.news_sc);
		news_sc.scrollTo(0, 0);
		news_first_iv = (ImageView) findViewById(R.id.news_first_iv);
		news_yaz_lv = (ListView) findViewById(R.id.news_yaz_lv);
		news_tj_lv = (ListView) findViewById(R.id.news_tj_lv);
		news_yaz = (TextView) findViewById(R.id.about_app_logo);
		news_yaz_rl = (RelativeLayout) findViewById(R.id.news_yaz_rl);
		news_yaz_no = (TextView) findViewById(R.id.news_yaz_no);
		back.setOnClickListener(this);
		news_first_iv.setOnClickListener(this);
		news_yaz_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				remindPosition = arg2;
				adapter.notifyDataSetChanged();
				MoKeyApplication.getInstance().setNewsClick(
						list.get(arg2).getPageName());
				MoKeyApplication
						.getInstance()
						.getAcache()
						.put(SaveUtil.NEWS_PAGENAME,
								list.get(arg2).getPageName());
				MoKeyApplication.getInstance().getAcache()
						.put(SaveUtil.NEWS_NAME, list.get(arg2).getName());
				// MoKeyApplication.getInstance().getAcache()
				// .put(SaveUtil.NEWS_PACKAGE_SAVE,
				// list.get(arg2).getPageName());
				SharedPreferences share = getSharedPreferences("news",
						MODE_PRIVATE);
				Editor editor = share.edit();
				editor.putString("packageName", adapter.appBeans.get(arg2)
						.getPageName());
				editor.commit();
			}
		});
	}

	/**
	 * 适配
	 */
	private void layout() {
		RelativeLayout title = (RelativeLayout) findViewById(R.id.title_bar);
		LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) title
				.getLayoutParams();
		titleLayoutParams.height = (int) (MoKeyApplication.getInstance()
				.getDisplayHightAndWightPx()[1] * 0.13);
		title.setLayoutParams(titleLayoutParams);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		try {
			getSaveApps = JSON.parseObject(MoKeyApplication.getInstance()
					.copJson(this, "mokey_json.json"),
					new TypeReference<ArrayList<NewsBean>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		list = new ArrayList<AppSaveBean>();
		List<AppSaveBean> appBeans = MoKeyApplication.getInstance().getAllApp();
		for (NewsBean appSaveBean : getSaveApps) {
			for (int i = 0; i < appBeans.size(); i++) {
				if (appSaveBean.getPageName().equals(
						appBeans.get(i).getPageName())) {
					list.add(appBeans.get(i));
				}
			}
		}
		if (list.size() == 0) {
			// RelativeLayout.LayoutParams news_yaz_rlLayoutParams =
			// (LayoutParams) news_yaz_rl
			// .getLayoutParams();
			// news_yaz_rlLayoutParams.height = (int) (MoKeyApplication
			// .getInstance().getDisplayHightAndWightPx()[1] * 0.23);
			// news_yaz_rl.setLayoutParams(news_yaz_rlLayoutParams);
			news_yaz_no.setVisibility(View.VISIBLE);
			news_yaz_lv.setVisibility(View.GONE);
			findViewById(R.id.news_yaz_lv);
		} else {
			news_yaz_no.setVisibility(View.GONE);
			news_yaz_lv.setVisibility(View.VISIBLE);
			MoKeyApplication.getInstance().setNewsClick(
					list.get(0).getPageName());
			adapter = new YazAdapter(list, NewsActivity.this);
			SharedPreferences share = getSharedPreferences("news", MODE_PRIVATE);
			String savePackageName = share.getString("packageName", "");
			remindPosition = adapter.getExistObject(savePackageName);
			news_yaz_lv.setAdapter(adapter);
		}
		listRecord = list;

	}

	/**
	 * 刷新界面
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (tjAdapter != null) {
			tjAdapter.notifyDataSetChanged();
		}
		initData();
		// updateInstalled();
		if (infosRecommend != null) {
			updateRecommend();
		}
	}

	/**
	 * 推荐数据更新
	 */
	public void updateRecommend() {
		List<AppInfo> infosFilter = new ArrayList<AppInfo>();
		if (adapter != null) {
			for (int i = 0; i < infosRecommend.size(); i++) {
				Log.i("cout", "---->" + infosRecommend.get(i).C_JARNAME
						+ "---->size:" + infosRecommend.size());

				AppInfo info = infosRecommend.get(i);
				if (!MoKeyApplication.getInstance().isInstalled(info.C_JARNAME)) {
					infosFilter.add(info);
				}
			}
		} else {
			infosFilter = infosRecommend;
		}
		tjAdapter = new TjAdapter(infosFilter, NewsActivity.this);
		news_tj_lv.setAdapter(tjAdapter);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.news_first_iv:
			Intent in = new Intent(this, FirstActivity.class);
			in.putExtra("first", 2);
			startActivity(in);
			break;
		}
	}

	/**
	 * 已安装的app适配器
	 * 
	 * @author lenovo
	 * 
	 */
	public class YazAdapter extends BaseAdapter {
		List<AppSaveBean> appBeans;
		Context context;

		public YazAdapter(List<AppSaveBean> appBeans, Context context) {
			super();
			this.appBeans = appBeans;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appBeans.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		// 是否存在该包名对应的app
		public boolean isExistObject(String str) {
			for (int i = 0; i < appBeans.size(); i++) {
				Log.i("isExist", "----checkApp---->" + str);
				Log.i("isExist", "----bijiaoApp---->"
						+ appBeans.get(i).getPageName());
				if (str.equals(appBeans.get(i).getPageName())) {
					return true;
				}

			}
			return false;
		}

		public int getExistObject(String str) {
			for (int i = 0; i < appBeans.size(); i++) {
				Log.i("isExist", "----checkApp---->" + str);
				Log.i("isExist", "----bijiaoApp---->"
						+ appBeans.get(i).getPageName());
				if (str.equals(appBeans.get(i).getPageName())) {
					return i;
				}

			}
			return -1;
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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.newsitemlayout, null);
				holder.logo = (ImageView) convertView
						.findViewById(R.id.news_item_logo);
				holder.name = (TextView) convertView
						.findViewById(R.id.news_item_name);
				holder.check = (ImageView) convertView
						.findViewById(R.id.news_item_iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// RelativeLayout.LayoutParams layoutParams = (LayoutParams)
			// holder.logo
			// .getLayoutParams();
			// layoutParams.width = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.15);
			// layoutParams.height = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.15);
			// holder.logo.setLayoutParams(layoutParams);

			AppSaveBean bean = appBeans.get(position);
			holder.logo.setBackground(bean.getIcon());
			holder.name.setText(bean.getName());
			if (position == remindPosition) {
				holder.check.setBackgroundResource(R.drawable.checked);
			} else {
				holder.check.setBackgroundResource(R.drawable.nocheck);
			}
			TextView tv_diver = (TextView) convertView
					.findViewById(R.id.tv_diver);
			if (position == (appBeans.size() - 1)) {
				tv_diver.setVisibility(View.INVISIBLE);
			} else {
				tv_diver.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

		class ViewHolder {
			ImageView logo;
			TextView name;
			ImageView check;
		}
	}

	private boolean isExist(String packageName) {
		for (int i = 0; i < AppData.isDownLoadingPackageApp.size(); i++) {
			String str = AppData.isDownLoadingPackageApp.get(i);
			if (str.equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 推荐的app适配器
	 * 
	 * @author lenovo
	 * 
	 */
	public class TjAdapter extends BaseAdapter {
		List<AppInfo> appBeans;
		Context context;
		public boolean[] isDownLoading;
		protected ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.bg_default)
				.showImageForEmptyUri(R.drawable.bg_default)
				.showImageOnFail(R.drawable.bg_default).cacheInMemory(true)
				.cacheOnDisc(false).displayer(new RoundedBitmapDisplayer(0))
				.build();// 配置图片加载及显示选项

		public TjAdapter(List<AppInfo> appBeans, Context context) {
			super();
			this.appBeans = appBeans;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appBeans.size();
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

		public String getAPKName(String apkUrl) {
			String apkName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);

			if (apkName != null) {
				return apkName;
			}
			return null;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final AppInfo bean = appBeans.get(position);
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.newstjitemlayout, null);
				holder.logo = (ImageView) convertView
						.findViewById(R.id.news_tj_item_logo);
				holder.name = (TextView) convertView
						.findViewById(R.id.news_tj_item_name);
				holder.check = (TextView) convertView
						.findViewById(R.id.news_item_iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// RelativeLayout.LayoutParams checkLayoutParams = (LayoutParams)
			// holder.check
			// .getLayoutParams();
			// checkLayoutParams.width = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.15);
			// checkLayoutParams.height = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.1);
			// holder.check.setLayoutParams(checkLayoutParams);
			final String apkName = getAPKName(bean.C_APPURL);
			final boolean isExist = FileService.isExistFile(apkName,
					"mokey/apk");
			if (isExist) {
				holder.check.setText("安装");

			} else {
				holder.check.setText("下载");
			}
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(NewsActivity.this, "ok", 1).show();
					if (!isExist) {
						if (!isExist(bean.C_JARNAME)) {
							Drawable drawable = holder.logo.getDrawable();
							AppInfoDialog dialog = new AppInfoDialog(
									NewsActivity.this, bean.C_NAME, drawable,
									bean.C_SIZE + "", bean.C_ABSTRACT,
									bean.C_JARNAME);
							dialog.apkUrl = bean.C_APPURL;
							dialog.show();
						} else {
							Toast.makeText(NewsActivity.this,
									"正在下载，下拉通知栏可查看进度!", 1).show();
						}
					} else {
						maybeIntsalled = new AppSaveBean();
						maybeIntsalled.setName(bean.C_NAME);
						maybeIntsalled.setPageName(bean.C_JARNAME);
						maybeIntsalled.setIcon(holder.logo.getDrawable());
						String fileName = Environment
								.getExternalStorageDirectory()
								+ "/mokey/apk/"
								+ apkName;
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(new File(fileName)),
								"application/vnd.android.package-archive");
						context.startActivity(intent);
					}
				}
			});
			// RelativeLayout.LayoutParams layoutParams = (LayoutParams)
			// holder.logo
			// .getLayoutParams();
			// layoutParams.width = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.15);
			// layoutParams.height = (int) (MoKeyApplication.getInstance()
			// .getDisplayHightAndWightPx()[1] * 0.15);
			// holder.logo.setLayoutParams(layoutParams);
			// holder.logo.setBackground(bean.getIcon());
			imageLoader.displayImage(bean.C_LOGOURL, holder.logo, options);
			holder.name.setText(bean.C_NAME);
			TextView tv_diver = (TextView) convertView
					.findViewById(R.id.tv_diver);
			if (position == (appBeans.size() - 1)) {
				tv_diver.setVisibility(View.INVISIBLE);
			} else {
				tv_diver.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

		class ViewHolder {
			ImageView logo;
			TextView name;
			TextView check;
		}
	}
}
