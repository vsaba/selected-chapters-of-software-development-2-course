<%@page import="java.util.Random"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%!

	private static final String[] colors = {"000000", "FF80ED", "5AC18E", "40E0D0", "008080",
			"800000", "FA8072", "7FFFD4", "00FF00", "FF00FF", "00CED1"};

	public String getRandomColor(){
		
		Random r = new Random();
		
		//stavljeno colors.lengthj - 1 jer inace baca indexOutOfBounds exception
		return colors[r.nextInt((colors.length - 1 - 0) + 1)];
	}
%>    
    

<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>A funny story</title>
	</head>
	<body>
		<h1>A funny story</h1>
		<p style="color:#<%=getRandomColor()%>">Rest in peace boiling water. You will be mist!</p><br><br>
		<a href="../index.jsp">Back to the home page</a>
	</body>
</html>