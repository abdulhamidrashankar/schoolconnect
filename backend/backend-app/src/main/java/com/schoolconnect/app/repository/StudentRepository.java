package com.schoolconnect.app.repository;

import com.schoolconnect.app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Basic CRUD methods are auto-implemented
}
