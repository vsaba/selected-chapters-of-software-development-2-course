<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Choose a poll</title>
	</head>
	<body>
		<h1>Choose a poll to vote in:</h1>
		<ol>
		<% 
		@SuppressWarnings("unchecked")
		List<Poll> polls = (List<Poll>) request.getAttribute("polls");
		
		for(Poll poll : polls){%>
			<li><a href="../servleti/glasanje?pollID=<%=poll.getId()%>"><%= poll.getTitle() %></a> 
		<% } %>
		</ol>
	</body>
</html>