package bipul.karki.simple_service;

import java.util.ArrayList;

import bipul.karki.simple_service.DownloadService.DownloadBinder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ImageDownloadActivity extends Activity {
	EditText location1, location2, location3, location4, location5, location6,
			location7, location8, location9, location10;
	DownloadService mService;
	boolean mBound = false;
	Intent intent;
	ArrayList<String> path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_download_activity);

		location1 = (EditText) findViewById(R.id.editText_image1);
		location2 = (EditText) findViewById(R.id.editText_image2);
		location3 = (EditText) findViewById(R.id.editText_image3);
		location4 = (EditText) findViewById(R.id.editText_image4);
		location5 = (EditText) findViewById(R.id.editText_image5);
		location6 = (EditText) findViewById(R.id.editText_image6);
		location7 = (EditText) findViewById(R.id.editText_image7);
		location8 = (EditText) findViewById(R.id.editText_image8);
		location9 = (EditText) findViewById(R.id.editText_image9);
		location10 = (EditText) findViewById(R.id.editText_image10);
		location1.setText(R.string.image1);
		location2.setText(R.string.image2);
		location3.setText(R.string.image3);
		location4.setText(R.string.image4);
		location5.setText(R.string.image5);
		location6.setText(R.string.image6);
		location7.setText(R.string.image7);
		location8.setText(R.string.image8);
		location9.setText(R.string.image9);
		location10.setText(R.string.image10);
		path = new ArrayList<String>();
		path.add("http://www.sjsu.edu/sjsuhome/pics/paintings-on-campus.jpg");
		path.add("http://www.sjsu.edu/academics/pics/acedemics.jpg");
		path.add("http://www.sjsu.edu/discover/pics/administration.jpg");
		path.add("http://info.sjsu.edu/info/img/land_admission.jpg");
		path.add("http://www.sjsu.edu/sjsuhome/pics/black-history-month-021114.jpg");
		path.add("http://www.sjsu.edu/sjsuhome/pics/back-to-school-012414.jpg");
		path.add("http://www.sjsu.edu/sjsuhome/pics/hiep-012714.jpg");
		path.add("http://www.sjsu.edu/housingoptions/pics/campus-housing.jpg");
		path.add("http://www.sjsu.edu/future_students/pics/student-photographer.jpg");
		path.add("http://www.sjsu.edu/alumni/pics/scholarships_piggybank_660.jpg");
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void onStart() {
		super.onStart();
		intent = new Intent(getBaseContext(), DownloadService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		Log.i("MyTag", " service connected with BIND_AUTO_CREATE >>>");
		//bindService(intent, mConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
		//Log.i("MyTag", " service connected with BIND_ADJUST_WITH_ACTIVITY >>>");
	}

	@Override
	protected void onStop() {
		super.onStop();// Unbind from the service
		if (mBound) {
		unbindService(mConnection);
		mBound = false;
		}
	}

	public void startImageDownload(View v) {

		// Intent intent = new Intent(getBaseContext(), DownloadService.class);
		// intent.putExtra("filepath", path);
		// intent.putExtra("activity", "TextFileDownloadActivity");
		// startService(intent); // use this to run service without binding

		// Bind to DownloadService
		if (mBound) {
			Toast.makeText(this, " DownloadService is bounded",
					Toast.LENGTH_LONG).show();
			intent.putExtra("filepath", path);
			intent.putExtra("activity", "TextFileDownloadActivity");
			mService.downloadFiles(intent);
		}
		ImageDownloadActivity.this.finish();

	}

	// *** Defines call-backs for service binding, passed to bindService() ***/
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			// We've bound to DownloadService, cat the IBinder and get
			// DownloadService instance
			DownloadBinder binder = (DownloadBinder) service;
			mService = binder.getService();
			mBound = true;
			Log.i("MyTag", " service connected >>>");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
				mBound = false;
				Log.i("MyTag", " service disconnected <<<");
		}		
		
	};// ServiceConnection
}
