package com.panzoid.soundboard.model.state;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;
import android.media.AudioManager;
import android.media.SoundPool;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.controller.MainActivity;
import com.panzoid.soundboard.model.event.Event;

public class PlayState implements State {
	
	private static final String LOG_TAG = "PlayState";
	
	private static final boolean USE_TEST_SOUNDS = true;
	
	private SoundPool soundPool;
	private int[] soundIDs = new int[]{-1,-1,-1,-1};
	private int[] streamIDs = new int[4];
	private float volume;
	private AudioManager audioManager;
	
	public PlayState() {
		audioManager = (AudioManager) MainActivity.getInstance().getSystemService(MainActivity.AUDIO_SERVICE);
	}
	private void onPlay(int id) {		
		float actualVolume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actualVolume / maxVolume;
		
		try {
			int oldStreamID = 0;
			
			switch(id) {
			case R.id.button1:
				oldStreamID = streamIDs[0];
				streamIDs[0] = soundPool.play(soundIDs[0], volume, volume, 1, 0, 1f);
				break;
			case R.id.button2:
				oldStreamID = streamIDs[1];
				streamIDs[1] = soundPool.play(soundIDs[1], volume, volume, 1, 0, 1f);
				break;
			case R.id.button3:
				oldStreamID = streamIDs[2];
				streamIDs[2] = soundPool.play(soundIDs[2], volume, volume, 1, 0, 1f);
				break;
			case R.id.button4:
				oldStreamID = streamIDs[3];
				streamIDs[3] = soundPool.play(soundIDs[3], volume, volume, 1, 0, 1f);
				break;	
			}
			
			if (oldStreamID != 0) {
				soundPool.stop(oldStreamID);
			}
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
	}
	
	@Override
	public boolean onEnter() {
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

		if (USE_TEST_SOUNDS) {
			soundIDs[0] = soundPool.load(MainActivity.getInstance(), R.raw.voice, 1);
			soundIDs[1] = soundPool.load(MainActivity.getInstance(), R.raw.drum, 1);
			soundIDs[2] = soundPool.load(MainActivity.getInstance(), R.raw.hihat, 1);
			soundIDs[3] = soundPool.load(MainActivity.getInstance(), R.raw.scratch, 1);
		} else {
			String internalPath = MainActivity.internalStoragePath;
			soundIDs[0] = soundPool.load(internalPath + R.id.button1 + ".3gp", 1);
			soundIDs[1] = soundPool.load(internalPath + R.id.button2 + ".3gp", 1);
			soundIDs[2] = soundPool.load(internalPath + R.id.button3 + ".3gp", 1);
			soundIDs[3] = soundPool.load(internalPath + R.id.button4 + ".3gp", 1);
		}
		
		return true;
	}

	@Override
	public boolean onExit() {
		return true;
	}

	@Override
	public boolean handleEvent(Event event) {
		if (event.getType() == Event.Types.RECORD_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.RECORD_STATE);
			return true;
		}
		else if (event.getType() == Event.Types.MENU_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.MENU_STATE);
			return true;
		}
		else if (event.getType() == Event.Types.CLICK_EVENT) {
			onPlay(event.getId());
			return true;
		}
		return false;
	}

}
