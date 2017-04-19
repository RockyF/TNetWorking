package com.aliencoder.tnetworking.task;

import com.aliencoder.tnetworking.Utils;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by rockyl on 16/2/22.
 */
public class GetTaskImpl implements INetWorkingTask {
	Map<String, String> parameters;

	public GetTaskImpl(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public String parseUrl(String url) {
		if(parameters != null){
			String paramsStr = Utils.parameterStringify(parameters);
			if (url.contains("?")) {
				url += paramsStr;
			} else {
				url += "?" + paramsStr;
			}
		}

		return url;
	}

	@Override
	public void parseRequest(HttpURLConnection conn) {

	}
}
