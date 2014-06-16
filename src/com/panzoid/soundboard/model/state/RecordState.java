package com.panzoid.soundboard.model.state;

import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

import com.panzoid.soundboard.controller.MainActivity;
import com.panzoid.soundboard.model.event.Event;

public class RecordState implements State {

	private static final String LOG_TAG = "RecordState";
	
	private MediaRecorder mRecorder;
	private boolean isRecording;
	
	private void onRecord(int id) {
		// Stop recording if it is recording
		if(isRecording) {
			mRecorder.stop();
			mRecorder.release();
	        mRecorder = null;
		}
		else {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setOutputFile(MainActivity.mediaFileNamePart + id + ".3gp");
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			try {
	            mRecorder.prepare();
	            isRecording = true;
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "MediaRecorder.prepare() failed");
	        }
		}
	}
	
	@Override
	public boolean onEnter() {
		isRecording = false;
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

}
