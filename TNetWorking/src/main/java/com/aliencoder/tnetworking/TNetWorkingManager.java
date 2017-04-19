package com.aliencoder.tnetworking;

import com.aliencoder.tnetworking.serializer.DownloadSerializerImpl;
import com.aliencoder.tnetworking.serializer.INetWorkingSerializer;
import com.aliencoder.tnetworking.serializer.JsonSerializerImpl;
import com.aliencoder.tnetworking.task.GetTaskImpl;
import com.aliencoder.tnetworking.task.PostTaskImpl;
import com.aliencoder.tnetworking.task.UploadTaskImpl;

import java.io.File;
import java.util.Map;

/**
 * Created by rockyl on 16/2/17.
 */
public class TNetWorkingManager {
	public int timeout = 2000;
	public INetWorkingSerializer serializer;

	public TNetWorkingManager(){
		serializer = new JsonSerializerImpl();
	}

	HttpRequest createRequest(INetWorkingDelegate delegate){
		return new HttpRequest(delegate, timeout);
	}

	public void upload(String url, File file, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new UploadTaskImpl(file), serializer);
	}

	public void upload(String url, byte[] data, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new UploadTaskImpl(data), serializer);
	}

	public void GET(String url, Map<String, String> params, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new GetTaskImpl(params), serializer);
	}

	public void POST(String url, Map<String, String> params, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new PostTaskImpl(params), serializer);
	}

	public void download(String url, Map<String, String> params, File path, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new GetTaskImpl(params), new DownloadSerializerImpl(path));
	}

	private static TNetWorkingManager _instance;
	public static TNetWorkingManager getInstance(){
		if(_instance == null){
			_instance = new TNetWorkingManager();
		}
		return _instance;
	}
}
