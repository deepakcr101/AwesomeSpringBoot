<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Profile</title>
</head>
<body>
     <h1>Profile Information</h1>
     <h3>Your Name: <strong>${user.getName()}</strong></h3>    
     <h3>Your Name: <strong>${user.getEmail()}</strong></h3>    
     <h3>Your Name: <strong>${user.getMessage()}</strong></h3>  
     
     <p>Thanks for your response<p/>
        
</body>
</html>