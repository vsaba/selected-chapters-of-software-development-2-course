<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Error</title>
	</head>
	<body>
		<h1><%= request.getAttribute("code") %> error has ocurred</h1>
		<h2><%= request.getAttribute("message") %></h2>
		
		<a href="index.jsp">Return to home page</a>		
	</body>
</html>