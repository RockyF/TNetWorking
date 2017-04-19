package com.aliencoder.tnetworking.serializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rockyl onVersionSuccess 16/2/17.
 */
public class DownloadSerializerImpl implements INetWorkingSerializer {
	private static String TAG = "DownloadSerializerImpl";

	private File des;

	public DownloadSerializerImpl(File des){
		this.des = des;
	}

	@Override
	public Object parse(byte[] bytes) {
		des.getParentFile().mkdirs();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(des);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return des;
	}
}
