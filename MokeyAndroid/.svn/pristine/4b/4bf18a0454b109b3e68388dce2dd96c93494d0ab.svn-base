package com.net.mokey.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileUtil {

	public static boolean saveFileToSDCard(String fileName, String dirName,
			byte[] data) {
		// TODO Auto-generated method stub

		boolean flag = false;

		String state = Environment.getExternalStorageState();

		FileOutputStream outputStream = null;

		File root = Environment.getExternalStorageDirectory();
		Log.i("FileService", "--->" + root.getAbsolutePath());
		if (state.equals(Environment.MEDIA_MOUNTED)) {

			File file = new File(root.getAbsolutePath() + "/mokey/" + dirName);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				outputStream = new FileOutputStream(file + "/" + fileName);
				outputStream.write(data);
				outputStream.flush();
				flag = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return flag;

	}

	public static byte[] readContentFromSdcard(String fileName, String dirName) {

		File root = Environment.getExternalStorageDirectory();
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(root.getAbsolutePath() + "/mokey/" + dirName
					+ "/" + fileName);
			if (file.exists()) {
				try {
					inputStream = new FileInputStream(file);

					int len = 0;
					byte data[] = new byte[1024];
					while ((len = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, len);

					}
					outputStream.flush();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (inputStream != null) {
							inputStream.close();
						}
						if (outputStream != null) {
							outputStream.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return outputStream.toByteArray();

	}

	public static boolean isExistFile(String fileName, String dirName) {
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root.getAbsolutePath() + "/" + dirName + "/");
		if (!file.exists()) {
			file.mkdirs();
		}
		File files[] = file.listFiles();
		if (files == null) {
			return false;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(fileName)) {
				return true;
			}
		}
		return false;

	}
}
