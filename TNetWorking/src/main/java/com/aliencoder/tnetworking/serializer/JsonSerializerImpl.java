package com.aliencoder.tnetworking.serializer;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public class JsonSerializerImpl extends TextSerializerImpl {
	private static String TAG = "JsonSerializerImpl";

	@Override
	public Object parse(byte[] bytes) {
		String body = (String)super.parse(bytes);

		JSONObject json = null;
		try {
			json = new JSONObject(body);
		} catch (JSONException e) {
			Log.i(TAG, "response decode failed: " + body);
			e.printStackTrace();
		}

		return json;
	}
}
