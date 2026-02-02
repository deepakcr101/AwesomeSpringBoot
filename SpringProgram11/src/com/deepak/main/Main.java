package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deepak.beans.Student;

public class Main {
	public static void main(String[] args) {

		String config_file_location = "/com/deepak/resources/applicationContext.xml";
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(config_file_location);

		Student stdStudent = (Student) context.getBean("std1");
		stdStudent.display();
	}
}
