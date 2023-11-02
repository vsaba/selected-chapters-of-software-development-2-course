<%@page import="java.util.ArrayList"%>
<%@page import="hr.fer.zemris.java.p12.model.PollOption"%>
<%@page import="java.util.List"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
public List<PollOption> getLeadingOptions(HttpServletRequest request){
	
	@SuppressWarnings("unchecked")
	List<PollOption> pollOptions = (List<PollOption>) request.getAttribute("pollOptions");
	List<PollOption> leadingOptions = new ArrayList<>();
	
	long maxVote = pollOptions.get(0).getVotesCount();
	
	for(PollOption option : pollOptions){
		if(Long.compare(option.getVotesCount(), maxVote) == 0){
			leadingOptions.add(option);
		}
	}
	
	return leadingOptions;
}

%> 

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Voting results!</title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		
		<a href="index.html">Back to home page</a>
		
		<p>Ovo su rezultati glasanja.</p>
		
	 	<table border="1" class="rez">
		 	<thead><tr><th>Bendovi</th><th>Broj glasova</th></tr></thead>
		 	<tbody>
			 	<%
				@SuppressWarnings("unchecked")
			 	List<PollOption> pollOptions = (List<PollOption>) request.getAttribute("pollOptions");

			 	for(PollOption option : pollOptions){%>
			 		<tr><td><%= option.getName() %></td><td><%= option.getVotesCount() %></td></tr>
			 	<%}%>
		 	</tbody>
	 	</table>
	 	
	 	<h2>Grafički prikaz rezultata</h2>
 		<img alt="Pie-chart" src="../servleti/glasanje-grafika?pollID=<%= pollOptions.get(0).getPollId()%>" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="../servleti/glasanje-xls?pollID=<%= pollOptions.get(0).getPollId()%>">ovdje</a></p>
	
	 	<h2>Razno</h2>
	 	<p>Primjeri pjesama pobjedničkih bendova:</p>
	 	<ul>			
			<%
			List<PollOption> leadingOptions = getLeadingOptions(request);
			for(PollOption option : leadingOptions){%>
				<li><a href="<%=option.getLink() %>" target="_blank"><%=option.getName()%></a></li>
			<%}%>
	 	</ul>
	</body>
</html>