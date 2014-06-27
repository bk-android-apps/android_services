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

public class PdfDownloadActivity extends Activity {
	EditText location1, location2, location3, location4, location5;
	DownloadService mService;
	boolean mBound = false;
	Intent intent;
	ArrayList<String> path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pdf_download_activity);

		location1 = (EditText) findViewById(R.id.editText_pdf1);
		location2 = (EditText) findViewById(R.id.editText_pdf2);
		location3 = (EditText) findViewById(R.id.editText_pdf3);
		location4 = (EditText) findViewById(R.id.editText_pdf4);
		location5 = (EditText) findViewById(R.id.editText_pdf5);
		location1.setText(R.string.pdf1);
		location2.setText(R.string.pdf2);
		location3.setText(R.string.pdf3);
		location4.setText(R.string.pdf4);
		location5.setText(R.string.pdf5);
		path = new ArrayList<String>();
		path.add("http://engineering.sjsu.edu/files/public/media/faculty-and-staff/forms/rtp-guidelines.pdf");
		path.add("http://engineering.sjsu.edu/files/public/media/faculty-and-staff/forms/lab-safety-audit-form.pdf");
		path.add("http://www.sjsu.edu/communications/docs/photoReleaseForm.pdf");
		path.add("http://www.sjsu.edu/registrar/docs/major_minor_more_than_90.pdf");
		path.add("http://www.sjsu.edu/registrar/docs/address_change.pdf");
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

	public void startPdfDownload(View p) {

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
		PdfDownloadActivity.this.finish();
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
