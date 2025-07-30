package com.schoolconnect.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolconnect.app.entity.Student;
import com.schoolconnect.app.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public List<Student> getUser() {
		return studentRepository.findAll();
	}

	@Override
	public Student createUser(Student student) {
		return studentRepository.save(student);
	}

	@Override
	@Transactional
	public Student updateUser(String enrollmentNumber, Student student) throws Exception {
		Student existing = studentRepository.findByEnrollmentNumber(enrollmentNumber).orElseThrow(() -> new Exception("User Not Found!"));

		existing.setFirstName(student.getFirstName());
		existing.setMiddleName(student.getMiddleName());
		existing.setLastName(student.getLastName());
		existing.setDateOfBirth(student.getDateOfBirth());
		existing.setGender(student.getGender());
		existing.setEnrollmentNumber(student.getEnrollmentNumber());
		existing.setEnrollmentDate(student.getEnrollmentDate());

		return studentRepository.save(existing);
	}

	@Override
	@Transactional // REQUIRED for derived deleteBy...
	public void deleteStudent(String enrollmentNumber) {
		long deleted = studentRepository.deleteByEnrollmentNumber(enrollmentNumber);
		if (deleted == 0) {
			throw new jakarta.persistence.EntityNotFoundException(
					"Student with enrollmentNumber " + enrollmentNumber + " not found");
		}
	}

	@Transactional
	public Student deleteAndReturnByEnrollmentNumber(String enrollmentNumber) {
		Student s = studentRepository.findByEnrollmentNumber(enrollmentNumber)
				.orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Student not found"));
		studentRepository.delete(s);
		return s;
	}

}
