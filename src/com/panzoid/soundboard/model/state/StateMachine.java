/**
 * 
 */
package com.panzoid.soundboard.model.state;

import com.panzoid.soundboard.model.event.Event;

/**
 * @author Wei
 *
 */
public class StateMachine {
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
				currentState.onExit();
				currentState = menuState;
				currentState.onEnter();
			}
		}
		else if(newState == States.PLAY_STATE) {
			if( !(currentState instanceof PlayState) ){
				currentState.onExit();
				currentState = playState;
				currentState.onEnter();
			}
		}
		else if(newState == States.RECORD_STATE) {
			if( !(currentState instanceof RecordState) ){
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
