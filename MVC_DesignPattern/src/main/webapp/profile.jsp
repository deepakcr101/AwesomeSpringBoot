<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.deepak.models.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Profile</title>
</head>
<body>
     <% 
       User myuser = (User) session.getAttribute("user");
       if (myuser == null) {
           response.sendRedirect("login.html"); // if session expired or not logged in
           return;
       }
     %>
     
     <h1>My Profile</h1>
     <p>Welcome, <strong><%= myuser.getName() %></strong>!</p>
     <p>Email: <strong><%= myuser.getEmail() %></strong></p>
     <p>Phone: <strong><%= myuser.getMobileNo() %></strong></p>
     <p>Age: <strong><%= myuser.getAge() %></strong></p>
     
     <a href="Logout">LogOut</a>
</body>
</html>
