<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<style>
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Home</title>
	</head>
	<body>
		<h1>Hello there</h1>
		
		
		<a href="setcolor">Background color chooser</a><br><br>
		<a href="trigonometric?a=0&b=90">Get basic trigonometric values</a><br><br>
		
		<form action="trigonometric" method="GET">
 			Starting angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Ending angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 		<input type="submit" value="Table"><input type="reset" value="Reset">
		</form><br><br>
		
		<a href="stories/funny.jsp">Click here to read a funky funny story</a><br><br>
		
		<a href="reports/report.jsp">The history of OS usage</a><br><br>
		
		<a href="powers?a=1&b=100&n=3">Generate Microsoft Excel document</a><br><br>
		
		<form action="powers" method="GET">
			Starting number: <input type="number" name="a" min="-100" max="100" value="1"><br>
			Ending number: <input type="number" name="b" min="-100" max="100" value="100"><br>
			Number of sheets: <input type="number" name="n" min="1" max="5" value="3"><br>
		<input type="submit" value="Create Excel"><input type="reset" value="Reset">
		</form><br><br>
		
		<a href="info/appinfo.jsp">Server properties</a><br><br>
		
		<a href="glasanje">Vote for your favourite band</a>
	</body>
</html>