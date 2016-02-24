package com.net.mokey.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

public class CommonUtil {

	public static Builder showInfoDialog(Context context, String message) {
		return showInfoDialog(context, message, "��ʾ", "ȷ��", null);
	}

	public static boolean isValidCode(String paramString) {
		String regex = "[a-zA-Z0-9]{6,20}";
		// String regex =
		// "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValidMobiNumber(String paramString) {
		String regex = "^1\\d{10}$";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if the net is connected
	 * 
	 * @param context
	 * @return true is connected
	 */
	public static boolean getNetworkStatus(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()
				|| !networkInfo.isConnected()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ���sdcard�Ƿ����
	 * 
	 * @return trueΪ���ã�����Ϊ������
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * Checks if there is enough Space on SDCard
	 * 
	 * @param updateSize
	 *            Size to Check
	 * @return True if the Update will fit on SDCard, false if not enough space
	 *         on SDCard Will also return false, if the SDCard is not mounted as
	 *         read/write
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * get the space is left over on sdcard
	 */
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Checks if there is enough Space on phone self
	 * 
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

	/**
	 * get the space is left over on phone self
	 */
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}

	/**
	 * ����ֻ�ֱ��ʴ�dpת��px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * ����ֻ�ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * �Ƿ���Ч�ֻ���룬���Ը�Ĳ�����ֻ����ض���Ӫ�̵ĺŶ�
	 * 
	 * @param mobile
	 *            ����
	 * @return �Ƿ�Ϸ��ֻ����
	 */
	public static boolean isValidMobile(String mobile) {
		/**
		 * �ֻ���� �ƶ���134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
		 * ��ͨ��130,131,132,152,155,156,185,186 ���ţ�133,1349,153,180,189
		 */
		String MOBILE = "^1(3[0-9]|5[0-35-9]|8[0-9]|7[0-9])\\d{8}$";
		/**
		 * �й��ƶ���China Mobile
		 * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
		 */
		String CM = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$";
		/**
		 * �й���ͨ��China Unicom 130,131,132,152,155,156,185,186
		 */
		String CU = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
		/**
		 * �й���ţ�China Telecom 133,1349,153,180,189
		 */
		String CT = "^1((33|53|8[09])[0-9]|349)\\d{7}$";
		/**
		 * ��½����̻���С��ͨ ��ţ�010,020,021,022,023,024,025,027,028,029 ���룺��λ���λ
		 */
		String PHS = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";

		Pattern pattern = Pattern.compile(MOBILE);
		Matcher matcher = pattern.matcher(mobile);

		return matcher.matches();
	}

	public static Builder showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);

		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		// if (localBuilder.create().isShowing()) {
		// localBuilder.show();
		// }
		return localBuilder;
	}
}
