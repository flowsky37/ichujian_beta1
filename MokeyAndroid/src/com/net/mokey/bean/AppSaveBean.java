package com.net.mokey.bean;

import android.graphics.drawable.Drawable;

public class AppSaveBean {
	String name;
	String pageName;
	Drawable icon;
	public boolean check = false;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
}
