<%@page import="hr.fer.zemris.java.tecaj_13.forms.RegisterForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Register!</title>
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
		<h1>Register for a new profile:</h1>
		
		<%
		RegisterForm form = (RegisterForm) request.getAttribute("form");
		
		if(form == null){
			form = new RegisterForm();
		}
		
		%>
		
		<form action="../servleti/register" method="post">
			<div>
			 <div>
			  <span class="formLabel">First name:</span><input type="text" name="firstName" value='<%=form.getFirstName() %>' size="20" required="required">
			 </div>
			</div>
			
			<%if(form.errorsExist()){%>
			  <div class="greska"><%=form.getError("firstName") %></div>
			<%} %>
			
			<div>
			 <div>
			  <span class="formLabel">Last name:</span><input type="text" name="lastName" value='<%=form.getLastName() %>' size="20" required="required">
			 </div>
			</div>
			
			<%if(form.errorsExist()){%>
			  <div class="greska"><%=form.getError("lastName") %></div>
			<%} %>
			
			<div>
			 <div>
			  <span class="formLabel">Email:</span><input type="text" name="email" value='<%=form.getEmail()%>' size="20" required="required">
			 </div>
			</div>
			
			<%if(form.errorsExist()){%>
			  <div class="greska"><%=form.getError("email") %></div>
			<%} %>
			
			<div>
			 <div>
			  <span class="formLabel">Nickname</span><input type="text" name="nick" value='' size="20" required="required">
			 </div>
			</div>
			
			<%if(form.errorsExist()){%>
			  <div class="greska"><%=form.getError("nick") %></div>
			<%} %>
			
			<div>
			 <div>
			  <span class="formLabel">Password</span><input type="password" name="password" value='' size="20" required="required">
			 </div>
			</div>
			
			<input type="submit" value="Register">
		</form>
	</body>
</html>