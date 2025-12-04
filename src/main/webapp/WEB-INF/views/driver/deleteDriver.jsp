<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.vtmp.driver.DriverBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Delete Driver</title>

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

		<%@ include file="../jsp/include/header.jsp"%>

		<div class="content">

			<%
			boolean showSearchForm = (request.getParameter("did") == null || request.getAttribute("driverInfo") == null);

			if (showSearchForm) {
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/driver/delete"
					method="GET">
					<div class="heading">
						<p>Delete Driver</p>
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
				<form action="${pageContext.request.contextPath}/driver/delete"
					method="POST">

					<div class="heading">
						<p>Delete Driver</p>
					</div>

					<div class="form-section">
						<label>Driver Details</label>
						<div id="additional" class="input">

							<label class="input-descriptor">Name</label>
							<div>
								<input type="hidden" name="driver_id"
									value="<%=driver.getDriver_id()%>" required readonly /> <input
									type="text" name="name"
									value="<%=(driver.getFname() == null ? "" : driver.getFname()) + " "
		+ (driver.getMname() == null ? "" : driver.getMname()) + " "
		+ (driver.getLname() == null ? "" : driver.getLname())%>"
									readonly />
							</div>

							<label class="input-descriptor">Phone</label>
							<div>
								<input type="text" name="phone" value="<%=driver.getPhone()%>"
									readonly />
							</div>

							<label class="input-descriptor">Aadhaar</label>
							<div>
								<input type="text" name="aadhaar"
									value="<%=driver.getAadhaar()%>" readonly />
							</div>

							<label class="input-descriptor">Address</label>
							<div>
								<input type="text" name="address"
									value="<%=driver.getAddress()%>" readonly />
							</div>

						</div>
					</div>

					<div id="submit" class="delete">
						<input type="submit" value="Delete" />
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
