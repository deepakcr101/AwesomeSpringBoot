package com.deepak.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepak.beans.Student;

@Configuration
public class SpringConfiFile {

	@Bean
	public Student stdId1() {
		Student std = new Student();
		std.setName("Suresh");
		std.setRollNo(123);
		std.setEmail("suresh@gmail.com");
		return std;
	}
}
