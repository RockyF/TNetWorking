# TNetWorking
A net working lib for Android

## Install

Define via Gradle:

``` groovy
repositories {
	maven { url 'https://github.com/RockyF/TNetWorking/raw/master/releases/' }
}

dependencies {
	compile 'com.aliencoder:tnetworking:1.0.1'
}
```

## Use

``` java
Map<String, Object> configs = new HashMap<>();
configs.put("serializer", new JsonSerializerImpl());
configs.put("timeout", 2000);
configs.put("bufferSize", 1024);
TNetWorkingManager manager = new TNetWorkingManager(configs); //configs can be null
manager.download("the file url", null, desFile, new INetWorkingDelegate() {
	@Override
	public void onSuccess(Object response) {
		File file = (File)response;
		Log.i(TAG, "download file success: " + file.getAbsolutePath());
	}

	@Override
	public void onError(String err) {
		Log.i(TAG, err);
	}

	@Override
	public void onProgress(int progress, int total) {
		Log.i(TAG, progress + "/" + total);
	}
});
```