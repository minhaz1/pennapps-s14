package com.example.volumemeter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneIntentReceiver extends BroadcastReceiver {
	public static final String TAG = "PhoneIntentReceiver";
	public static Context main;

	static void setMainActivityHandler(MainActivity m) {
		main = m;
	}

	@Override
	public void onReceive(final Context ctx, Intent intent) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener listener = new PhoneStateListener() {
			// private KnockDetector detectorInstance;

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				super.onCallStateChanged(state, incomingNumber);

				if (state == TelephonyManager.CALL_STATE_RINGING) {
					// when Ringing
					Log.d(TAG, "Phone state Ringing");
					MainActivity.detectorInstance.resume();

				} else {
					MainActivity.detectorInstance.pause();
					Log.d("DEBUG", "phone state is now idle");
					AudioManager audio = (AudioManager) ctx
							.getSystemService(Context.AUDIO_SERVICE);
					audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				}
			}
		};
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

}
