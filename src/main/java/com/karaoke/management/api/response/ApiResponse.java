package com.karaoke.management.api.response;

public class ApiResponse {
    private Boolean success;
    private String message;
    private Response response;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, String message, Response response) {
		super();
		this.success = success;
		this.message = message;
		this.response = response;
	}
    
    public ApiResponse(Boolean success, Response response) {
		super();
		this.success = success;
		this.response = response;
	}

	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
    
    
}