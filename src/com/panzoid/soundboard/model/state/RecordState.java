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
	
	private static final boolean EMULATOR = false;
	
	private static final String LOG_TAG = "RecordState";
	
	private MediaRecorder mRecorder;
	
	private static boolean isRecording;
	private static int recordingId;
	
	private Handler handler = new Handler();
	private RecordTimer recordTimer;
	
	private void onRecord(int id) {
		// Stop recording if it is recording and the same button was pressed.
		if(isRecording) {
			if(id == recordingId) {
				if(!EMULATOR) {
					mRecorder.stop(); 
				}
				MainActivity.getInstance().setText(id, "");
				handler.removeCallbacks(recordTimer);
				isRecording = false;
			}
		}
		else {
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
	}
	
	@Override
	public boolean onEnter() {
		isRecording = false;
		mRecorder = new MediaRecorder();
		return true;
	}

	@Override
	public boolean onExit() {
		isRecording = false;
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
			onRecord(event.getId());
			return true;
		}
		return false;
	}
	
	private class RecordTimer implements Runnable
	{
		// RecordTimer ticks each second for 3 seconds.
		private int ticks;
		// Id of the TextView to display count down
		private int id;
		
		public RecordTimer(int id) {
			this.id = id;
			ticks = 3;
		}
		@Override
		public void run() {
			if( ticks <= 0 ) {
				if(isRecording) {
					onRecord(id);
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
