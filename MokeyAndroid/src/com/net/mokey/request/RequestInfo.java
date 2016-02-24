package com.net.mokey.request;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * RequestInfo
 * 
 * @author Andlisoft<br/>
 *         ��������ķ�װ������URL������JSON 2013/11/8
 */
public class RequestInfo {

	/* URL���ο�HttpUrls���塣������Ҫ��������Ĳ������HttpParamFormat���� */
	public String url;
	/* ����body��json�ַ�����JsonMaker���� */
	public String json;
	/* ʹ�û��� */
	public boolean useCache = false;
	/* �ⲿ���� */
	public boolean external = false;
	/* ����ʽ */
	public String method = "GET";
	/* �������Ƿ���ʾ����� */
	public boolean showDialog = false;
	/* ���������ļ� */
	public boolean download = false;

	public boolean showErrInfo = true;

	public boolean ifUploadFile = false;

	public Map<String, String> params;

	public Map<String, File> files;

	public RequestInfo() {
	}

	public RequestInfo(String url, String json) {
		this.url = url;
		this.json = json;
	}

}
