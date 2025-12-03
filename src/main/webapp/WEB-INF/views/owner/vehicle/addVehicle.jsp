<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Add Vehicle</title>
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
			<div id="form">
				<form action="${pageContext.request.contextPath}/owner/vehicle/add"
					method="POST">
					<div class="heading">
						<p>Add Vehicle</p>
					</div>

					<div class="form-section">
						<label>Owner ID</label>
						<div class="input">
							<div>
								<input type="text" name="owner_id"
									value="<%=request.getAttribute("owner_id")%>" readonly
									disabled />
							</div>
						</div>
					</div>

					<div class="form-section">
						<label>Vehicle Details</label>
						<div id="additional" class="input">
							<label class="input-descriptor">Vehicle Number</label>
							<div>
								<input type="text" name="vehicle_no"
									placeholder="Vehicle License Number"
									pattern="^[A-Z]{2}\s?\d{1,2}\s?[A-Z]{0,3}\s?\d{1,4}$" required
									title="Format: AA 12 ABC 1234 (spaces optional)" />
							</div>

							<label class="input-descriptor">Vehicle Type</label>
							<div>
								<select name="vehicle_type" required>
									<option value="" selected disabled>Select Vehicle Type
									</option>
									<option value="car">Car</option>
									<option value="bus">Bus</option>
								</select>
							</div>
						</div>
					</div>

					<div id="submit">
						<input type="submit" value="Add" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
