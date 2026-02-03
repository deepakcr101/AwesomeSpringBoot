package com.deepak.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.deepak.resources.SpringConfigFile;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello Maven Project Running !");

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigFile.class);

		NamedParameterJdbcTemplate npJdbcTemplate = context.getBean("npJdbcTemplate", NamedParameterJdbcTemplate.class);

		if (npJdbcTemplate != null) {
			System.out.println("NamedParameterJdbcTemplate bean is successfully created and retrieved.");
		} else {
			System.out.println("Failed to create NamedParameterJdbcTemplate bean.");
		}

		String insertQuery = "INSERT INTO users (username, email, password) VALUES (:username, :email, :password)";
		Map<String, Object> map = new HashMap<>();
		map.put("username", "Devendra Yadav");
		map.put("email", "devendra@example.com");
		map.put("password", "devendrapass");

		int count = npJdbcTemplate.update(insertQuery, map);
		if (count > 0) {
			System.out.println("Data inserted successfully.");
		} else {
			System.out.println("Failed to insert data.");
		}

	}
}
