package com.panzoid.soundboard.model.state;

import com.panzoid.soundboard.model.event.Event;

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
		if (event == Event.PLAY_EVENT) {
			StateMachine.getInstance().changeState(StateMachine.States.PLAY_STATE);
			return true;
		}
		return false;
	}

}
