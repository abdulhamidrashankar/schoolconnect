package com.schoolconnect.app.controller;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.schoolconnect.app.entity.Role;
import com.schoolconnect.app.entity.User;
import com.schoolconnect.app.repository.RoleRepository;
import com.schoolconnect.app.repository.UserRepository;

@Component
public class StartupAdminUserInitializer {

	@Bean
	public CommandLineRunner initDefaultUser(UserRepository userRepository, 
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("root").isEmpty()) {
				Optional<Role> optionalRole = roleRepository.findByName("superadmin");

				Role adminRole = optionalRole.orElseGet(() -> {
				    Role newRole = new Role();
				    newRole.setName("superadmin");
				    return roleRepository.save(newRole); // this must return a Role
				});


				User admin = new User();
				admin.setUsername("root");
				admin.setPassword(passwordEncoder.encode("root123")); // encrypted password
				admin.setRole(adminRole);
				admin.setIsblocked(false);
				userRepository.save(admin);

				System.out.println("✅ Admin user created: root / root123");
			}
		};
	}
}
