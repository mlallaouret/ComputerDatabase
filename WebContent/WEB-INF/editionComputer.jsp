<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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


		<h1>Edit computer</h1>



		<form action="/ComputerDatabase/validation" method="POST">

			<input type="hidden" name="id" value="${param.id }"/>
			<fieldset>

				
				<div class="clearfix ">
					<label for="name">Computer name</label>
					<div class="input">

						<input type="text" id="name" name="name" value="${computer.name}"> <span
							class="help-inline">Required</span>
					</div>
				</div>



				<div class="clearfix ">
					<label for="introduced">Introduced date</label>
					<div class="input">

						<input type="text" id="introduced" name="introduced" value="<fmt:formatDate value="${computer.introduced }" pattern="yyyy-MM-dd" />">

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="clearfix ">
					<label for="discontinued">Discontinued date</label>
					<div class="input">

						<input type="text" id="discontinued" name="discontinued" value="<fmt:formatDate value="${computer.discontinued }" pattern="yyyy-MM-dd" />">

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>


				<div class="clearfix ">
					<label for="company">Company</label>
					<div class="input">

						<select id="company" name="company">

							<option class="blank" value="">-- Choose a company --</option>

							<c:forEach items="${companies}" var="company">		
								<option value="${company.id }" <c:if test="${company.id==computer.company.id }">selected </c:if> >${company.name }</option>
							</c:forEach>

						</select> <span class="help-inline"></span>
					</div>
				</div>




			</fieldset>

			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary">
				or <a href="/ComputerDatabase/index" class="btn">Cancel</a>
			</div>


		</form>




		<form action="/ComputerDatabase/delete" method="POST" class="topRight">
			<input type="hidden" value="${computer.id}" name="id" />
			<input type="submit" value="Delete this computer" class="btn danger"/>

		</form>



	</section>

</body>
</html>