package com.net.mokey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class Utility {

	private static final String SDCARD_CACHE_WEBDATA_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/yizhouyou/webcache/";

	private static final int MAX_NONCE = 0 + 10;

	private static final String LABEL_App_sign = "api_sign";
	private static final String LABEL_TIME = "timestamp";
	private static final String LABEL_NONCE = "nonce";
	private static final String LABEL_UID = "uid";

	private static final SecureRandom sRandom = new SecureRandom();

	private static char sHexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getCacheWebDataPath() {
		return SDCARD_CACHE_WEBDATA_PATH;
	}

	private static String getNonce() {
		byte[] bytes = new byte[MAX_NONCE / 2];
		sRandom.nextBytes(bytes);
		return hexString(bytes);
	}

	public static String hexString(byte[] source) {
		if (source == null || source.length <= 0) {
			return "";
		}

		final int size = source.length;
		final char str[] = new char[size * 2];
		int index = 0;
		byte b;
		for (int i = 0; i < size; i++) {
			b = source[i];
			str[index++] = sHexDigits[b >>> 4 & 0xf];
			str[index++] = sHexDigits[b & 0xf];
		}
		return new String(str);
	}

	private static long getTimestamp() {
		Date date = new Date();
		long i = date.getTime();
		return i;
	}

	/**
	 * ������������ת��Ϊ���ڸ�ʽ���ַ�
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String long2DateString(long time, String format) {
		if (time > 0l) {
			if (format == null || format.trim().length() == 0)
				format = "yyyy-MM-dd";

			return new SimpleDateFormat(format).format(new Date(time));
		}
		return "";
	}

	private static String getAPIsig(String key, long timestamp, String nonce,
			String uid) {
		// api_sig =
		// MD5("api_key"+@api_key+"nonce"+@nonce+"timestamp"+@timestamp)
		String result = null;
		StringBuilder builder = new StringBuilder();
		synchronized (builder) {
			builder.append(key);
			builder.append(timestamp);
			builder.append(nonce);
			builder.append(uid);
			result = MD5.encode(builder.toString());
			builder.delete(0, builder.length());
		}
		return result;
	}

	public static String getParams(String key) {
		String[] temp = key.split(":");
		long timestamp = getTimestamp();
		String nonce = getNonce();
		String api_sign = getAPIsig(key, timestamp, nonce, temp[1]);

		StringBuilder builder = new StringBuilder();
		String result = "";
		synchronized (result) {
			builder.append(String.format("&" + LABEL_UID + "=%s", temp[1]));
			builder.append(String.format("&" + LABEL_NONCE + "=%s", nonce));
			builder.append(String.format("&" + LABEL_TIME + "=%s", timestamp));
			builder.append(String
					.format("&" + LABEL_App_sign + "=%s", api_sign));
			result = builder.toString();
			builder.delete(0, builder.length());
		}
		return result;
	}

	public static void saveFile(String filePath, String file) {

		if (file == null || "".equals(file) || filePath == null
				|| "".equals(filePath)) {
			return;
		}

		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}

		try {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos;
			fos = new FileOutputStream(f);
			fos.write(file.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			f.delete();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			f.delete();
		}

	}

	public static String readFile(String filePath) {
		String path = filePath;
		String content = ""; // �ļ������ַ�
		// ���ļ�
		File file = new File(path);
		if (!file.exists()) {
			return "";
		}
		// ���path�Ǵ��ݹ����Ĳ��������һ����Ŀ¼���ж�
		if (file.isDirectory()) {
			return "";
		} else {
			try {
				InputStream instream = new FileInputStream(file);
				if (instream != null) {
					InputStreamReader inputreader = new InputStreamReader(
							instream);
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// ���ж�ȡ
					while ((line = buffreader.readLine()) != null) {
						content += line + "\n";
					}
					instream.close();
				}
			} catch (java.io.FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;

	}

	/**
	 * ��assets �ļ����л�ȡ�ļ�����ȡ���
	 * 
	 * @params˵��������
	 * @return String ˵������ֵ����
	 * @throws
	 */
	public static String getFromAssets(Context context, String fileName) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// ��ȡ�ļ����ֽ���
			int lenght = in.available();
			// ����byte����
			byte[] buffer = new byte[lenght];
			// ���ļ��е���ݶ���byte������
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ȡ�ð汾��
	public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}

	public static String getImei(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonyManager.getDeviceId();
			if (imei == null || imei.equals("0")) {
				imei = getMacAddress(context);
			}
			return imei;
		} catch (Exception e) {
			Log.e("getImei", e.getMessage());
			return "";
		}
	}

	public static String getMacAddress(Context context) {
		// ��ȡmac��ַ��
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * ��֤�ֻ��
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern pattern = Pattern
				.compile("^13\\d{9}||14\\d{9}||15[0,1,2,3,5,6,7,8,9]\\d{8}||18[0,1,2,3,5,6,7,8,9]\\d{8}$");
		Matcher m = pattern.matcher(mobiles);
		return m.matches();
	}
	/**
	 * ��֤����
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	/**
	 * ��֤QQ��
	 */
	public static boolean isQQ(String qq){
		String str = "[1-9][0-9]{4,}";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(qq);
		return m.matches();
	}
	/**
	 * ��֤�Ƿ���λС��
	 */
	public static boolean isBouble(String tixnum){
		String str = "^[0-9]+\\.[0-9]{2}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(tixnum);
		return m.matches();
	}
}
