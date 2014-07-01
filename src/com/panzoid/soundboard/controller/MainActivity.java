package com.panzoid.soundboard.controller;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.R.layout;
import com.panzoid.soundboard.model.event.Event;
import com.panzoid.soundboard.model.state.StateMachine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener{
	
	public static final boolean EMULATOR = true;

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
		
		((Button) findViewById(R.id.button1)).setOnTouchListener(this);
		((Button) findViewById(R.id.button2)).setOnTouchListener(this);
		((Button) findViewById(R.id.button3)).setOnTouchListener(this);
		((Button) findViewById(R.id.button4)).setOnTouchListener(this);
		
		internalStoragePath = getApplicationContext().getFilesDir().getPath();
		mainActivity = this;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		boolean eventConsumed = false;
		
		Event.Types eventType = null;
		if(MotionEvent.ACTION_DOWN == event.getActionMasked()) {
			eventType = Event.Types.ACTION_DOWN_EVENT;
		}
		else if(MotionEvent.ACTION_UP == event.getActionMasked()) {
			eventType = Event.Types.ACTION_UP_EVENT;
		}
		
		switch(view.getId()) {
			case R.id.button1:
				Log.i(LOG_TAG, "button1.onTouch()");
				eventConsumed = StateMachine.getInstance().handleEvent(new Event(eventType, view.getId()));
				break;
			case R.id.button2:
				Log.i(LOG_TAG, "button2.onTouch()");
				eventConsumed = StateMachine.getInstance().handleEvent(new Event(eventType, view.getId()));
			 	break;
			case R.id.button3:
				Log.i(LOG_TAG, "button3.onTouch()");
				eventConsumed = StateMachine.getInstance().handleEvent(new Event(eventType, view.getId()));
				break;
			case R.id.button4:
				Log.i(LOG_TAG, "button4.onTouch()");
				eventConsumed = StateMachine.getInstance().handleEvent(new Event(eventType, view.getId()));
				break;
			default:
				Log.i(LOG_TAG, "???.onTouch()");
				break;
		}
		return eventConsumed;
	}
	
	// Sets the text of a TextView indicated by id
	public void setText(int id, String text) {
		View view = findViewById(id);
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		}
	}
}
