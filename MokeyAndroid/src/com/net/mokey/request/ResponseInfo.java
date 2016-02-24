package com.net.mokey.request;

import java.util.List;


import org.json.JSONObject;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.net.mokey.bean.AppInfo;

/**
 * ResponseInfo
 * 
 * @author Andlisoft<br/>
 *         网络请求的响应数据封装 2013-11-14
 */
public class ResponseInfo {
	@SuppressWarnings("unused")
	private String TAG = "ResponseInfo";
	public final static int RET_OK = 0;

	/* 返回的json字符串 */
	private String result;
	/* url，用于对比接口 */
	private String url;
	/* 标签，用于对比request */
	private String mark;
	/* 数据是否从缓存返回的 */
	private boolean fromCache = false;

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResult() {
		return result;
	}

	public boolean isFromCache() {
		return fromCache;
	}

	public void setFromCache(boolean fromCache) {
		this.fromCache = fromCache;
	}

	public void setResult(String result) {
		// consume an optional byte order mark (BOM) if it exists
		if (result != null && result.startsWith("\ufeff")) {
			result = result.substring(1);
		}
		this.result = result;
	}

	/**
	 * 获取返回码
	 * 
	 * @return int 返回码（0表示成功）
	 */
	public int getRetCode() {
		int retcode = -1;
		try {
			JSONObject mJson = new JSONObject(this.result);
			retcode = mJson.getInt("retcode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retcode;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return int 返回码（0表示成功）
	 */
	public String getErrMsg() {
		String retcode = "";
		try {
			JSONObject mJson = new JSONObject(this.result);
			retcode = mJson.getString("msg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retcode;
	}

	/**
	 * 获取列表页码
	 * 
	 * @params说明参数含义
	 * @return int 页码
	 */
	public int getPageNo() {
		return 1;
	}

	/**
	 * 获取列表的总页数
	 * 
	 * @params说明参数含义
	 * @return int 总页数
	 */
	public int getPageCount() {
		return 2;
	}

	/**
	 * 从网络返回信息中获取App列表信息
	 * 
	 * @params说明参数含义
	 * @return List<AppInfo> App列表信息
	 */
	public List<AppInfo> getAppInfos() {

		String jsonString = this.result;
		try {
			JSONObject mJson = new JSONObject(jsonString);
			String retcode = mJson.getString("status");
			if (retcode.equals("N")) {
				Log.e(TAG, "retcode = " + retcode);
				return null;//
			}

			String strData = mJson.getString("goodnews");
			List<AppInfo> infos = null;
			infos = JSON.parseArray(strData, AppInfo.class);
			if (infos != null) {
				return infos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}