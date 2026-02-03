package com.deepak.entities;

public class User {
	int id;
	private String username;
	private String email;
	private String password;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void display() {
		System.out.print("ID: " + this.id + " ");
		System.out.print("Username: " + this.username + " ");
		System.out.print("Email: " + this.email + " ");
		System.out.println("Password: " + this.password + " ");
	}
}
