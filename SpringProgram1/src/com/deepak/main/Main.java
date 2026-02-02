package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deepak.beans.Student;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String config_loc = "/com/deepak/resources/applicationContext.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(config_loc);

		Student std = (Student) context.getBean("stdId1");
		std.display();

		System.out.println("---------------------------------");
		Student std1 = (Student) context.getBean("stdId2");
		std1.display();

	}
}
