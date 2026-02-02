package com.deepak.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepak.beans.Address;
import com.deepak.beans.Student;
import com.deepak.beans.Subjects;

@Configuration
public class SpringConfigFile {
	@Bean
	public Address createAddress() {
		Address addr = new Address();
		addr.setHouseNo(456);
		addr.setCity("New Delhi");
		addr.setLocality("Rohini West");
		addr.setPinCode(110034);
		addr.setState("Delhi");
		return addr;
	}

	@Bean
	public Subjects createSubjects() {
		Subjects subObj = new Subjects();
		List<String> sub_list = new ArrayList<>();
		sub_list.add("Materials Management");
		sub_list.add("Polymer Waste Management");
		sub_list.add("IPR");
		subObj.setSubjects(sub_list);
		return subObj;
	}

	@Bean
	public Student createStudent() {
		Student std = new Student();
		std.setName("Lovendra Kumar");
		std.setRollNo(67);
		std.setEmail("lovendra@gmail.com");
		// std.setAddress(createAddress()); // manually dependency

		return std;
	}
}
