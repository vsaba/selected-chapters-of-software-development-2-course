<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%!

private String calculateTime(HttpServletRequest request){
	
	long startupTime = (long) request.getServletContext().getAttribute("startup");
	long duration = System.currentTimeMillis() - startupTime;
	
	String format = String.format("%02d days %02d hours %02d minutes %02d seconds ", 
			TimeUnit.MILLISECONDS.toDays(duration),
			TimeUnit.MILLISECONDS.toHours(duration) % 24,
			TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
			TimeUnit.MILLISECONDS.toSeconds(duration) % 60);
	
	SimpleDateFormat formatter = new SimpleDateFormat("SSS");
	
	format += formatter.format(new Date(duration)) + " milliseconds";
	
	
	return format;
}


%>
    

<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Server info</title>
	</head>
	<body>
	
	<h1>The time that the server has been running</h1>
	<p>The server has been running for: <%= calculateTime(request)%></p>
	
	<a href="../index.jsp">Back to the home page</a>
	
	</body>
</html>