package com.schoolconnect.app.service;

import java.util.List;

import com.schoolconnect.app.entity.Student;

public interface StudentService {
	List<Student> getUser();

	Student createUser(Student student);

	Student updateUser(String enrollmentNumber, Student student) throws Exception;

	void deleteStudent(String enrollmentNumber);
}
