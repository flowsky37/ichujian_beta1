package com.net.mokey.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileService {
	/*
	 * private Context context;
	 * 
	 * public FileService(Context context) { super(); this.context = context; }
	 */

	public static boolean saveFileToSDCard(String fileName, String dirName,
			byte data[]) {
		boolean flag = false;

		String state = Environment.getExternalStorageState();

		FileOutputStream outputStream = null;

		File root = Environment.getExternalStorageDirectory();
		Log.i("FileService", "--->" + root.getAbsolutePath());
		if (state.equals(Environment.MEDIA_MOUNTED)) {

			File file = new File(root.getAbsolutePath() + "/" + dirName);
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

	public static byte[] readContextFromSdcard(String filenNme, String dirName) {

		File root = Environment.getExternalStorageDirectory();
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(root.getAbsolutePath() + "/" + dirName + "/"
					+ filenNme);
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

	public void saveFileToSdcardBysuff(String fileName, byte data[]) {
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (fileName.endsWith(".mp3")) {
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

			} else if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			} else if (fileName.endsWith(".3gp") || fileName.endsWith(".mp4")) {
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
			} else {
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			}

			FileOutputStream outputStream = null;

			try {
				outputStream = new FileOutputStream(new File(file, fileName));
				outputStream.write(data);
				outputStream.flush();
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

	}

	public static boolean deleteFileFromSdcard(String folder, String fileName) {
		boolean flag = false;
		File file = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File exitFile = new File(file.getAbsolutePath() + "/" + folder
					+ "/" + fileName);
			if (exitFile.exists()) {
				exitFile.delete();
				flag = true;
				return flag;
			}
		}

		return flag;

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
