package com.deepak.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.deepak.dbConnection.DatabaseConnection;
import com.deepak.models.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = { "/login" })
public class LogInController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		try {
			String loginid = req.getParameter("loginId");
			String password = req.getParameter("password");

			try {
				Connection connection = DatabaseConnection.getConnection();
				String selectQueryString = "SELECT * FROM users WHERE (email = ? OR mobile_no = ?) AND password = ?";
				PreparedStatement ps = connection.prepareStatement(selectQueryString);
				ps.setString(1, loginid); // check email
				ps.setString(2, loginid); // check mobile number
				ps.setString(3, password); // check password

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// User found, login successful
					User user = new User();
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setMobileNo(rs.getString("mobile_no"));
					user.setAge(rs.getInt("age"));

					HttpSession session = req.getSession();
					session.setAttribute("user", user);

					RequestDispatcher requestDispatcher = req.getRequestDispatcher("/profile.jsp");
					requestDispatcher.forward(req, response);
				} else {
					// User not found, login failed
					out.println("<h3 style='color:red'> Invalid login credentials!</h3>");
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.html");
					requestDispatcher.include(req, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
