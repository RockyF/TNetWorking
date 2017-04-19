package com.aliencoder.tnetworking.task;

import com.aliencoder.tnetworking.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by rockyl on 16/2/22.
 */
public class PostTaskImpl implements INetWorkingTask {
	Map<String, String> parameters;

	public PostTaskImpl(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getMethod() {
		return "POST";
	}

	@Override
	public String parseUrl(String url) {
		return url;
	}

	@Override
	public void parseRequest(HttpURLConnection conn) {
		if (parameters != null) {
			String paramsStr = Utils.parameterStringify(parameters);

			byte[] bypes = paramsStr.getBytes();
			try {
				conn.getOutputStream().write(bypes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
