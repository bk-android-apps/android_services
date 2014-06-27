package bipul.karki.simple_service;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//**************button actions*************
	
	public void downloadPdf(View pdf){
		Intent intent = new Intent(getApplicationContext(), PdfDownloadActivity.class);
		startActivity(intent);
	}
	
	public void downloadImage(View img ){
		Intent intent = new Intent(getApplicationContext(), ImageDownloadActivity.class);
		startActivity(intent);
	}
	
	public void downloadTextfile(View txt){
		Intent intent = new Intent(getApplicationContext(), TextFileDownloadActivity.class);
		startActivity(intent);
	}
	
	public void closeMainActivity(View v){
		MainActivity.this.finish();
	}

}
