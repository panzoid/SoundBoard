package com.panzoid.soundboard.controller;

import com.panzoid.soundboard.R;
import com.panzoid.soundboard.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

public class MainActivity extends Activity {

	Switch playRecordSwitch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		playRecordSwitch = (Switch) findViewById(R.id.playRecordSwitch);
	}
}
