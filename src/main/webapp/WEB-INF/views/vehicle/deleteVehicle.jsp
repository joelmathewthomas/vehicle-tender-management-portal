<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.vtmp.vehicle.VehicleBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Delete Vehicle</title>

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
			boolean showSearchForm = (request.getParameter("vid") == null || request.getAttribute("vehicleInfo") == null);

			if (showSearchForm) {
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/vehicle/delete"
					method="GET">
					<div class="heading">
						<p>Delete Vehicle</p>
					</div>

					<div class="form-section">
						<label>Enter Vehicle ID</label>
						<div id="user" class="input">
							<div>
								<input type="text" name="vid" placeholder="Vehicle ID" required
									minlength="1" maxlength="20" pattern="[0-9]{1,20}"
									title="Enter a valid Vehicle ID." />
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
			VehicleBean vehicle = (VehicleBean) request.getAttribute("vehicleInfo");
			%>

			<div id="form">
				<form action="${pageContext.request.contextPath}/vehicle/delete"
					method="POST">

					<div class="heading">
						<p>Delete Vehicle</p>
					</div>

					<div class="form-section">
						<label>Owner ID</label>
						<div class="input">
							<div>
								<input type="hidden" name="vid"
									value="<%=vehicle.getVehicle_id()%>" /> <input type="text"
									name="owner_id" value="<%=vehicle.getOwner_id()%>" readonly
									disabled />
							</div>
						</div>
					</div>

					<div class="form-section">
						<label>Vehicle Details</label>
						<div id="additional" class="input">

							<label class="input-descriptor">Vehicle Number</label>
							<div>
								<input type="text" name="vehicle_license_number"
									value="<%=vehicle.getVehicle_no()%>" readonly />
							</div>

							<label class="input-descriptor">Vehicle Type</label>
							<div>
								<input type="text" name="vehicle_type"
									value="<%=vehicle.getVehicle_type().substring(0, 1).toUpperCase() + vehicle.getVehicle_type().substring(1)%>"
									readonly />
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
