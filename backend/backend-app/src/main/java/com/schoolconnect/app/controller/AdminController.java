package com.schoolconnect.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.entity.Admin;
import com.schoolconnect.app.repository.AdminRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@GetMapping("/getall")
	public List<Admin> getAllStudents() {
		return adminRepository.findAll();
	}

	@PostMapping("/add")
	public ResponseEntity<Admin> createStudent(@RequestBody Admin admin) {
		Admin savedAdmin = adminRepository.save(admin);
		return ResponseEntity.ok(savedAdmin);
	}

}
