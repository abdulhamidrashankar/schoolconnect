package com.schoolconnect.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.entity.Teacher;
import com.schoolconnect.app.repository.TeacherRepository;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping("/getall")
	public List<Teacher> getAllStudents() {
		return teacherRepository.findAll();
	}

	@PostMapping("/add")
	public ResponseEntity<Teacher> createStudent(@RequestBody Teacher teacher) {
		Teacher savedteacher = teacherRepository.save(teacher);
		return ResponseEntity.ok(savedteacher);
	}
}
