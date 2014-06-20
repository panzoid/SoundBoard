package com.panzoid.soundboard.controller;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.R.layout;
import com.panzoid.soundboard.model.event.Event;
import com.panzoid.soundboard.model.state.StateMachine;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class MainActivity extends Activity implements OnClickListener{

	private static final String LOG_TAG = "MainActivity";	
	private Switch playRecordSwitch;
	public static String mediaFileNamePart;
	
	private SoundPool soundPool;
	private int[] soundIDs = new int[]{-1,-1,-1,-1};
	private int[] oldStreamIDs = new int[4];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		playRecordSwitch = (Switch) findViewById(R.id.playRecordSwitch);
		playRecordSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					StateMachine.getInstance().changeState(StateMachine.States.RECORD_STATE);
				}
				else {
					StateMachine.getInstance().changeState(StateMachine.States.PLAY_STATE);
				}
			}
		});
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundIDs[0] = soundPool.load(this, R.raw.voice, 1);
		soundIDs[1] = soundPool.load(this, R.raw.drum, 1);
		soundIDs[2] = soundPool.load(this, R.raw.hihat, 1);
		soundIDs[3] = soundPool.load(this, R.raw.scratch, 1);
		
		mediaFileNamePart = Environment.getExternalStorageDirectory().getAbsolutePath();
		mediaFileNamePart += "/SoundBoard_";
	}

	@Override
	public void onClick(View v) {
		
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		
		switch(v.getId()) {
			case R.id.button1:
				Log.i(LOG_TAG, "button1.onClick()");
				if (oldStreamIDs[0] != 0) {
					soundPool.stop(oldStreamIDs[0]);
				}
				oldStreamIDs[0] = soundPool.play(soundIDs[0], volume, volume, 1, 0, 1f);
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, v.getId()));
				break;
			case R.id.button2:
				Log.i(LOG_TAG, "button2.onClick()");
				if (oldStreamIDs[1] != 0) {
					soundPool.stop(oldStreamIDs[1]);
				}
				oldStreamIDs[1] = soundPool.play(soundIDs[1], volume, volume, 1, 0, 1f);
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, v.getId()));
			 	break;
			case R.id.button3:
				Log.i(LOG_TAG, "button3.onClick()");
				if (oldStreamIDs[2] != 0) {
					soundPool.stop(oldStreamIDs[2]);
				}
				oldStreamIDs[2] = soundPool.play(soundIDs[2], volume, volume, 1, 0, 1f);
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, v.getId()));
				break;
			case R.id.button4:
				Log.i(LOG_TAG, "button4.onClick()");
				if (oldStreamIDs[3] != 0) {
					soundPool.stop(oldStreamIDs[3]);
				}
				oldStreamIDs[3] = soundPool.play(soundIDs[3], volume, volume, 1, 0, 1f);
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, v.getId()));
				break;
			default:
				Log.i(LOG_TAG, "???.onClick()");
				break;
		}
	}
}
