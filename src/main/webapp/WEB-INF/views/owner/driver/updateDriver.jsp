<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.vtmp.driver.DriverBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Update Driver Details</title>

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
			boolean showSearchForm = (request.getParameter("did") == null || request.getAttribute("driverInfo") == null
					|| request.getAttribute("owner_id") == null);

			if (showSearchForm) {
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/owner/driver/edit"
					method="GET">
					<div class="heading">
						<p>Update Driver Details</p>
					</div>

					<div class="form-section">
						<label>Enter Driver ID</label>
						<div id="user" class="input">
							<div>
								<input type="text" name="did" placeholder="Driver ID" required
									minlength="1" maxlength="20" pattern="[0-9]{1,20}"
									title="Enter a valid Driver ID." />
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
			DriverBean driver = (DriverBean) request.getAttribute("driverInfo");
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/owner/driver/edit"
					method="POST">

					<div class="heading">
						<p>Update Driver Details</p>
					</div>
					<div class="form-section">
						<label>Driver Details</label>
						<div id="additional" class="input">

							<label class="input-descriptor">Name</label>
							<div id="name" class="input">
								<div>
									<input type="text" name="driver_id"
										value="<%=request.getParameter("did")%>" required
										minlength="1" maxlength="20" pattern="[0-9]{1,20}" readonly
										hidden /> <input type="text" name="owner_id"
										value="<%=request.getAttribute("owner_id")%>" required
										minlength="1" maxlength="20" pattern="[0-9]{1,20}" readonly
										hidden /> <input type="text" name="fname"
										placeholder="First Name" value="<%=driver.getFname()%>"
										required pattern="[A-Za-z ]{2,30}"
										title="Only letters allowed." />
								</div>
								<div>
									<input type="text" name="mname" placeholder="Middle Name"
										value="<%=(driver.getMname() == null || driver.getMname().isEmpty()) ? " " : driver.getMname()%>"
										pattern="[A-Za-z ]{0,30}" title="Only letters allowed." />
								</div>
								<div>
									<input type="text" name="lname" placeholder="Last Name"
										value="<%=driver.getLname()%>" required
										pattern="[A-Za-z ]{2,30}" title="Only letters allowed." />
								</div>
							</div>
						</div>
					</div>

					<div class="form-section">
						<label>Driver Details</label>
						<div id="additional" class="input">

							<label class="input-descriptor">Phone</label>
							<div>
								<input type="text" name="phone" value="<%=driver.getPhone()%>" />
							</div>

							<label class="input-descriptor">Aadhaar</label>
							<div>
								<input type="text" name="aadhaar"
									value="<%=driver.getAadhaar()%>" readonly />
							</div>

							<label class="input-descriptor">Address</label>
							<div>
								<input type="text" name="address"
									value="<%=driver.getAddress()%>" />
							</div>

						</div>
					</div>

					<div id="submit">
						<input type="submit" value="Update" />
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
