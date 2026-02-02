package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.deepak.beans.Student;
import com.deepak.resources.SpringConfig;

public class Main {
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		Student stdStudent = (Student) context.getBean("createStudent");
		stdStudent.display();
	}
}
