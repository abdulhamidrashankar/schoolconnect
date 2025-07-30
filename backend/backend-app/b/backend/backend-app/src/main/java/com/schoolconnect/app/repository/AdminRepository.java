package com.schoolconnect.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolconnect.app.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
