package com.net.mokey.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.impl.auth.UnsupportedDigestAlgorithmException;

import android.util.Log;

/**
 * MD5
 * 
 * @author liu
 * 
 */
public class MD5 {

	/**
	 * ʹ��MD5��ԭ�Ľ��м���
	 * 
	 * @param value
	 *            ԭ��
	 * @return MD5���ܺ�
	 */
	public static String digest(String value) {
		StringBuilder sb = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(value.getBytes());
			sb = new StringBuilder();
			for (byte b : result) {
				String hexString = Integer.toHexString(b & 0xFF);
				if (hexString.length() == 1) {
					sb.append("0" + hexString);// 0~F
				} else {
					sb.append(hexString);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	private static final String LOG_TAG = "MD5";
	private static final String ALGORITHM = "MD5";

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static MessageDigest sDigest;

	static {
		try {
			sDigest = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			Log.e(LOG_TAG, "Get MD5 Digest failed.");
			throw new UnsupportedDigestAlgorithmException(ALGORITHM, e);
		}
	}

	private MD5() {
	}

	final public static String encode(String source) {
		if (source == null || source.equals("")) {
			return "";
		}
		byte[] btyes = source.getBytes();
		byte[] encodedBytes = sDigest.digest(btyes);

		return Utility.hexString(encodedBytes);
	}

	public static String signatureMD5(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if (str != null) {
				digest.update(str.getBytes());
			}
			return toHexString(digest.digest());
		} catch (Exception e) {
			return "";
		}
	}

	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

}
