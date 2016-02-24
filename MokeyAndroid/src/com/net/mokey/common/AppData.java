/*
 * 文件名
 * 包含类名列表
 * 版本信息，版本号
 * 创建日期。
 * 版权声明
 */

package com.net.mokey.common;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;


/**
 * Constants
 * 
 * @author Andlisoft<br/>
 *         全局的应用数据 2013-12-26
 */

public class AppData {
	public static List<String> isDownLoadingPackageApp = new ArrayList<String>();
	
	// 是否登录
	private static boolean isLogin;
	// paopaoId
	private static String paopaoId;

	//留言数量
    public static int Mycount;
    
    //修改完密码状态
    public static int passowrdzt;
//    //所属校区Id
//    private static String schoolId;
    
	public static int getPassowrdzt() {
		return passowrdzt;
	}

	public static void setPassowrdzt(int passowrdzt) {
		AppData.passowrdzt = passowrdzt;
	}

	public static TabActivity TabActivity;
    
    //判读登录切换状态
	public static boolean isSwitchLogin = false;
    
	public static TabActivity getTabActivity() {
		return TabActivity;
	}

	public static void setTabActivity(TabActivity tabActivity) {
		TabActivity = tabActivity;
	}

	public static int getMycount() {
		return Mycount;
	}

	public static void setMycount(int mycount) {
		Mycount = mycount;
	}
	
	// 查询登陆状态
	public static boolean isLogin() {
		return isLogin;
	}

	// 更改登录状态
	public static void setLogin(boolean isLogin) {
		AppData.isLogin = isLogin;
	}

	// 查询paopaoID
	public static String paopaoId() {
		if (paopaoId == null || paopaoId == "") {
			return "0";
		}
		return paopaoId;
	}

	// 更改paopaoID
	public static void setPaopaoId(String paopaoId) {
		AppData.paopaoId = paopaoId;
	}

	
	public static boolean isSwitchLogin() {
		return isSwitchLogin;
	}

	// 更改登录->注销,或注销->登录状态
	public static void setSwitchLogin(boolean isSwitchLogin) {
		AppData.isSwitchLogin = isSwitchLogin;
	}

	/* 渠道号 */
	private static String channelid = "";

	public static String getChannelid() {
		return channelid;
	}

	public static void setChannelid(String channelid) {
		AppData.channelid = channelid;
	}

	public static boolean isTakePhoto;
}
