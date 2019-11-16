package com.karaoke.management.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.Urls;
import com.karaoke.management.api.request.LoginRequest;
import com.karaoke.management.api.request.SignUpRequest;
import com.karaoke.management.api.security.JwtTokenProvider;
import com.karaoke.management.service.UserAccountService;

@RestController

public class AuthentController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @PostMapping(value= Urls.API_AUTHENTICATION_SIGNIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userAccountService.userSigin(loginRequest);
    }
    
	@PostMapping(value=Urls.API_AUTHENTICATION_SIGUP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userAccountService.userSigup(signUpRequest);
    }
    
}
