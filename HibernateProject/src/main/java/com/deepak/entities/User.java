package com.deepak.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@Column
	private long id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String mobNo;
	@Column
	private int age;

	public User() {
	}

	public User(long id, String name, String email, String mobNo, int age) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobNo = mobNo;
		this.age = age;
	}

	public User(String name, String email, String mobNo, int age) {
		this.name = name;
		this.email = email;
		this.mobNo = mobNo;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void display() {
		System.out.println("Id: " + this.id);
		System.out.println("Name: " + this.name);
		System.out.println("Email: " + this.email);
		System.out.println("Mobile No: " + this.mobNo);
		System.out.println("Age: " + this.age);
	}
}