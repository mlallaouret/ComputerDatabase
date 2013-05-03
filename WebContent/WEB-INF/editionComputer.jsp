<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<a href="/"> Play 2.0 sample application &mdash; Computer
				database </a>
		</h1>
	</header>

	<section id="main">


		<h1>Edit computer</h1>



		<form action="/computers/381" method="POST">


			<fieldset>


				<div class="clearfix ">
					<label for="name">Computer name</label>
					<div class="input">

						<input type="text" id="name" name="name" value="${c.name}"> <span
							class="help-inline">Required</span>
					</div>
				</div>



				<div class="clearfix ">
					<label for="introduced">Introduced date</label>
					<div class="input">

						<input type="text" id="introduced" name="introduced" value="">

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="clearfix ">
					<label for="discontinued">Discontinued date</label>
					<div class="input">

						<input type="text" id="discontinued" name="discontinued" value="">

						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>


				<div class="clearfix ">
					<label for="company">Company</label>
					<div class="input">

						<select id="company" name="company">

							<option class="blank" value="">-- Choose a company --</option>

							<c:forEach items="${companies}" var="company">		
								<option value="${company.id }">${$company.name }</option>
							</c:forEach>

						</select> <span class="help-inline"></span>
					</div>
				</div>




			</fieldset>

			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary">
				or <a href="/ComputerDatabase/index.html" class="btn">Cancel</a>
			</div>


		</form>




		<form action="/computers/381/delete" method="POST" class="topRight">

			<input type="submit" value="Delete this computer" class="btn danger">

		</form>



	</section>

</body>
</html>