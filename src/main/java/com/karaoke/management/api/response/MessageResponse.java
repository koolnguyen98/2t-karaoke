package com.karaoke.management.api.response;

public class MessageResponse {
	
	String Message;
	
	int code;

	public MessageResponse(String message, int code) {
		super();
		Message = message.trim();
		this.code = code;
	}
	
	public MessageResponse() {
		super();
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message.trim();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
