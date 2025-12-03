<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.vtmp.auth.AuthBean"%>
<%@ page import="com.vtmp.home.admin.owner.dto.OwnerDetails"%>
<%@ page import="com.vtmp.home.admin.owner.OwnerBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Update Owner Details</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/content.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/form.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet" />
</head>

<body>
	<div id="root">
		<%@ include file="../../jsp/include/header.jsp"%>
		<div class="content">
			<%
			if (request.getParameter("oid") == null || request.getAttribute("ownerInfo") == null) {
			%>
			<div id="form">
				<form action="${pageContext.request.contextPath}/admin/owner/edit"
					method="GET">
					<div class="heading">
						<p>Update Owner Details</p>
					</div>
					<div class="form-section">
						<label>Enter Owner ID</label>
						<div id="user" class="input">
							<div>
								<input type="text" name="oid" placeholder="Owner ID" required
									minlength="1" maxlength="20" pattern="[0-9]{1,20}"
									title="Enter a valid Owner ID." />
							</div>
						</div>
					</div>
					<div id="submit">
						<input type="submit" value="Search" />
					</div>
				</form>
			</div>
			<%
			} else {
			OwnerDetails ownerDetails = (OwnerDetails) request.getAttribute("ownerInfo");
			AuthBean user = ownerDetails.getUser();
			OwnerBean owner = ownerDetails.getOwner();
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/admin/owner/edit"
					method="POST">
					<div class="heading">
						<p>Update Owner Details</p>
					</div>
					<div class="form-section">
						<label>User Details</label>
						<div id="user" class="input">
							<div>
								<input type="text" name="username" placeholder="Username"
									value="<%=user.getUsername()%>" required minlength="3"
									maxlength="20" pattern="[A-Za-z0-9_]{3,20}"
									title="3-20 characters. Letters, numbers, and underscores only."
									disabled />
							</div>
						</div>
					</div>
					<div class="form-section">
						<label>Name</label>
						<div id="name" class="input">
							<div>
								<input type="text" name="owner_id" value="<%= request.getParameter("oid") %>" required
									minlength="1" maxlength="20" pattern="[0-9]{1,20}" hidden />

								<input type="text" name="fname" placeholder="First Name"
									value="<%=owner.getFname()%>" required
									pattern="[A-Za-z ]{2,30}" title="Only letters allowed." />
							</div>
							<div>
								<input type="text" name="mname" placeholder="Middle Name"
									value="<%=(owner.getMname() == null || owner.getMname().isEmpty()) ? " " : owner.getMname()%>"
									pattern="[A-Za-z ]{0,30}" title="Only letters allowed." />
							</div>
							<div>
								<input type="text" name="lname" placeholder="Last Name"
									value="<%=owner.getLname()%>" required
									pattern="[A-Za-z ]{2,30}" title="Only letters allowed." />
							</div>
						</div>
					</div>
					<div class="form-section">
						<label>Additional Details</label>
						<div id="additional" class="input">
							<label class="input-descriptor">Phone</label>
							<div>
								<input type="text" name="phone" placeholder="Phone Number"
									value="<%=owner.getPhone()%>" required pattern="\d{10}"
									maxlength="10" title="Enter a valid 10-digit phone number." />
							</div>
							<div>
								<label class="input-descriptor">Address</label> <input
									type="text" name="address" placeholder="Address"
									value="<%=owner.getAddress()%>" required minlength="5"
									maxlength="200" />
							</div>
							<label class="input-descriptor">Aadhaar</label>
							<div>
								<input type="text" name="aadhaar" placeholder="Aadhaar"
									value="<%=owner.getAadhaar()%>" required pattern="\d{12}"
									maxlength="12" title="Enter a 12-digit Aadhaar number."
									readonly />
							</div>
						</div>
					</div>

					<div id="submit">
						<input type="submit" value="Save" />
					</div>
				</form>
			</div>
			<%
			}
			%>
		</div>
	</div>
</body>
</html>
