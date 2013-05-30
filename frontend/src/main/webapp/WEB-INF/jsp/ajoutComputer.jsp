<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
			<a href="affichageComputers.html"> My super Computer database </a>
		</h1>
	</header>

	<section id="main">


		<h1>Add computer</h1>



		<form:form action="ajoutComputer.html" commandName= "computer" method="POST">


			<fieldset>


				<div class="clearfix <c:if test="${!empty result.getFieldError('name')}"> error</c:if>">
					<label for="name">Computer name</label>
					<div class="input">

						<form:input type="text" id="name" path="name"/> 
						<span class="help-inline">Required</span>
					</div>
				</div>



				<div class="clearfix <c:if test="${!empty result.getFieldError('introduced')}"> error</c:if>">
					<label for="introduced">Introduced date</label>
					<div class="input">

						<form:input type="text" id="introduced" path="introduced" />

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="clearfix <c:if test="${!empty result.getFieldError('discontinued')}"> error</c:if>">
					<label for="discontinued">Discontinued date</label>
					<div class="input">

						<form:input type="text" id="discontinued" path="discontinued"/>

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>


				<div class="clearfix">
					<label for="company">Company</label>
					<div class="input">

						<form:select id="company" path="company">

							<form:option class="blank" value="">-- Choose a company --</form:option>
							<form:options items="${companies}" itemValue="id" itemLabel="name"/>

						</form:select> <span class="help-inline"></span>
					</div>
				</div>




			</fieldset>

			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary">
				or <a href="affichageComputers.html" class="btn">Cancel</a>
			</div>


		</form:form>

	</section>

</body>
</html>