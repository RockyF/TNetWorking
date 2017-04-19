package com.aliencoder.tnetworking;

import com.aliencoder.tnetworking.serializer.DownloadSerializerImpl;
import com.aliencoder.tnetworking.serializer.INetWorkingSerializer;
import com.aliencoder.tnetworking.serializer.JsonSerializerImpl;
import com.aliencoder.tnetworking.task.GetTaskImpl;
import com.aliencoder.tnetworking.task.INetWorkingTask;
import com.aliencoder.tnetworking.task.PostTaskImpl;
import com.aliencoder.tnetworking.task.UploadTaskImpl;

import java.io.File;
import java.util.Map;

/**
 * Created by rockyl on 16/2/17.
 */
public class TNetWorkingManager {
	public int timeout, bufferSize;
	public INetWorkingSerializer serializer;

	public TNetWorkingManager(Map<String, Object> configs){
		if(configs == null){
			serializer = new JsonSerializerImpl();
			timeout = 2000;
			bufferSize = 1024 * 4;
		}else{
			if(configs.containsKey("serializer")){
				serializer = (INetWorkingSerializer)configs.get("serializer");
			}else{
				serializer = new JsonSerializerImpl();
			}
			if(configs.containsKey("timeout")){
				timeout = (int)configs.get("timeout");
			}else{
				timeout = 2000;
			}
			if(configs.containsKey("bufferSize")){
				bufferSize = (int)configs.get("bufferSize");
			}else{
				bufferSize = 1024 * 4;
			}
		}
	}

	HttpRequest createRequest(INetWorkingDelegate delegate){
		return new HttpRequest(delegate, timeout, bufferSize);
	}

	public void doCustomTask(String url, INetWorkingTask task, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, task, serializer);
	}

	public void upload(String url, File file, INetWorkingDelegate delegate) {
		doCustomTask(url, new UploadTaskImpl(file), delegate);
	}

	public void upload(String url, byte[] data, INetWorkingDelegate delegate) {
		doCustomTask(url, new UploadTaskImpl(data), delegate);
	}

	public void GET(String url, Map<String, String> params, INetWorkingDelegate delegate) {
		doCustomTask(url, new GetTaskImpl(params), delegate);
	}

	public void POST(String url, Map<String, String> params, INetWorkingDelegate delegate) {
		doCustomTask(url, new PostTaskImpl(params), delegate);
	}

	public void download(String url, Map<String, String> params, File path, INetWorkingDelegate delegate) {
		HttpRequest httpRequest = createRequest(delegate);
		httpRequest.doTask(url, new GetTaskImpl(params), new DownloadSerializerImpl(path));
	}
}
