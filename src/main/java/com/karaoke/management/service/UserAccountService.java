package com.karaoke.management.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karaoke.management.api.request.LoginRequest;
import com.karaoke.management.api.request.SignUpRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.JwtAuthenticationResponse;
import com.karaoke.management.api.security.JwtTokenProvider;
import com.karaoke.management.api.security.UserDetailsImp;
import com.karaoke.management.entity.UserAccount;
import com.karaoke.management.reponsitory.RoleRepository;
import com.karaoke.management.reponsitory.UserAccountRepository;

@Service
public class UserAccountService {
	
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtTokenProvider tokenProvider;
    
    public UserAccount createAccount(SignUpRequest signUpRequest) {
    	// Creating user's account
        UserAccount userAccount = new UserAccount(signUpRequest.getUsername(), 
        		signUpRequest.getPassword(), 
        		signUpRequest.getName(), 
        		roleRepository.findByLevel(signUpRequest.getLevel()));

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        UserAccount result = userAccountRepository.save(userAccount);
    	return result;
    }
    
    public ResponseEntity<JwtAuthenticationResponse> userSigin(LoginRequest loginRequest) {
    	
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((UserDetailsImp) authentication.getPrincipal());
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    public ResponseEntity<?> userSigup(SignUpRequest signUpRequest){
    	
    	if(checkUserAccount(signUpRequest.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        UserAccount userAccount = createAccount(signUpRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(userAccount.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
        
    }
    
    private boolean checkUserAccount(String username) {
    	boolean checkUserAccount = userAccountRepository.existsByUserName(username);
    	return checkUserAccount;
    }
	
}
