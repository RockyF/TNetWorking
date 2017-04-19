package com.aliencoder.tnetworking.serializer;

import java.util.HashMap;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public class TextSerializerImpl implements INetWorkingSerializer {
	private static String TAG = "TextSerializerImpl";

	@Override
	public Object parse(byte[] bytes) {
		return new String(bytes);
	}
}
