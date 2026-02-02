package com.deepak.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
	@Value("deepak")
	private String name;
	@Value("53")
	private int rollNo;
	@Value("deepak@gmail.com")
	private String email;

	public Student() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void display() {
		System.out.println("Nmae: " + this.name);
		System.out.println("Roll No : " + this.rollNo);
		System.out.println("Email Id : " + this.email);
	}
}
