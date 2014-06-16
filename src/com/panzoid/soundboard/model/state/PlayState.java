package com.panzoid.soundboard.model.state;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

import com.panzoid.soundboard.controller.MainActivity;
import com.panzoid.soundboard.model.event.Event;

public class PlayState implements State {
	
private static final String LOG_TAG = "PlayState";
	
	private MediaPlayer mPlayer;
	
	private void onPlay(int id) {
		try {
            mPlayer.setDataSource(MainActivity.mediaFileNamePart+id+".3gp");
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "MediaPlayer.prepare() failed");
        }
	}
	
	@Override
	public boolean onEnter() {
		mPlayer = new MediaPlayer();
		return true;
	}

	@Override
	public boolean onExit() {
		if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
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
