package com.aliencoder.tnetworking.serializer;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public class JsonSerializerImpl extends TextSerializerImpl {
	private static String TAG = "JsonSerializerImpl";

	@Override
	public Object parse(byte[] bytes) {
		String body = (String)super.parse(bytes);

		JSONObject json = null;
		try{
			json = (JSONObject) JSON.parse(body);
		}catch(JSONException exception){
			Log.i(TAG, "response decode failed: " + body);
		}

		return json;
	}
}
