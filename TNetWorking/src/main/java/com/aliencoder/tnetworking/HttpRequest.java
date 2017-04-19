package com.aliencoder.tnetworking;

import android.os.AsyncTask;

import com.aliencoder.tnetworking.task.INetWorkingTask;
import com.aliencoder.tnetworking.serializer.INetWorkingSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by RockyF onVersionSuccess 2015/4/16.
 */
public class HttpRequest extends AsyncTask<Object, Integer, Object> {
	private static String TAG = "HttpRequest";

	public INetWorkingDelegate netWorkingDelegate;
	int timeout;
	int bufferSize;

	String requestURL;
	String method;
	INetWorkingTask task;

	private String nextError = "error";

	public INetWorkingSerializer serializer;

	public HttpRequest(INetWorkingDelegate netWorkingDelegate, int timeout, int bufferSize) {
		this.netWorkingDelegate = netWorkingDelegate;
		this.timeout = timeout;
		this.bufferSize = bufferSize;
	}

	public void doTask(String url, INetWorkingTask netWorkingTask, INetWorkingSerializer netWorkingSerializer){
		requestURL = url;
		method = netWorkingTask.getMethod();
		this.task = netWorkingTask;
		this.serializer = netWorkingSerializer;

		start();
	}

	private void start() {
		execute();
	}

	@Override
	protected Object doInBackground(Object[] params) {
		Object response = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL url = new URL(task.parseUrl(requestURL));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setReadTimeout(timeout);
			conn.setUseCaches(false);

			task.parseRequest(conn);

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				byte[] buffer = new byte[bufferSize];
				int total = conn.getContentLength();
				int read;
				int progress = 0;

				is = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(total);
				while ((read = is.read(buffer)) > 0) {
					baos.write(buffer, 0, read);
					progress += read;

					publishProgress(progress, total);
				}
				is.close();
				response = serializer.parse(baos.toByteArray());
			} else {
				response = null;
				nextError = "error with response code: " + responseCode;
				cancel(true);
			}

		} catch (IOException e) {
			e.printStackTrace();

			nextError = "invalid url: " + requestURL;
			cancel(true);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return response;
	}

	@Override
	protected void onPostExecute(Object o) {
		if (netWorkingDelegate != null) {
			netWorkingDelegate.onSuccess(o);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (netWorkingDelegate != null) {
			netWorkingDelegate.onProgress(values[0], values[1]);
		}
	}

	@Override
	protected void onCancelled(Object o) {
		if (netWorkingDelegate != null) {
			netWorkingDelegate.onError(nextError);
			nextError = "error";
		}
	}
}