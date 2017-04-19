package com.aliencoder.tnetworking.task;

import java.net.HttpURLConnection;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public interface INetWorkingTask {
	String getMethod();
	String parseUrl(String url);
	void parseRequest(HttpURLConnection conn);
}
