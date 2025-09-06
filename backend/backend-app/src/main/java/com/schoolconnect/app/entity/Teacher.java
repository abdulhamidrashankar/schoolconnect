package com.schoolconnect.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String name;
	private String surname;
	private String gender;
	private String email_address;
	private String phone_number;
	private Integer user_id;

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", surname=" + surname + ", gender=" + gender
				+ ", email_address=" + email_address + ", phone=" + phone_number + ", user_id=" + user_id + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone) {
		this.phone_number = phone;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
