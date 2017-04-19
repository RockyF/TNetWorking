package com.aliencoder.tnetworking;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public interface INetWorkingDelegate {
	void onSuccess(Object response);
	void onError(String err);
	void onProgress(int progress, int total);
}
