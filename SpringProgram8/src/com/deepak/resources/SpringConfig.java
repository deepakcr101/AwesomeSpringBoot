package com.deepak.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepak.beans.Address;
import com.deepak.beans.Student;

@Configuration
public class SpringConfig {

	@Bean
	public Address createAddressObj() {
		Address adr = new Address();
		adr.setHouseNo(140);
		adr.setLocality("Rohini Sector 34");
		adr.setCity("New Delhi");
		adr.setPinCode(110045);
		adr.setState("Delhi");
		return adr;
	}

	@Bean
	public Student stdId1() {
		Student std = new Student();
		std.setName("Ramesh Sharma");
		std.setRollNo(67);
		std.setEmail("rameshbhai@gmail.com");
		std.setAddress(createAddressObj());
		return std;
	}
}
