<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.excilys.projet.computerdatabase.model.Computer"%>
<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="style/main.css">
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="/"> My super Computer database </a>
		</h1>
	</header>

	<section id="main">

		<h1>${total} computers found</h1>



		<div id="actions">



			<form action="/affichageComputers" method="GET">

				<input type="search" id="searchbox" name="f" value=""
					placeholder="Filter by computer name..."> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">

			</form>


			<a class="btn success" id="add" href="/affichageComputers/new">Add a new
				computer</a>

		</div>



		<table class="computers zebra-striped">
			<thead>
				<tr>

					<th class="col2 header headerSortUp"><a href="/affichageComputers?s=-2">Computer
							name</a></th>


					<th class="col3 header "><a href="/affichageComputers?s=3">Introduced</a>
					</th>


					<th class="col4 header "><a href="/affichageComputers?s=4">Discontinued</a>
					</th>


					<th class="col5 header "><a href="/affichageComputers?s=5">Company</a>
					</th>

				</tr>

			</thead>
			<tbody>

				<c:forEach items="${computers}" var="computer">		
					<tr>
						<td><a href="/ajoutComputer?id=${computer.id}">${computer.name}</a></td>
						<td><em>${computer.introduced}</em></td>
						<td><em>${computer.discontinued}</em></td>
						<td><em>${computer.company.name}</em></td>
					</tr>
				</c:forEach>
				

				


			</tbody>
		</table>

		<div id="pagination" class="pagination">
			<ul>
				<c:if test="${page==0}">
					<li class="prev disabled"><a>&larr; Previous</a></li>
				</c:if>
				<c:if test="${page!=0}">
					<li class="prev"><a href="/ComputerDatabase/index.html?page=${page -1}">&larr; Previous</a></li>
				</c:if>

				<li class="current"><a>Displaying ${page *10 +1} to ${displayTo} of ${total }</a></li>
				
				<c:if test="${last>=1}">
					<li class="next"><a href="/ComputerDatabase/index.html?page=${page +1}">Next &rarr;</a></li>
				</c:if>
				<c:if test="${last<1}">
					<li class="next disabled"><a >Next &rarr;</a></li>
				</c:if>
			</ul>
		</div>





	</section>

</body>
</html>