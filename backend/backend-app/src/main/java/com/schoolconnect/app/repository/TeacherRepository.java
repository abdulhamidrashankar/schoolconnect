package com.schoolconnect.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolconnect.app.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
