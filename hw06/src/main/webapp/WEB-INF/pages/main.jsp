<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="hr.fer.zemris.java.tecaj_13.forms.LoginForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Welcome to the best blogging site on the internet</title>
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
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
		
		
		<h1>Welcome to the blog!</h1>
		
		<%
		LoginForm form = (LoginForm) request.getAttribute("form"); 
		String previousNick = "";
		boolean error = false;
		if(form != null){
			previousNick = form.getNick();
			error = form.isError();
		}
		%>
		<h2>Log in:</h2>
		<form action="../servleti/login" method="post">
			<div>
			 <div>
			  <span class="formLabel">Nickname</span><input type="text" name="nick" value='<%=previousNick %>' size="20" required="required">
			 </div>
			</div>
			<div>
			 <div>
			  <span class="formLabel">Password</span><input type="password" name="password" value='' size="20" required="required">
			 </div>
			 <%if(error) {%>
			  <div class="greska">Incorrect username or password</div>
			 <%} %>
			</div>
			<input type="submit" value="Log In">
		</form>
		
		<br><br>
		
		<h2>Sign up here:</h2>
		<a href="../servleti/register">Sign up!</a>
		
		<br><br>
		
		<h2>Registered users:</h2>
		<ol>
		<% 
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) request.getAttribute("allUsers");
		
		if(users == null){
			users = new ArrayList<>();
		}
		
		for(BlogUser user : users){%>
			<li><a href="../servleti/author/<%=user.getNick()%>"><%=user.getNick()%></a> 
		<% } %>
		</ol>
		
		
		
	</body>
</html>