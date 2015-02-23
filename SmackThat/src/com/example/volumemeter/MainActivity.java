
package com.example.volumemeter;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	// The abstract class KnockDetector requires the implementation of void
	// knockDetected(int) method
	private PhoneIntentReceiver pr = null;
	public static KnockDetector detectorInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PhoneIntentReceiver.setMainActivityHandler(this);
		IntentFilter callInterceptorIntentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
		registerReceiver(pr, callInterceptorIntentFilter);

		detectorInstance = new KnockDetector(this) {
			@Override
			void knockDetected(int knockCount) {
				if (knockCount >= 1) {
					AudioManager audio = (AudioManager) MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
					audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					mTimer.cancel();
					eventGen.cancel();
				}
			}
		};

		detectorInstance.init();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
