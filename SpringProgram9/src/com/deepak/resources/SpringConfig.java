package com.deepak.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepak.beans.Address;
import com.deepak.beans.Student;

@Configuration
public class SpringConfig {

	@Bean
	public Address createAddress() {
		Address addr1 = new Address(546, "Rohini", "NEW Delhi", 110065, "Delhi");
		return addr1;
	}

	@Bean
	public Student createStudent() {
		Student std1 = new Student("Suresh Beta", 45, "suresh@gmail.com", createAddress());
		return std1;
	}
}
