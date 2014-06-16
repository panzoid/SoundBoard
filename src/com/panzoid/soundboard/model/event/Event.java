package com.panzoid.soundboard.model.event;

public class Event {
	
	public enum Types {
		RECORD_EVENT,
		PLAY_EVENT,
		MENU_EVENT,
		CLICK_EVENT
	}
	
	private final Types type;
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
