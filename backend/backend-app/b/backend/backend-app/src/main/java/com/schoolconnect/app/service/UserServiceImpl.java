package com.schoolconnect.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.User;
import com.schoolconnect.app.repository.UserRepository;

@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	

}
