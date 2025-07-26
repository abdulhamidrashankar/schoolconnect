package com.schoolconnect.app.controller;

import com.schoolconnect.app.entity.Student;
import com.schoolconnect.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
}
