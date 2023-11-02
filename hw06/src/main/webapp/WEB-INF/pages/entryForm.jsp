<%@page import="hr.fer.zemris.java.tecaj_13.forms.BlogForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Entry form!</title>
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
	
		<%
		BlogForm form = (BlogForm) request.getAttribute("form");
		%>
		<form action="..<%=(String)request.getAttribute("path")%>" method="post">
			<div>
			 <div>
			  <span class="formLabel">Blog title</span><input type="text" name="title" value='<%= form.getTitle()%>' size="20">
			 </div>
			</div>
			
			 <%if(form.errorsExist()) {%>
			 	<div class="greska"><%= form.getError("title") %></div>
			 <%}%>
			
			<div>
			 <div>
			  <span class="formLabel">Blog text</span><input type="text" name="text" value='<%= form.getText()%>' size="20">
			 </div>
			</div>
			
			<%if(form.errorsExist()) {%>
				<div class="greska"><%= form.getError("text") %></div>
			<%}%>

			<input type="submit" value="Submit">
		</form>
	</body>
</html>