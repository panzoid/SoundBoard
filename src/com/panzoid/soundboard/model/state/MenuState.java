package com.panzoid.soundboard.model.state;

import com.panzoid.soundboard.model.event.Event;

/*
 * MenuState remains unused for now as there is no use for a menu.
 * 
 * @author Wei Pan
 */
public class MenuState implements State{

	@Override
	public boolean onEnter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onExit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleEvent(Event event) {
		if (event.getType() == Event.Types.PLAY_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.PLAY_STATE);
			return true;
		}
		return false;
	}

}
