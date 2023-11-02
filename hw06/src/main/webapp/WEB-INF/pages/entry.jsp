<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<style type="text/css">
			.formLabel {
			   display: inline-block;
			   width: 100px;
			   height: 100px;
	           font-weight: bold;
			   text-align: right;
	           padding-right: 10px;
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

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      
      <c:if test="${loggedIn}">
      <a href="../<%=session.getAttribute("current.user.nick")%>/edit?EID=${blogEntry.id}">Edit blog</a>
      </c:if>
      
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
      
      <br><br>
      
      <form action="../../comment/${blogEntry.id}" method="post">
		<div>
		 <div>
			 <span class="formLabel">Add a comment:</span><input type="text" name="comment" value='' size="50">
		 </div>
		</div>
		<input type="submit" value="Submit">
      </form>
      
    </c:otherwise>
  </c:choose>

  </body>
</html>
