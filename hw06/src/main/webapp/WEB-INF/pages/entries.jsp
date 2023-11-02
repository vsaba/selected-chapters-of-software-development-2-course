<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Blogs of the selected user</title>
	</head>
		
	<body>
	
		<% if(request.getSession().getAttribute("current.user.id") == null) {%>
			<div>Not logged in</div>
			<a href="/blog/servleti/register">Register</a>
		<%} else{%>
			<div>First name: <%=request.getSession().getAttribute("current.user.fn") %>,
			 Last name: <%=request.getSession().getAttribute("current.user.ln")%>,
			 Nickname: <%=request.getSession().getAttribute("current.user.nick") %>
			 </div>
			<a href="/blog/servleti/logout">Log out</a>
		<%} %>
		<br><br>
	
		<%
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) request.getAttribute("entries");
		
		if(entries == null){%>
			<h1>Nema unosa!</h1>
		<%} else {%>
		<ol>
		<%
		for(BlogEntry entry : entries){%>
			<li><a href="<%=entry.getCreator().getNick() + "/" + entry.getId()%>"><%=entry.getTitle()%></a></li>
		<%}
		}%>
		</ol>
		<%boolean loggedIn = (boolean) request.getAttribute("loggedIn");
		if(loggedIn){%>
			<a href="<%=(String)session.getAttribute("current.user.nick")%>/new">Add a new blog</a>
		<%}%>
	
	</body>
</html>