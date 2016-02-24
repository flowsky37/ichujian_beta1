package com.net.mokey.activity.net;

public class MainHttpServer {
	private static HCKHttpClient client;

	public MainHttpServer() {
		MainHttpServer.client = new HCKHttpClient();
		client.setTimeout(10000);
	}

	public static void getData(String urlString, RequestParams params,
			HCKHttpResponseHandler res) {
		if (params == null) {
			client.get(urlString, res);
		} else {
			client.get(urlString, params, res);
		}
	}

	public static void getData(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		if (params == null) {
			client.get(urlString, res);
		} else {
			client.get(urlString, params, res);
		}
	}

	public static void getData(String uString,
			BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	public static void postData(String urlString, HCKHttpResponseHandler res) {
		client.post(urlString, res);
	}

	public static void postData(String url, RequestParams params,
			HCKHttpResponseHandler handler) {
		client.post(url, params, handler);
	}

	public static HCKHttpClient getClient() {
		return client;
	}

}
