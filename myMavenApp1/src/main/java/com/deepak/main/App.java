package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deepak.entities.Student;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello My First Maven Project");
		String config_file_pathString = "/com/deepak/resources/applicationContext.xml";
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(config_file_pathString);

		Student std1 = (Student) context.getBean(Student.class);
		std1.display();
	}
}
