<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Add Owner</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminHome.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet" />
</head>

<body>
	<header>
		<div>
			<span>Vehicle Tender Management Portal</span>
		</div>

		<nav class="nav-links">
			<a href="#">Owners</a> <a href="#">Drivers</a> <a href="#">Vehicles</a>
			<a href="#">Tenders</a> <a href="#">Reports</a> <a href="#"
				class="logout">Logout</a>
		</nav>
	</header>

	<div class="content">
		<div id="addOwner">
			<form action="/addOwner" method="POST">
				<div class="heading">
					<p>Add Owner</p>
				</div>
				<div class="form-section">
					<label>User Details</label>
					<div id="user" class="input">
						<div>
							<input type="text" name="username" placeholder="Username" />
						</div>
						<div>
							<input type="password" name="password" placeholder="Password" />
						</div>
					</div>
				</div>
				<div class="form-section">
					<label>Name</label>
					<div id="name" class="input">
						<div>
							<input type="text" name="fname" placeholder="First Name" />
						</div>
						<div>
							<input type="text" name="mname" placeholder="Middle Name" />
						</div>
						<div>
							<input type="text" name="lname" placeholder="Last Name" />
						</div>
					</div>
				</div>
				<div class="form-section">
					<label>Additional Details</label>
					<div id="additional" class="input">
						<div>
							<input type="text" name="phone" placeholder="Phone Number" />
						</div>
						<div>
							<input type="text" name="address" placeholder="Address" />
						</div>
						<div>
							<input type="text" name="Aadhaar" placeholder="Aadhaar" />
						</div>
					</div>
				</div>
				<div id="submit">
					<input type="submit" value="Add Owner" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>
