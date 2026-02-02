package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.deepak.beans.Student;
import com.deepak.resources.SpringConfigFile;

public class Main {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigFile.class);
		Student std1 = (Student) context.getBean("student");

		std1.display();
	}
}
