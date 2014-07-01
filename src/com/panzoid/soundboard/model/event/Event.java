package com.panzoid.soundboard.model.event;

public class Event {
	
	public enum Types {
		RECORD_EVENT,
		PLAY_EVENT,
		MENU_EVENT,
		ACTION_DOWN_EVENT,
		ACTION_UP_EVENT
	}
	
	// event type
	private final Types type;
	// id of view that triggered event, id can be null for state change events
	private final int id;
	
	public Event(Types type, int id) {
		this.type = type;
		this.id = id;
	}
	
	public Types getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}
}
