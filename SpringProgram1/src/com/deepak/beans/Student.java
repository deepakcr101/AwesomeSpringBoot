package com.deepak.beans;

public class Student {
   private String name;
   private int rollNo;
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
	   System.out.println("Nmae: "+ this.name);
	   System.out.println("Roll No : "+ this.rollNo);
	   System.out.println("Email Id : "+ this.email);
   }
}
