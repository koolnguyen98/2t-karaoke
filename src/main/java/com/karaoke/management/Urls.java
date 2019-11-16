package com.karaoke.management;

public interface Urls {
	
	public final String API = "/api";
	
	public final String API_AUTHENTICATION_SIGNIN = API + "/checksignin";
	public final String API_AUTHENTICATION_SIGUP = API + "/signup";
	
	public final String API_ROOM = API + "/room";
	public final String API_ROOM_FIND_ALL = API_ROOM;
	public final String API_ROOM_FIND_BY_ID = API_ROOM + "/{id}";
	public final String API_ROOM_UPDATE_BY_ID = API_ROOM + "/{id}/update";
	public final String API_ROOM_CREATE = API_ROOM + "/create";
	public final String API_ROOM_DELETE_BY_ID = API_ROOM + "/{id}/delete";
	
	public final String API_ROOM_TYPE = API + "/roomtype";
	public final String API_ROOM_TYPE_FIND_ALL = API_ROOM_TYPE;
	public final String API_ROOM_TYPE_FIND_BY_ID = API_ROOM_TYPE + "/{id}";
	public final String API_ROOM_TYPE_UPDATE_BY_ID = API_ROOM_TYPE + "/{id}/update";
	public final String API_ROOM_TYPE_CREATE = API_ROOM_TYPE + "/create";
	public final String API_ROOM_TYPE_DELETE_BY_ID = API_ROOM_TYPE + "/{id}/delete";
	
	public final String API_FOOD = API + "/food";
	public final String API_FOOD_FIND_ALL = API_FOOD;
	public final String API_FOOD_FIND_BY_ID = API_FOOD + "/{id}";
	public final String API_FOOD_UPDATE_BY_ID = API_FOOD + "/{id}/update";
	public final String API_FOOD_CREATE = API_FOOD + "/create";
	public final String API_FOOD_DELETE_BY_ID = API_FOOD + "/{id}/delete";
	
	public final String API_ORDER = API + "/order";
	public final String API_ORDER_CHECKIN = API_ORDER + "/checkin/{roomId}";
	public final String API_ORDER_CHECKOUT = API_ORDER + "/checkout/{roomId}";
	public final String API_ORDER_FIND_BILL = API_ORDER + "/findBill/{roomId}";
	public final String API_ORDER_ADD_BILL_DETAILS = API_ORDER + "/addbilldetail/{roomId}";
	public final String API_ORDER_ADD_LIST_BILL_DETAILS = API_ORDER + "/addlistbilldetail/{roomId}";
	public final String API_ORDER_DELETE_BILL_DETAILS = API_ORDER + "/deletebilldetail/{roomId}";

	public final String API_IMPORT_DATABASE = API + "/import";
	
}
