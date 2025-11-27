<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Add Owner</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/addOwner.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet" />
</head>

<body>
	<%@ include file="../../jsp/include/header.jsp"%>
	<div class="content">
		<div id="addOwner">
			<form action="${pageContext.request.contextPath}/admin/owner/add" method="POST">
				<div class="heading">
					<p>Add Owner</p>
				</div>
				<div class="form-section">
					<label>User Details</label>
					<div id="user" class="input">
						<div>
							<input type="text" name="username" placeholder="Username"
								required minlength="3" maxlength="20"
								pattern="[A-Za-z0-9_]{3,20}"
								title="3-20 characters. Letters, numbers, and underscores only." />
						</div>
						<div>
							<input type="password" name="password" placeholder="Password"
								required minlength="6" maxlength="32"
								title="Minimum 6 characters recommended." />
						</div>
					</div>
				</div>
				<div class="form-section">
					<label>Name</label>
					<div id="name" class="input">
						<div>
							<input type="text" name="fname" placeholder="First Name" required
								pattern="[A-Za-z ]{2,30}" title="Only letters allowed." />
						</div>
						<div>
							<input type="text" name="mname" placeholder="Middle Name"
								pattern="[A-Za-z ]{0,30}" title="Only letters allowed." />
						</div>
						<div>
							<input type="text" name="lname" placeholder="Last Name" required
								pattern="[A-Za-z ]{2,30}" title="Only letters allowed." />
						</div>
					</div>
				</div>
				<div class="form-section">
					<label>Additional Details</label>
					<div id="additional" class="input">
						<div>
							<input type="text" name="phone" placeholder="Phone Number"
								required pattern="\d{10}" maxlength="10"
								title="Enter a valid 10-digit phone number." />
						</div>
						<div>
							<input type="text" name="address" placeholder="Address" required
								minlength="5" maxlength="200" />
						</div>
						<div>
							<input type="text" name="aadhaar" placeholder="Aadhaar" required
								pattern="\d{12}" maxlength="12"
								title="Enter a 12-digit Aadhaar number." />
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
