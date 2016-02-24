package com.net.mokey.util;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageUtil2 {
	private static final String SDCARD_CACHE_IMG_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/schoolrun/imgcache/";
	private static final String SDCARD_PICTURE_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/schoolrun/picture/";
	private static final String TAG = "ImageUtil";

	public static void lock() {
		mAllowLoad = false;
	}

	private static final long CLEARTIME = 3 * 24 * 60 * 60 * 1000;

	private static Object lock = new Object();
	// 是否允许加载图片
	private static boolean mAllowLoad = true;

	public static void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	/**
	 * 内存图片软引用缓存
	 */
	// private static HashMap<String, SoftReference> imageCache = new
	// HashMap<String, SoftReference>();

	public static void setThumbnailView(String imageUrl,
			ImageView iv_item_image, Context context, ImageCallback callback,
			boolean b, int placeHolderImageId) {
		if (imageUrl == null) {
			return;
		}
		String md5 = ImageUtil2.md5(imageUrl);
		String cachePath = context.getCacheDir().getAbsolutePath() + "/" + md5;
		String imagePath = ImageUtil2.getCacheImgPath().concat(md5);
		if (!CommonUtil.sdCardIsAvailable()) {
			setThumbnailImage(iv_item_image, imageUrl, cachePath, callback, b,
					placeHolderImageId);
			iv_item_image.setTag(cachePath);
		} else {
			setThumbnailImage(iv_item_image, imageUrl, imagePath, callback, b,
					placeHolderImageId);
			iv_item_image.setTag(imagePath);
		}
	}

	public static void setThumbnailView(String imageUrl,
			ImageView iv_item_image, Context context, ImageCallback callback,
			boolean b, int placeHolderImageId, int pixels) {
		String md5 = ImageUtil2.md5(imageUrl);
		String cachePath = context.getCacheDir().getAbsolutePath() + "/" + md5;
		String imagePath = ImageUtil2.getCacheImgPath().concat(md5);
		if (!CommonUtil.sdCardIsAvailable()) {
			setThumbnailImage(iv_item_image, imageUrl, cachePath, callback, b,
					placeHolderImageId, pixels);
			iv_item_image.setTag(cachePath);
		} else {
			setThumbnailImage(iv_item_image, imageUrl, imagePath, callback, b,
					placeHolderImageId, pixels);
			iv_item_image.setTag(imagePath);
		}
	}

	private static void setThumbnailImage(ImageView view, String imageUrl,
			String cachePath, ImageCallback callback, boolean b,
			int placeHolderImageId, int pixels) {

		Bitmap bitmap = null;
		bitmap = ImageUtil2
				.loadThumbnailImage(cachePath, imageUrl, callback, b);
		if (bitmap == null) {//
			// 设置默认图片
			view.setImageResource(placeHolderImageId);
		} else {
			// 设置本地SD卡缓存图片
			if (pixels > 0) {
				view.setImageBitmap(ImageUtil2.toRoundCorner(bitmap, pixels));
			} else {
				view.setImageBitmap(ImageUtil2.toRoundCorner(bitmap, pixels));
			}
		}
	}

	private static void setThumbnailImage(ImageView view, String imageUrl,
			String cachePath, ImageCallback callback, boolean b,
			int placeHolderImageId) {

		Bitmap bitmap = null;
		bitmap = ImageUtil2
				.loadThumbnailImage(cachePath, imageUrl, callback, b);
		if (bitmap == null) {//
			// 设置默认图片
			view.setImageResource(placeHolderImageId);
		} else {
			// 设置本地SD卡缓存图片
			view.setImageBitmap(bitmap);
		}
	}

	/**
	 * 保存图片到SD卡
	 * 
	 * @param imagePath
	 * @param buffer
	 * @throws IOException
	 */
	public static void saveImage(String imagePath, byte[] buffer)
			throws IOException {
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}

	public static void saveImage(String imagePath, Bitmap bm) {

		if (bm == null || imagePath == null || "".equals(imagePath)) {
			return;
		}

		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			try {
				File parentFile = f.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				f.createNewFile();
				FileOutputStream fos;
				fos = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				f.delete();
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				f.delete();
			}
		}
	}

	/**
	 * 从SD卡加载图片
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		Bitmap bitmap = null;
		if (file.exists()) {
			try {
				bitmap = BitmapCompressUtils.decodeBitmapFromFileUseSize(
						imagePath, 1);
			} catch (Exception e) {
				bitmap = BitmapCompressUtils.decodeBitmapFromFileUseSize(
						imagePath, 2);
			}
			file.setLastModified(System.currentTimeMillis());
			return bitmap;
		}
		return null;
	}

	/**
	 * 从本地或者服务端异步加载缩略图
	 * 
	 * @return
	 * @param imagePath
	 *            本地缓存路径
	 * @param imgUrl
	 *            拼接后的请求路径
	 * @param callback
	 *            得到数据后的处理方法回调
	 * @throws IOException
	 */
	public static Bitmap loadThumbnailImage(final String imagePath,
			final String imgUrl, final ImageCallback callback, final boolean b) {
		// 在软链接缓存中，则返回Bitmap对象
		// if (imageCache.containsKey(imgUrl)) {
		// SoftReference reference = imageCache.get(imgUrl);
		// Bitmap bitmap = (Bitmap) reference.get();
		// if (bitmap != null) {
		// return bitmap;
		// }
		// }
		// 若软链接缓存没有
		Bitmap bitmap = null;
		bitmap = getImageFromLocal(imagePath);
		if (bitmap != null) {// 从本地加载
			return bitmap;
		} else {// 从网上加载
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.obj != null) {
						Bitmap bitmap = (Bitmap) msg.obj;
						callback.loadImage(bitmap, imagePath);
					}
				}
			};
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						if (!mAllowLoad) {
							synchronized (lock) {
								try {
									lock.wait();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						if (imgUrl != null) {
							URL url = new URL(imgUrl);
							URLConnection conn = url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.connect();
							InputStream in = conn.getInputStream();
//							BitmapFactory.Options options = new Options();
							// options.inJustDecodeBounds = true;
							// BitmapFactory.decodeStream(in,
							// new Rect(0, 0, 0, 0), options);
//							options.inSampleSize = 2;
							// options.inJustDecodeBounds = false;
							// options.inPurgeable = true;
							// options.inInputShareable = true;
							// Bitmap bitmap = BitmapFactory.decodeStream(in,
							// new Rect(0, 0, 0, 0), options);
							// in.
							byte data[] = BitmapCompressUtils
									.InputStreamTOByte(in);
							Bitmap bitmap = null;
							if (data.length / 1024 > 500) {
								bitmap = BitmapCompressUtils
										.decodeBitmapFromDataUseSize(data, 2);
							} else {
								bitmap = BitmapCompressUtils
										.decodeBitmapFromDataUseSize(data, 1);
							}
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							handler.sendMessage(msg);
							if (bitmap != null) {
								// 保存文件到sd卡
								saveImage(imagePath, bitmap);
							}
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	// 返回图片存到sd卡的路径
	public static String getCacheImgPath() {
		return SDCARD_CACHE_IMG_PATH;
	}

	public static String getPicturePath() {
		return SDCARD_PICTURE_PATH;
	}

	public static String md5(String paramString) {
		if (paramString == null || paramString.equals(""))
			return "";
		return MD5.encode(paramString);
	}

	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}

	/**
	 * 每次打开含有大量图片的activity
	 * 
	 * @param context
	 */
	public static void checkCache(final Context context) {
		new Thread() {
			public void run() {
				int state = 0;
				String cacheS = "0M";
				String cacheD = "0M";
				File sdCache = new File(getCacheImgPath());
				File cacheDir = context.getCacheDir();

				try {
					if (sdCache != null && sdCache.exists()) {
						long sdFileSize = getFileSize(sdCache);
						if (sdFileSize > 1024 * 1024 * 50) {

							long clearFileSize = clear(sdCache);
							state += 1;
							cacheS = clearFileSize + "";
						}
					}
					if (cacheDir != null && cacheDir.exists()) {
						long cacheFileSize = getFileSize(cacheDir);
						if (cacheFileSize > 1024 * 1024 * 50) {

							long clearFileSize = clear(cacheDir);
							state += 2;
							cacheD = clearFileSize + "";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	public static long clear(File cacheDir) {
		long clearFileSize = 0;
		File[] files = cacheDir.listFiles();
		if (files == null)
			return 0;
		for (File f : files) {
			if (f.isFile()) {
				if (System.currentTimeMillis() - f.lastModified() > CLEARTIME) {
					long fileSize = f.length();
					if (f.delete()) {
						clearFileSize += fileSize;
					}
				}
			} else {
				clear(f);
			}
		}
		return clearFileSize;
	}

	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}
