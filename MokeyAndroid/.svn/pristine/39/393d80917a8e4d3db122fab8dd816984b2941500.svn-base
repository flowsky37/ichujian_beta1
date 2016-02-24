package com.net.mokey.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtil {
	// 将数据流转成数组的方法
	private static byte[] streamToBytes(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (java.io.IOException e) {
		}
		return os.toByteArray();
	}

	// 将Bitmap对象转换成数组的方法
	private static byte[] bitmapToBytes(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 将Bitmap压缩成PNG编码，质量为100%存储
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);// 除了PNG还有很多常见格式，如jpeg等。
		return os.toByteArray();
	}

	// Drawable —> Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ?
				Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		return bitmap;
	}
	//byte -> drawable
	public static Drawable byteToDrawable(byte[] bs){
		return new BitmapDrawable(Bytes2Bimap(bs));
	}
	//byte[] → Bitmap
	public static Bitmap Bytes2Bimap(byte[] b) {
		if(b!=null){
			 if (b.length != 0) {
		            return BitmapFactory.decodeByteArray(b, 0, b.length);
		        } else {
		            return null;
		        }
		}
		return null;
    }
	// 将drawable转换成可以用来存储的byte[]类型
	public static byte[] getPicture(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, os);
		return os.toByteArray();
	}
}
