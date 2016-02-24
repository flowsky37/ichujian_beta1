package com.net.mokey.application;

import java.io.Serializable;
import java.util.List;

import com.net.mokey.bean.AppSaveBean;

public class MoKeyManager implements Serializable {
	private static class SingletonHolder {
		/**
		 *
		 */
	static final MoKeyManager INSTANCE = new MoKeyManager();
	}

	public static MoKeyManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 
	 */
	private Object readResolve() {
		return getInstance();
	}
	List<AppSaveBean> appBeans;
	public List<AppSaveBean> getAppBeans() {
		return appBeans;
	}

	public void setAppBeans(List<AppSaveBean> appBeans) {
		this.appBeans = appBeans;
	}
	
}
