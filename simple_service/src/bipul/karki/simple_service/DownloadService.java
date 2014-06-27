package bipul.karki.simple_service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

public class DownloadService extends Service {
	int counter = 0;

	private Timer timer = new Timer();

	private final IBinder mBinder = new DownloadBinder(); // Binder given to clients

	/*
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */

	class DownloadBinder extends Binder {
		DownloadService getService() {
			// Return this instance of DownloadService so clients can call
			// public methods
			return DownloadService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		//return null; // return null to use service without binding.
		return mBinder;
	}

	/*@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		Bundle extras = intent.getExtras();
		String activity = extras.getString("activity");
		Log.i("MyTag", "**** Running : " + activity + " using onStartCommand ******");
		ArrayList<String> url = extras.getStringArrayList("filepath");
		for (String s : url) {
			try {
				new DoBackgroundTask().execute(new URL(s));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		return START_STICKY;
	} */

	// Download method for bindService
	public void downloadFiles(Intent intent) {
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		Bundle extras = intent.getExtras();
		String activity = extras.getString("activity");
		Log.i("MyTag", "********** Running : " + activity + " *********");
		ArrayList<String> url = extras.getStringArrayList("filepath");
		for (String s : url) {
			try {
				new DoBackgroundTask().execute(new URL(s));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}
}

class DoBackgroundTask extends AsyncTask<URL, Void, Void> {

	@Override
	protected Void doInBackground(URL... urls) {
		int count = urls.length;
		try {
			File root = android.os.Environment.getExternalStorageDirectory();
			//Log.d("MyTag", root.toString());
			
			for (int i = 0; i < count; i++) {
				String fileExtension = MimeTypeMap
						.getFileExtensionFromUrl(urls[i].toString());
				String filename = URLUtil.guessFileName(urls[i].toString(),
						null, fileExtension); // get file name with extension
				File dir = new File (root + "/CMPE277");
				if (!dir.exists()){
					dir.mkdir();
					Log.d("MyTag", dir.toString()+" directory created");
				} else {
					Log.d("MyTag", dir.toString()+ " directory exists ");
				}
				URLConnection connection = urls[i].openConnection();
				File file = new File(dir, filename);
				long startTime = System.currentTimeMillis();
				Log.i("MyTag", "download begginning...from: "+ urls[i]);
				connection.connect();
				
				InputStream inputStream = connection.getInputStream();
				BufferedInputStream bis = new BufferedInputStream (inputStream);
				ByteArrayBuffer baf = new ByteArrayBuffer(5000);
				int current =0;
				while ((current = bis.read()) != -1){
					baf.append((byte) current);
				}
				
				FileOutputStream fos =new FileOutputStream(file);
				fos.write(baf.toByteArray());
				fos.flush();
				fos.close();
				Log.i("MyTag", "file downloaded in: " +((System.currentTimeMillis()-startTime))+"ms");
				int totalSize = connection.getContentLength(); // check file size
																
				Log.i("MyTag", filename + ",content size: " + totalSize/1024 +"KB" );
				inputStream.close();
			}
		} catch (Exception e) {
			Log.e("error:", e.getMessage());
		}

		return null;
	}
}
