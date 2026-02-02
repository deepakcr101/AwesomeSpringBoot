package com.deepak.beans;

public class Student {
	private String name;
	private int rollNo;
	private String email;
	private Subjects subjects;
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

	public void setAddress(Address adr) {
		this.address = adr;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public void display() {
		System.out.println("Name : " + this.name);
		System.out.println("Roll No: " + this.rollNo);
		System.out.println("Email Id: " + this.email);
		this.address.display();
		System.out.println(this.subjects.toString());
	}
}
