<%@page import="hr.fer.oprpp2.hw04.band.Band"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%!
public List<Band> getLeadingBand(HttpServletRequest request){
	
	@SuppressWarnings("unchecked")
	List<Band> bands = (List<Band>) request.getAttribute("bands");
	List<Band> leadingBands = new ArrayList<>();
	
	int maxVote = bands.get(0).getVotes();
	
	for(Band band: bands){
		if(band.getVotes() == maxVote){
			leadingBands.add(band);
		}
	}
	
	return leadingBands;
}

%>   
    
 
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
 			table.rez td {text-align: center;}
			body {background-color: #<%= session.getAttribute("pickedBgCol") == null ? "FFFFFF" : session.getAttribute("pickedBgCol") %>}
		</style>
		<meta charset="UTF-8">
		<title>Voting results</title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		
		<a href="index.jsp">Back to the home page</a>
		
		<p>Ovo su rezultati glasanja.</p>
		
	 	<table border="1" class="rez">
		 	<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
		 	<tbody>
			 	<%
				@SuppressWarnings("unchecked")
				List<Band> bands = (List<Band>) request.getAttribute("bands");
			 	
			 	for(Band band: bands){%>
			 		<tr><td><%= band.getName() %></td><td><%=band.getVotes() %></td></tr>
			 	<%}%>
		 	</tbody>
	 	</table>
	 	
	 	<h2>Grafički prikaz rezultata</h2>
 		<img alt="Pie-chart" src="glasanje-grafika" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
	
	 	<h2>Razno</h2>
	 	<p>Primjeri pjesama pobjedničkih bendova:</p>
	 	<ul>			
			<%
			List<Band> leadingBands = getLeadingBand(request);
			for(Band band: leadingBands){%>
				<li><a href="<%=band.getSong() %>" target="_blank"><%=band.getName()%></a></li>
			<%}%>
	 	</ul>
	</body>
</html>