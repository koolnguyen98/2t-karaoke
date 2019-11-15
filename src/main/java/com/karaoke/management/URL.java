package com.karaoke.management;

public class URL {
	public final String API = "/api";
	
	public final String API_AUTHENTICATION_SIGIN = API + "/checksigin";
	public final String API_AUTHENTICATION_SIGUP = API + "/sigup";
	
	public final String API_ROOM = API + "/room";
	public final String API_ROOM_FIND_ALL = API_ROOM;
	public final String API_ROOM_UPDATE_BY_ID = API_ROOM + "/{id}/update";
	public final String API_ROOM_CREATE = API_ROOM + "/create";
	public final String API_ROOM_DELETE_BY_ID = API_ROOM + "/{id}/delete";
	
	
}
