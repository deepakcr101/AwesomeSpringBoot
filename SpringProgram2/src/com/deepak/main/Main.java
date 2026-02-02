package com.deepak.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.deepak.beans.Student;
import com.deepak.resources.SpringConfiFile;
public class Main {
   
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context =new AnnotationConfigApplicationContext(SpringConfiFile.class);
        Student std1= (Student) context.getBean("stdId1");
		std1.display();
	}
}
