<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Colors</title>
	</head>
	<body>
		<h1>Select a color</h1>
		<a href="setcolor?pickedBgCol=white">WHITE</a>
		<br>
		<a href="setcolor?pickedBgCol=red">RED</a>
		<br>
		<a href="setcolor?pickedBgCol=green">GREEN</a>
		<br>
		<a href="setcolor?pickedBgCol=cyan">CYAN</a>
		<br>
		<br>
		
		<a href="index.jsp">Back to the home page</a>
	</body>
</html>