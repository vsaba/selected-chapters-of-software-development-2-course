<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>OS usage</title>
	</head>
	<body>
		<p>Here are the results of OS usage in a survey that we completed</p>
		
		
		<img src="../reportImage"><br><br>
		
		<a href="../index.jsp">Return to the home page</a>
		
	</body>
</html>