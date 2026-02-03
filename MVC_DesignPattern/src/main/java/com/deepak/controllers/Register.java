package com.deepak.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.deepak.dbConnection.DatabaseConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(name = "Register", urlPatterns = { "/register" })
public class Register extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");

		String name = req.getParameter("name");
		String emailId = req.getParameter("email");
		String mobileNo = req.getParameter("mobile");
		int age = Integer.parseInt(req.getParameter("age"));
		String password = req.getParameter("password");

		try {

			Connection connection = DatabaseConnection.getConnection();

			PreparedStatement pStatement = connection
					.prepareStatement("INSERT INTO users (name,email,mobile_no,age,password) VALUES (?,?,?,?,?)");

			pStatement.setString(1, name);
			pStatement.setString(2, emailId);
			pStatement.setString(3, mobileNo);
			pStatement.setInt(4, age);
			pStatement.setString(5, password); // Use hashed password here!

			int rowsAffected = pStatement.executeUpdate();
			if (rowsAffected > 0) {
				out.println("<h3 style='color:green'> User registered successfully!</h3>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.html");
				requestDispatcher.include(req, response);
			} else {
				out.println("<h3 style='color:red'> User registration failed!</h3>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/register.html");
				requestDispatcher.include(req, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<h3 style='color:red'> Internal error occurred!</h3>");
		}
	}

}
