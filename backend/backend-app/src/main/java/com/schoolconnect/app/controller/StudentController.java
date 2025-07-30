package com.schoolconnect.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.entity.Student;
import com.schoolconnect.app.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	StudentService studentService;

	@GetMapping(value = "/getall", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Student>> getAllStudents() {
		return ResponseEntity.ok(studentService.getUser());
	}

	@PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		Student savedStudent = studentService.createUser(student);
		return ResponseEntity.ok(savedStudent);
	}

	@PutMapping(value = "/{enrollmentNumber}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Student> updateUser(@PathVariable("enrollmentNumber") String enrollmentNumber, @RequestBody Student student)
			throws Exception {
		return ResponseEntity.ok(studentService.updateUser(enrollmentNumber, student));
	}

	@DeleteMapping(value = "/{enrollmentNumber}")
	public ResponseEntity<Void> deleteUser(@PathVariable String enrollmentNumber) {
		studentService.deleteStudent(enrollmentNumber);
		return ResponseEntity.noContent().build();
	}
}