package com.example.volumemeter;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.hardware.SensorManager;

abstract public class KnockDetector {

	private Context parentActivity;
	TimerTask eventGen = null;
	Timer mTimer;
	private final int MaxTimeBetweenEvents = 25;
	private int period = MaxTimeBetweenEvents;

	private AccelSpikeDetector mAccelSpikeDetector;
	private PatternRecognizer mPatt = new PatternRecognizer(this);

	abstract void knockDetected(int knockCount);

	KnockDetector(Context main) {
		parentActivity = main;
	}

	public void init() {
		mAccelSpikeDetector = new AccelSpikeDetector(
				(SensorManager) parentActivity.getSystemService(Context.SENSOR_SERVICE));
	}

	public void pause() {
		mAccelSpikeDetector.stopAccSensing();
	}

	public void resume() {
		// mSoundKnockDetector.vol_start();
		mAccelSpikeDetector.resumeAccSensing();
		mTimer = new Timer();
		eventGenerator();
	}

	private void eventGenerator() {
		eventGen = new TimerTask() {
			@Override
			public void run() {
				if (mAccelSpikeDetector.spikeDetected) {
					mPatt.knockEvent();
				}
			}
		};
		mTimer.scheduleAtFixedRate(eventGen, 0, period); // start after 0 ms
	}
}
