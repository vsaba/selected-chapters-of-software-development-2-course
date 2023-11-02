<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="hr.fer.zemris.java.p12.model.PollOption"%>
<%@page import="java.util.List"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Vote!</title>
	</head>
	<body>
	<%Poll poll = (Poll) request.getAttribute("poll"); %>
	<h1><%= poll.getTitle() %></h1>
 	<p><%= poll.getMessage() %></p>
 	<ol>
	 	<%
	 	@SuppressWarnings("unchecked")
	 	List<PollOption> pollOptions = (List<PollOption>) request.getAttribute("pollOptions");
	 	
	 	for(PollOption option : pollOptions){%>
	 		<li><a href="../servleti/glasanje-glasaj?id=<%=option.getId()%>&pollID=<%=poll.getId()%>"><%= option.getName() %></a>
	 	<%}%>
	 	
 	</ol>
	</body>
</html>