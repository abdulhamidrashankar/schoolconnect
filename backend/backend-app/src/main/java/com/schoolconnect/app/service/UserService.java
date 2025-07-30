package com.schoolconnect.app.service;

import java.util.Optional;

import com.schoolconnect.app.entity.User;

public interface UserService {

	Optional<User> findByUsername(String username);

}
