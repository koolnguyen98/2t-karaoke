package com.karaoke.management.api.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequest() {
		super();
	}

	public LoginRequest(@NotBlank String username, @NotBlank String password) {
		super();
		this.username = username.trim();
		this.password = password.trim();
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
