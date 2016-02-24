package com.net.mokey.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class FileHelper {
	private Context context;
	private boolean hasSD = false;
	String local_file = SaveUtil.SD_PATH+"/"+SaveUtil.FOLDER_NAME;
	public FileHelper(Context context) {
		this.context = context;
	}
	/**
	 * 创建文件夹
	 */
	public void createFile(){
		File f = new File(local_file);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	/**
	 * 创建文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String file_name) throws IOException {
		createFile();
		File file = new File(local_file + "//" + file_name);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public boolean deleteSDFile(String file_name) {
		File file = new File(local_file + "//" + file_name);
		if (file == null || !file.exists() || file.isDirectory())
			return false;
		return file.delete();
	}

	public void writeSDFile(String file_name,String str) {
		try {
			createSDFile(file_name);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(local_file + "//" + file_name, true);
			OutputStreamWriter writer = new OutputStreamWriter(outStream,"utf-8");
			writer.write(str);
			writer.close();
			outStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readSDFile(String file_name) {
		try {
			createSDFile(file_name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		try {
			File f = new File(local_file + "//" + file_name);// 这是对应文件名
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				// 在这对tmp操作
				sb.append(tmp);
			}
			in.close();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Log.e("mytag", "得到的qr===="+sb.toString());
		return sb.toString();
	}
	public boolean hasSD() {
		return hasSD;
	}
}
