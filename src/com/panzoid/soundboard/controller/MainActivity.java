package com.panzoid.soundboard.controller;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.R.layout;
import com.panzoid.soundboard.model.event.Event;
import com.panzoid.soundboard.model.state.StateMachine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	private static final String LOG_TAG = "MainActivity";	
	private Switch playRecordSwitch;
	public static String mediaFileNamePart;
	public static String internalStoragePath;
	
	public static MainActivity mainActivity;
	
	public static MainActivity getInstance(){
		return mainActivity;
	}
	
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
		
		internalStoragePath = getApplicationContext().getFilesDir().getPath();
		mainActivity = this;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.button1:
				Log.i(LOG_TAG, "button1.onClick()");
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, view.getId()));
				break;
			case R.id.button2:
				Log.i(LOG_TAG, "button2.onClick()");
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, view.getId()));
			 	break;
			case R.id.button3:
				Log.i(LOG_TAG, "button3.onClick()");
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, view.getId()));
				break;
			case R.id.button4:
				Log.i(LOG_TAG, "button4.onClick()");
				StateMachine.getInstance().handleEvent(new Event(Event.Types.CLICK_EVENT, view.getId()));
				break;
			default:
				Log.i(LOG_TAG, "???.onClick()");
				break;
		}
	}
	
	// Sets the text of a TextView indicated by id
	public void setText(int id, String text) {
		View view = findViewById(id);
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		}
	}
}
