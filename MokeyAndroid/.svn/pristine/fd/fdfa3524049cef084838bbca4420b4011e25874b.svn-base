package com.net.mokey.util;

import java.io.Serializable;

public class OnClickUtil implements Serializable {
	private static class SingletonHolder {
		/**
		 *
		 */
	static final OnClickUtil INSTANCE = new OnClickUtil();
	}

	public static OnClickUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 
	 */
	private Object readResolve() {
		return getInstance();
	}
	
}
