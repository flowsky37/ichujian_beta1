package com.net.mokey.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class UpLoad {

	/**
	 * 锟斤拷锟芥�╋拷锟斤拷��硷拷��э拷锟斤拷锟界�帮拷锟斤拷锟斤拷锟斤拷锟界�����锟斤拷锟斤拷锟界�圭�革拷锟界�癸拷锟斤拷��匡拷锟斤拷锟介
	 * ��锟斤拷���锟芥����ワ拷锟斤拷锟斤拷娴���碉拷锟芥��锟�
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static String post(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		Log.i("TAG", "---->0" + actionUrl);
		Log.i("TAG", "---->0" + " ���璁�");
		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		Log.i("TAG", "---->0" + "娴�璇�杩����url");
		conn.setReadTimeout(5 * 1000); // 缂�锟界��锟斤拷锟斤拷锟斤拷锟斤拷锟芥��锟藉��锟斤拷
		conn.setDoInput(true);// 锟斤拷锟界��姝�锟斤拷锟斤拷锟�
		conn.setDoOutput(true);// 锟斤拷锟界��姝�锟斤拷锟斤拷锟�
		conn.setUseCaches(false); // 娑�锟斤拷锟斤拷������濞�锟斤拷��э拷锟界��锟�
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		Log.i("TAG", "---->1" + " ���璁�");

		// 妫ｏ拷锟斤拷锟界��锟斤拷锟藉�硷拷锟斤拷锟斤拷缁�璇诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		Log.i("TAG", "---->2" + " ���璁�");
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟芥����碉拷���锟斤拷
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name="
						+ file.getKey() + "; filename=\""
						+ file.getValue().getName() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				// InputStream is = new FileInputStream(file.getValue());
				// byte[] buffer = new byte[1024];
				// int len = 0;
				// while ((len = is.read(buffer)) != -1) {
				// outStream.write(buffer, 0, len);
				// }
				// is.close();
				Bitmap bitmap = null;
				if (file.getValue().length() / 1024 > 1024) {
					bitmap = BitmapCompressUtils.decodeBitmapFromFileUseSize(
							file.getValue().getPath(), 7);
				} else  if(file.getValue().length() / 1024 < 1024&&file.getValue().length() / 1024>500){
					bitmap = BitmapCompressUtils.decodeBitmapFromFileUseSize(
							file.getValue().getPath(), 3);
				}else{
					bitmap = BitmapCompressUtils.decodeBitmapFromFileUseSize(
							file.getValue().getPath(), 2);
				}
				byte bitdata[] = BitmapCompressUtils.compressByte(bitmap);
				outStream.write(bitdata);
				outStream.write(LINEND.getBytes());
			}

		// ������锟斤拷缂�锟斤拷锟斤拷锟斤拷锟借��锟�
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		Log.i("TAG", "---->3" + " ���璁�");
		// 瀵帮拷锟斤拷��匡拷锟芥�达拷锟斤拷锟�
		int res = conn.getResponseCode();
		String result = "";
		Log.i("TAG", "---->4" + " ���璁�" + "杩�������涓�---->" + res);
		if (res == 200) {
			InputStream in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {

				sb2.append((char) ch);
			}
			result = sb2.toString();
		}
		outStream.close();
		conn.disconnect();
		return result;
	}

}
