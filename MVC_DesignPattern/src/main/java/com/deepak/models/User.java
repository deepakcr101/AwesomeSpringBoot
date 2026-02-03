package com.deepak.models;

public class User {
	private String name;
	private String email;
	private String mobileNo;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String displayUser() {
		return "Name: " + name + ", Email: " + email + ", Mobile No: " + mobileNo + ", Age: " + age;
	}
}
