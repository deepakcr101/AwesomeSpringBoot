package com.deepak.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class Student {
	private String name;
	private int rollNo;
	private String email;
	@Autowired
	private Subjects subjects;

	@Autowired
	private Address address;

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

	public String getEmailString() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void display() {
		System.out.println("Name : " + this.name);
		System.out.println("Roll No: " + this.rollNo);
		System.out.println("Email Id: " + this.email);
		this.address.display();
		System.out.println(this.subjects.toString());
	}
}
