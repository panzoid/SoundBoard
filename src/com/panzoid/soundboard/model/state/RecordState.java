package com.panzoid.soundboard.model.state;

import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.controller.MainActivity;
import com.panzoid.soundboard.model.event.Event;

public class RecordState implements State {
	
	// Change to true if running in emulator as microphone is not supported in emulator
	private static final boolean EMULATOR = true;
	
	private static final String LOG_TAG = "RecordState";
	
	private MediaRecorder mRecorder;
	
	// Variables to determine if recording is in progress and which id (button) triggered it
	// Declared as static since microphone should only be in use by one instance
	private static boolean isRecording;
	private static int recordingId;
	
	// Handler and Timer to limit recordings to R.integer.max_recording_length_seconds
	private Handler handler = new Handler();
	private RecordTimer recordTimer;
	
	private void onClickEvent(int id) {
		if(isRecording) {
			stopRecording(id);
		}
		else {
			startRecording(id);
		}
	}
	
	private void startRecording(int id) {
		// Emulator cannot do recordings
		if(!EMULATOR) {
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setOutputFile(MainActivity.internalStoragePath + id + ".3gp");
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			try {
				mRecorder.prepare();
			} catch (IOException e) {
				Log.e(LOG_TAG, "MediaRecorder.prepare() failed");
			}
			mRecorder.start();
		}
		isRecording = true;
		recordingId = id;
		recordTimer = new RecordTimer(id);
		recordTimer.run();
	}

	private void stopRecording(int id) {
		// Only stop recording if the same button was pressed.
		if(id == recordingId) {
			// Emulator cannot start a recording so it is not in stop-able state
			if(!EMULATOR) {
				mRecorder.stop(); 
			}
			MainActivity.getInstance().setText(id, "");
			handler.removeCallbacks(recordTimer);
			isRecording = false;
		}
	}
	
	@Override
	public boolean onEnter() {
		isRecording = false;
		mRecorder = new MediaRecorder();
		return true;
	}

	@Override
	public boolean onExit() {
		// Stop recording if we leave this state
		if(isRecording) {
			stopRecording(recordingId);
		}
		
		// Release MediaRecorder resource
		if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
		return true;
	}

	@Override
	public boolean handleEvent(Event event) {
		if (event.getType() == Event.Types.PLAY_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.PLAY_STATE);
			return true;
		}
		else if (event.getType() == Event.Types.MENU_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.MENU_STATE);
			return true;
		}
		else if (event.getType() == Event.Types.CLICK_EVENT) {
			onClickEvent(event.getId());
			return true;
		}
		return false;
	}
	
	// RecordTimer ticks each second for 3 seconds
	private class RecordTimer implements Runnable
	{
		private int ticks;
		// id of the TextView to display count down
		private int id;
		
		public RecordTimer(int id) {
			this.id = id;
			ticks = MainActivity.getInstance().getResources().getInteger(R.integer.max_recording_length_seconds);
		}
		@Override
		public void run() {
			if( ticks <= 0 ) {
				// Only stop recording if currently recording
				if(isRecording) {
					stopRecording(id);
				}
			}
			else {
				Log.i(LOG_TAG, "RecordTimer ticks: " + ticks);
				MainActivity.getInstance().setText(id, ""+ticks);
				handler.postDelayed(this, 1000);
			}
			ticks--;
		}
	}
}
