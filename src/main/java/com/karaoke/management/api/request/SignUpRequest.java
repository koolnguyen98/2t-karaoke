package com.karaoke.management.api.request;

import javax.validation.constraints.*;

public class SignUpRequest {
   
    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;
    
    @NotBlank
    @Size(max = 100)
    private String name;

    public SignUpRequest() {
		super();
	}

	public SignUpRequest(@NotBlank @Size(max = 50) String username, @NotBlank @Size(max = 100) String password,
			@NotBlank @Size(max = 100) String name) {
		super();
		this.username = username.trim();
		this.password = password.trim();
		this.name = name.trim();
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
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