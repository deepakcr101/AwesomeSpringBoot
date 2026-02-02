package com.deepak.beans;

public class Student {
	private String name;
	private int rollNo;
	private String email;
	private Address address;

	public Student(String name, int roll, String em, Address ad) {
		this.name = name;
		this.rollNo = roll;
		this.email = em;
		this.address = ad;
	}

	public void display() {
		System.out.println("Name : " + this.name);
		System.out.println("Roll No: " + this.rollNo);
		System.out.println("Email Id: " + this.email);
		this.address.display();
	}
}
