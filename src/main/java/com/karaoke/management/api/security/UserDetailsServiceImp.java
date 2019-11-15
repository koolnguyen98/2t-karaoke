package com.karaoke.management.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.karaoke.management.entity.UserAccount;
import com.karaoke.management.reponsitory.UserAccountRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		// Check user
		UserAccount userAccount = userAccountRepository.findByUserName(username);
		if (userAccount == null) {
			throw new UsernameNotFoundException("User not found with username : " + username);
		}
		return new UserDetailsImp(userAccount);
	}

	// This method is used by JWTAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(int id) {
		// Check user
				UserAccount userAccount = userAccountRepository.findById(id);
				if (userAccount == null) {
					throw new UsernameNotFoundException("User not found with id : " + id);
				}
				return new UserDetailsImp(userAccount);
	}
}
