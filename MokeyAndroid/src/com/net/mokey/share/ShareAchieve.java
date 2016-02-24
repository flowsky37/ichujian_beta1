package com.net.mokey.share;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;

import com.net.mokeyandroid.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

public class ShareAchieve implements  ShareInterface{

	Bitmap bitmap = null;
	//MyWeiboManager myWeiboManager;
	public static String APP_ID = "wx6354f3784abf6e25";
	public static String store_id = null;
	Bitmap bitmapLocal;
	
	 
	/*public void sinalShare(final Context context, final String imageUri,
			final String content,final String store_id) {
		mWeibo = Weibo.getInstance(CONSUMER_KEY,REDIRECT_URL);
		accessToken = AccessTokenKeeper.readAccessToken(context.getApplicationContext());
		 final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				// TODO 接收消息并且去更新UI线程上的控件内容
				super.handleMessage(msg);
				if (msg.what == 0) {
					MarriageApp.getInstance().showDialog(context, "分享成功");
				}if(msg.what == 1){
					MarriageApp.getInstance().showDialog(context, "分享失败");
				}
			}
		};
		if(accessToken.getToken()!=null&&accessToken.isSessionValid()){
			System.out.println(accessToken.getToken()+"============================.......=======================================");
			if(imageUri==null){
			final Message message = new Message();
			StatusesAPI sta = new StatusesAPI(accessToken);
			sta.update(content, "", "", new RequestListener() {
				public void onIOException(IOException arg0) {
				}

				public void onError(WeiboException arg0) {
					if(arg0!=null){
						message.what = 1;
						handler.sendMessage(message);
						System.out.println(arg0.getMessage()+"===========");
					}
				}

				public void onComplete(String arg0) {
					message.what = 0;
					handler.sendMessage(message);
				}
			});
		}
		else if(imageUri!=null){
			
		StatusesAPI sta = new StatusesAPI(accessToken);
		final Message message = new Message();
		sta.uploadUrlText(content, imageUri, "", "",
				new RequestListener() {

					public void onIOException(IOException arg0) {
						System.out.println("...>" + arg0);
					}

					public void onError(WeiboException arg0) {
						System.out.println("===" + arg0);
						if(arg0!=null){
							message.what = 1;
							handler.sendMessage(message);
						}
					}

					public void onComplete(String position) {
						message.what = 0;
						handler.sendMessage(message);
					}
				});
			}
		}
		else{
	        if (accessToken.isSessionValid()) {
	            Weibo.isWifi = Utility.isWifi(context.getApplicationContext());
	        } 
	        mSsoHandler = new SsoHandler((Activity) context,mWeibo);
	        mSsoHandler.authorize(new WeiboAuthListener() {
				public void onWeiboException(WeiboException e) {
					Log.e("mytag", "认证失败222"+e.getMessage());
				}
				
				public void onError(WeiboDialogError e) {
					Log.e("mytag", "认证失败11111"+e.getMessage());
				}
				
				public void onComplete(Bundle values) {
					Log.e("mytag", "认证成功"+values);
					String token = values.getString("access_token");
		            String expires_in = values.getString("expires_in");
		            accessToken = new Oauth2AccessToken(token, expires_in);
		            if (accessToken.isSessionValid()) {
		                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
		                        .format(new java.util.Date(accessToken
		                                .getExpiresTime()));
		                try {
		                    Class sso = Class
		                            .forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
		                } catch (ClassNotFoundException e) {
		                     e.printStackTrace();
		                }
		                AccessTokenKeeper.keepAccessToken(context.getApplicationContext(),
		                		accessToken);
		                System.out.println("认证成功===================【的vndkvnekn");
		                if(imageUri==null){
		        			final Message message = new Message();
		        			StatusesAPI sta = new StatusesAPI(accessToken);
		        			sta.update(content, "", "", new RequestListener() {
		        				public void onIOException(IOException arg0) {
		        				}

		        				public void onError(WeiboException arg0) {
		        					if(arg0!=null){
		        						message.what = 1;
		        						handler.sendMessage(message);
		        						System.out.println(arg0.getMessage()+"===========");
		        					}
		        				}

		        				public void onComplete(String arg0) {
		        					message.what = 0;
		        					handler.sendMessage(message);
		        				}
		        			});
		        		}
		        		else if(imageUri!=null){
		        			
		        		StatusesAPI sta = new StatusesAPI(accessToken);
		        		final Message message = new Message();
		        		sta.uploadUrlText(content, imageUri, "", "",
		        				new RequestListener() {
		        					public void onIOException(IOException arg0) {
		        						System.out.println("...>" + arg0);
		        					}

		        					public void onError(WeiboException arg0) {
		        						System.out.println("===" + arg0);
		        						if(arg0!=null){
		        							message.what = 1;
		        							handler.sendMessage(message);
		        						}
		        					}

		        					public void onComplete(String position) {
		        						message.what = 0;
		        						handler.sendMessage(message);
		        					}
		        				});
		        			}
		            }
			      
				}
				
				public void onCancel() {
				}
			});
		}
	}*/
	public void weiXinShare(final String content, String imageurl,final String url,
			final Context context,final String title,String store_id) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, true);
		boolean isbig = api.registerApp(APP_ID);
		this.store_id = store_id;
		Log.e("mytag", content+"=========分享的内容");
		if(isbig==false){
			new AlertDialog.Builder(context)
			.setMessage("请安装微信后再使用该功能")
			.setPositiveButton("确定", null).create().show();
		}else{
		if (imageurl==null&&url==null) {
			WXTextObject ob = new WXTextObject();
			ob.text = content;
			WXMediaMessage message = new WXMediaMessage();
			message.mediaObject = ob;
			message.description = content;

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis());
			req.message = message;
			// req.scene = SendMessageToWX.Req.WXSceneTimeline;
			api.sendReq(req);
			
		} else if(imageurl!=null&&url!=null){
			/*ImageLoader.getInstance().loadImage(imageurl, new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap b) {
					bitmap = b;
					
					//ByteArrayOutputStream out1 = new ByteArrayOutputStream();		
					//b.compress(Bitmap.CompressFormat.JPEG, 100, out1);
					//Log.i("info", "----------------out1.toByteArray().length="+out1.toByteArray().length);
					
					
				}
			});*/
			Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_big);
			String urlback = url;// 收到分享的好友点击信息会跳转到这个地址去
			WXWebpageObject localWXWebpageObject = new WXWebpageObject();
			localWXWebpageObject.webpageUrl = urlback;					
			WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
			localWXMediaMessage.title = title;// 不能太长，否则微信会提示出错。
			localWXMediaMessage.description = content;
			if(getBitmapBytes(b, false)!=null)
				localWXMediaMessage.thumbData = getBitmapBytes(b, false);
			
			SendMessageToWX.Req localReq = new SendMessageToWX.Req();
			localReq.transaction = System.currentTimeMillis()+ "";
			localReq.message = localWXMediaMessage;
			//IWXAPI api = WXAPIFactory.createWXAPI(context,APP_ID, true);
			api.sendReq(localReq);
		}
		}
	}

	private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
//		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
//		Canvas localCanvas = new Canvas(localBitmap);
//		int i;
//		int j;
//		if (bitmap.getHeight() > bitmap.getWidth()) {
//			i = bitmap.getWidth();
//			j = bitmap.getWidth();
//		} else {
//			i = bitmap.getHeight();
//			j = bitmap.getHeight();
//		}
//		while (true) {
//			localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
//					80, 80), null);
//			if (paramBoolean)
//				bitmap.recycle();
//			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
//			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
//					localByteArrayOutputStream);
//			localBitmap.recycle();
//			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
//			try {
//				localByteArrayOutputStream.close();
//				return arrayOfByte;
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//			i = bitmap.getHeight();
//			j = bitmap.getHeight();
//		}
		
//		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
//		Canvas localCanvas = new Canvas(localBitmap);
//		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
//		while (true) {
//			localCanvas.drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(0, 0,width, height/4*3), null);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 100,out);
////			localBitmap.recycle();
//			byte[] arrayOfByte = out.toByteArray();
//			if(arrayOfByte.length < 32768){
//				try {
//					out.close();				
//					return arrayOfByte;
//				} catch (Exception e) {
//					e.printStackTrace();
//				}		
//			}
//			
//			Log.i("info", "=========================="+out.toByteArray().length);
//		}
//		
		
		int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleW =0.8f;
        float scaleH =0.8f;
        Log.i("info", "=========================width="+width+";height="+height);
		while (true){
			try {						
		        matrix.postScale(scaleW, scaleH);
		        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, (int) width,(int) height, matrix, true);
		        
				ByteArrayOutputStream out = new ByteArrayOutputStream();		
				bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
				byte[] arrayOfByte = out.toByteArray();
		        out.flush();   
		        out.close();
		        Log.i("info", "=========================="+arrayOfByte.length);
		        if(arrayOfByte.length < 32768)
		        	return arrayOfByte;
		    } catch (IOException e) {   
		        e.printStackTrace();   
		    }
			scaleW =scaleW-0.1f;
			scaleH =scaleH-0.1f;
		} 
	    
	    
	}

	/*
	 * 发送到微信朋友圈
	 * 
	 * @see
	 * com.sony.store.china.http.SonyHttpRequestInterface#sendToFriends(java.lang.String
	 * , android.graphics.Bitmap, android.content.Context)
	 */
	public void weiXinShareCircleOfFriends(final String content, String imageurl,final String url,
			final Context context,final String title,String store_id) {
		// TODO Auto-generated method stub
		this.store_id = store_id;
		IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, true);
		boolean isbig = api.registerApp(APP_ID);
		if(isbig==false){
			new AlertDialog.Builder(context)
			.setMessage("您的手机没有安装微信")
			.setPositiveButton("确定", null).create().show();
		}else{
		if (imageurl==null) {
			WXTextObject ob = new WXTextObject();
			ob.text = content;

			WXMediaMessage message = new WXMediaMessage();
			message.mediaObject = ob;
			message.description = content;

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis());
			req.message = message;
			 req.scene = SendMessageToWX.Req.WXSceneTimeline;
			api.sendReq(req);
		}else if(imageurl!=null&&url!=null){		
		
		/*ImageLoader.getInstance().loadImage(imageurl, new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap b) {
				bitmap = b;
				String urlback = url;// 收到分享的好友点击信息会跳转到这个地址去
				WXWebpageObject localWXWebpageObject = new WXWebpageObject();
				localWXWebpageObject.webpageUrl = urlback;

				WXMediaMessage localWXMediaMessage = new WXMediaMessage(
						localWXWebpageObject);
				localWXMediaMessage.title = title;// 不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
				localWXMediaMessage.description = content;
				localWXMediaMessage.thumbData = getBitmapBytes(b, false);
				SendMessageToWX.Req localReq = new SendMessageToWX.Req();
				localReq.transaction = System.currentTimeMillis() + "";
				localReq.message = localWXMediaMessage;
				localReq.scene = SendMessageToWX.Req.WXSceneTimeline;
				IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, true);
				api.sendReq(localReq);
			}
		});*/
		
		}
		}
	}

//	/*
//	 * 分享本地图片到微信
//	 * 
//	 * @see
//	 * com.sony.store.china.http.SonyHttpRequestInterface#sharedToWeiXinByLocal(java
//	 * .lang.String, android.graphics.Bitmap, android.content.Context)
//	 */
//	@Override
//	public void weiXinShareLocal(String content, String fil, Context context) {
//		// TODO Auto-generated method stub
//		int h = 108;
//		int w = 108;
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		// 设置为ture只获取图片大小
//		opts.inJustDecodeBounds = true;
//		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		// 返回为空
//		BitmapFactory.decodeFile(fil, opts);
//		int width = opts.outWidth;
//		int height = opts.outHeight;
//		float scaleWidth = 0.f, scaleHeight = 0.f;
//		if (width > w || height > h) {
//			// 缩放
//			scaleWidth = ((float) width) / w;
//			scaleHeight = ((float) height) / h;
//		}
//		opts.inJustDecodeBounds = false;
//		float scale = Math.max(scaleWidth, scaleHeight);
//		opts.inSampleSize = (int) scale;
//		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
//				BitmapFactory.decodeFile(fil, opts));
//
//		bitmapLocal = Bitmap.createScaledBitmap(weak.get(), w, h, true);
//
//		String urlback = "http://www.ABC.net";// 收到分享的好友点击信息会跳转到这个地址去
//		WXWebpageObject localWXWebpageObject = new WXWebpageObject();
//		localWXWebpageObject.webpageUrl = urlback;
//		WXMediaMessage localWXMediaMessage = new WXMediaMessage(
//				localWXWebpageObject);
//		localWXMediaMessage.title = "我的应用";// 不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
//		localWXMediaMessage.description = content;
//		localWXMediaMessage.thumbData = getBitmapBytes(bitmapLocal, false);
//		SendMessageToWX.Req localReq = new SendMessageToWX.Req();
//		localReq.transaction = System.currentTimeMillis() + "";
//		localReq.message = localWXMediaMessage;
//		IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, true);
//		api.sendReq(localReq);
//	}

//	/*
//	 * 分享本地图片到微信朋友圈(non-Javadoc)
//	 * 
//	 * @see
//	 * com.sony.store.china.share.ShareInterface#shareToWeiXinFriendsByLocal(java.lang
//	 * .String, java.lang.String, android.content.Context)
//	 */
//	@Override
//	public void shareToWeiXinFriendsByLocal(String content, String fil,
//			Context context) {
//		// 将路径转成Bitmap
//		int h = 108;
//		int w = 108;
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		// 设置为ture只获取图片大小
//		opts.inJustDecodeBounds = true;
//		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		// 返回为空
//		BitmapFactory.decodeFile(fil, opts);
//		int width = opts.outWidth;
//		int height = opts.outHeight;
//		float scaleWidth = 0.f, scaleHeight = 0.f;
//		if (width > w || height > h) {
//			// 缩放
//			scaleWidth = ((float) width) / w;
//			scaleHeight = ((float) height) / h;
//		}
//		opts.inJustDecodeBounds = false;
//		float scale = Math.max(scaleWidth, scaleHeight);
//		opts.inSampleSize = (int) scale;
//		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
//				BitmapFactory.decodeFile(fil, opts));
//		bitmapLocal = Bitmap.createScaledBitmap(weak.get(), w, h, true);
//		// 发送图片和内容到微信
//		// TODO Auto-generated method stub
//		String urlback = "http://www.ABC.net";// 收到分享的好友点击信息会跳转到这个地址去
//		WXWebpageObject localWXWebpageObject = new WXWebpageObject();
//		localWXWebpageObject.webpageUrl = urlback;
//		WXMediaMessage localWXMediaMessage = new WXMediaMessage(
//				localWXWebpageObject);
//		localWXMediaMessage.title = "我的应用";// 不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
//		localWXMediaMessage.description = content;
//		localWXMediaMessage.thumbData = getBitmapBytes(bitmapLocal, false);
//		SendMessageToWX.Req localReq = new SendMessageToWX.Req();
//		localReq.transaction = System.currentTimeMillis() + "";
//		localReq.message = localWXMediaMessage;
//		localReq.scene = SendMessageToWX.Req.WXSceneTimeline;
//		IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, true);
//		api.sendReq(localReq);
//	}

//	/*
//	 * 分享本地图片到微博
//	 * 
//	 * @see com.sony.store.china.share.ShareInterface#sinalShareLocal(java.lang.String,
//	 * java.lang.String, android.content.Context)
//	 */
//	@Override
//	public void sinalShareLocal(final String content, final String fil,
//			final Context context) {
//		// TODO Auto-generated method stub
//		 final Handler handler = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					// TODO 接收消息并且去更新UI线程上的控件内容
//					super.handleMessage(msg);
//					if (msg.what == 0) {
//						Toast.makeText(context, "分享成功", 1).show();
//					}if(msg.what == 1){
//						Toast.makeText(context, "分享失败，不能发送重复的内容", 1).show();
//					}
//				}
//			};
//		/*StatusesAPI sta = new StatusesAPI(SonySettingWeboActivity.oauth);
//		final Message message = new Message();
//		sta.upload(content, fil, "", "", new RequestListener() {
//
//			@Override
//			public void onIOException(IOException arg0) {
//				// TODO Auto-generated method stub
//				System.out.println("---->" + arg0);
//			}
//
//			@Override
//			public void onError(WeiboException arg0) {
//				// TODO Auto-generated method stub
//				//System.out.println("/////" + arg0);
//				message.what = 1;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void onComplete(String arg0) {
//				// TODO Auto-generated method stub
//				message.what = 0;
//				handler.sendMessage(message);
//			}
//		});*/
//	}
}
