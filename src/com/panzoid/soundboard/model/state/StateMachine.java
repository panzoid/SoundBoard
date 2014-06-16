/**
 * 
 */
package com.panzoid.soundboard.model.state;

import android.util.Log;

import com.panzoid.soundboard.model.event.Event;

/**
 * @author Wei
 *
 */
public class StateMachine {
	private static final String LOG_TAG = "StateMachine";
	
	public enum States {
		MENU_STATE,
		PLAY_STATE,
		RECORD_STATE
	}
	
	private static StateMachine sm;
	
	private MenuState menuState;
	private PlayState playState;
	private RecordState recordState;
	private State currentState;
	
	// StateMachine is a singleton
	public static StateMachine getInstance() {
		if(sm == null) {
			sm = new StateMachine();
		}
		return sm;
	}
	
	public StateMachine() {
		menuState = new MenuState();
		playState = new PlayState();
		recordState = new RecordState();
		
		currentState = playState;
		currentState.onEnter();
	}
	
	public void changeState(States newState) {
		if(newState == States.MENU_STATE) {
			if( !(currentState instanceof MenuState) ){
				Log.i(LOG_TAG, "changeState(MENU_STATE)");
				currentState.onExit();
				currentState = menuState;
				currentState.onEnter();
			}
		}
		else if(newState == States.PLAY_STATE) {
			if( !(currentState instanceof PlayState) ){
				Log.i(LOG_TAG, "changeState(PLAY_STATE)");
				currentState.onExit();
				currentState = playState;
				currentState.onEnter();
			}
		}
		else if(newState == States.RECORD_STATE) {
			if( !(currentState instanceof RecordState) ){
				Log.i(LOG_TAG, "changeState(RECORD_STATE)");
				currentState.onExit();
				currentState = recordState;
				currentState.onEnter();
			}
		}
	}
	
	public boolean handleEvent(Event event) {
		
		if(!currentState.handleEvent(event)) {
			// TODO: Do something if our current state does not know how to handle event
			return false;
		}
		return true;
	}
	
}
