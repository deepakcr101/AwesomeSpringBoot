package com.deepak.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deepak.entities.User;
import com.deepak.mappers.UserRowMapper;
import com.deepak.resources.SpringConfigFile;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigFile.class);
		System.out.println("Spring JDBC Application Started");

		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

		// ---------INSERT QUERY------
		/*
		 * String insertQuery =
		 * "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"; String
		 * username = "Ramesh Kumar"; String email = "rameshkumar@gmail.com"; String
		 * password = "rameshpass"; int count = jdbcTemplate.update(insertQuery,
		 * username, email, password);
		 * 
		 * if (count > 0) { System.out.println("User inserted successfully!"); } else {
		 * System.out.println("Failed to insert user."); }
		 */

		// ---------UPDATE QUERY------
		/*
		 * String updateQuery = "UPDATE users SET email = ? WHERE username = ?"; String
		 * newEmail = "amriteshraj123@gmail.com"; String username1 = "Amritesh Raj"; int
		 * updateCount = jdbcTemplate.update(updateQuery, newEmail, username1); if
		 * (updateCount > 0) { System.out.println("User updated successfully!"); } else
		 * { System.out.println("Failed to update user."); }
		 */

		// ---------DELETE QUERY------
		/*
		 * String deleteQuery = "DELETE FROM users WHERE username = ?"; String username2
		 * = "John Doe"; int deleteCount = jdbcTemplate.update(deleteQuery, username2);
		 * if (deleteCount > 0) { System.out.println("User deleted successfully!"); }
		 * else { System.out.println("Failed to delete user."); }
		 */

		// ---------SELECT QUERY------
		String selectQuery = "SELECT * FROM users";
		List<User> userList = jdbcTemplate.query(selectQuery, new UserRowMapper());
		if (userList.isEmpty()) {
			System.out.println("No users found.");
		} else {
			System.out.println("Users found:");
			for (User user : userList) {
				user.display();
			}
		}
	}
}
