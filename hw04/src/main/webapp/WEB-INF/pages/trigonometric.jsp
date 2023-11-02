<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Trigonometric values</title>
	</head>
	<body>
	<h1>The values of sin and cos from <%= request.getAttribute("a")%> to <%= request.getAttribute("b") %></h1>
	
	<a href="index.jsp">Back to the home page</a><br><br>
	
	
	<table style="width: 10%; border: 1px solid black">
		<tr>
			<th>Sin(x)</th>
			<th>Cos(x)</th>
		</tr>
		
		<%
		int a = (int) request.getAttribute("a");
		int b = (int) request.getAttribute("b");
		double[] sinValues = (double[]) request.getAttribute("sinValues");
		double[] cosValues = (double[]) request.getAttribute("cosValues");
		for(int i = 0; i < (b - a); i++){%>
			<tr>
				<td><%=sinValues[i]%> </td>
				<td><%=cosValues[i]%></td>
			</tr>
		<%}%>
	</table>
	</body>
</html>