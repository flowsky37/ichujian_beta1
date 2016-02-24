package com.net.mokey.share;

import android.content.Context;

/**
 * 接口定义
 * HTTP请求时候的接口定义标准
 * public void requestDataWithHander(AsyncHttpResponseHandler handler,String url, RequestParams params)
 * @author aimobeans
 */
public interface ShareInterface {

	/*
	 * 分享微博
	 */
	//public void sinalShare(final Context context,final String imageUri,final String content,String store_id);
	/*
	 * 分享微博分享本地图片
	 */
//	public void sinalShareLocal(String content,String fil,Context context);
	/*
	 * 微信分享
	 * content :发送的文本内容，imageurl 图片的url，url 用户点击之后进入的url，context 上下文
	 */
	public void weiXinShare(String content,String  imageuil,String url,Context context,String title,String store_id);
	
	/*
	 * 发送到朋友圈
	 */
	public void weiXinShareCircleOfFriends(String content,String imageuri,String url,Context context,String title,String store_id);
	/*
	 * 微信分享本地图片 
	 */
	//public void  weiXinShareLocal(String content,String fil  ,Context context);
	
	/*
	 * 微信分享本地图片到朋友圈
	 */
	//public void shareToWeiXinFriendsByLocal(String content,String fil ,Context context);
}
