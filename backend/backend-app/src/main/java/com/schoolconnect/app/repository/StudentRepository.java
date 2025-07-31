package com.schoolconnect.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    long deleteByEnrollmentNumber(String enrollmentNumber); 

    Optional<Student> findByEnrollmentNumber(String enrollmentNumber); 
}

