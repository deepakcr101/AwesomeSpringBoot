package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deepak.beans.Student;

public class Main {

	public static void main(String args[]) {
		String resource_file_path = "/com/deepak/resources/applicationContext.xml";
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(resource_file_path);

		Student std1 = (Student) context.getBean("student");

		std1.display();
	}
}
