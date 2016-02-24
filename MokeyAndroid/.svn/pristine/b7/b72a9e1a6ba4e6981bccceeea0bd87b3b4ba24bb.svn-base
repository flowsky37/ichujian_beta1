package com.net.mokey.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class BitmapCompressUtils {

	public static Bitmap decodeBitmapFromResource(Resources resources,
			int resId, int reqWidth, int reqHeight) {
		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources, resId, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(resources, resId, options);
	}

	public static Bitmap decodeBitmapFromData(byte[] data, int reqWidth,
			int reqHeight) {
		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}
	public static Bitmap decodeBitmapFromDataUseSize(byte[] data, int size) {
		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize =size;
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	public static Bitmap decodeBitmapFromFile(String filePath, int reqWidth,
			int reqHeight) {
		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;

		options.inPurgeable = true;
		options.inInputShareable = true;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static Bitmap decodeBitmapFromFileUseSize(String filePath, int size) {
		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = size;
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		return BitmapFactory.decodeFile(filePath, options);
	}

	private static int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		// TODO Auto-generated method stub
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static void compressBmpToFile(Bitmap bmp, File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// ����ϲ����80��ʼ,
		bmp.compress(Bitmap.CompressFormat.PNG, options, baos);
		if (bmp.getByteCount() / 1024 > 1024) {
			Log.i("compress", "---->" + "ѹ���ˣ�����");
			while (baos.toByteArray().length / 1024 > 400) {
				baos.reset();
				// options -= 10;
				bmp.compress(Bitmap.CompressFormat.PNG, options, baos);
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ѹ��ͼƬ����,����һ��bitmap
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] compressByte(Bitmap image) {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// ����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��
		int options = 90;
//		image.getByteCount()
		if((image.getRowBytes() * image.getHeight())/1024>1024)
		{
			options=30;
		}
		while (baos.toByteArray().length / 1024 > 500) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ�������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10
		}
		byte[] imgbyte = baos.toByteArray();// ��ByteArrayInputStream������ͼƬ
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgbyte;
	}

	public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[1024*4];  
        int count = -1;  
        while((count = in.read(data,0,1024*4)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return outStream.toByteArray();  
    }  
}
