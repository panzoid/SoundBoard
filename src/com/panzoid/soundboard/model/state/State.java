package com.panzoid.soundboard.model.state;

import com.panzoid.soundboard.model.event.Event;

public interface State {
	public boolean onEnter();
	public boolean onExit();
	public boolean handleEvent(Event event);
}
