package com.schoolconnect.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.schoolconnect.app.entity.User;
import com.schoolconnect.app.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	public CustomUserDetailsService() {
		
	}

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Load user from DB and convert to Spring Security's UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).roles(user.getRole().getName().toUpperCase())
				.accountLocked(user.getIsblocked()).build();
	}
}