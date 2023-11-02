<%@page import="java.util.List"%>
<%@page import="hr.fer.oprpp2.hw04.band.Band"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Vote for a band</title>
	</head>
	<body>
	<h1>Glasanje za omiljeni bend:</h1>
 	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
	glasali!</p>
 	<ol>
	 	<%
	 	@SuppressWarnings("unchecked")
	 	List<Band> bands = (List<Band>) request.getAttribute("bands");
	 	
	 	for(Band band: bands){%>
	 		<li><a href="glasanje-glasaj?id=<%=band.getId()%>"><%= band.getName() %></a>
	 	<%}%>
	 	
 	</ol>
	</body>
</html>