<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.vehicle.VehicleBean"%>
<%@ page import="com.vtmp.driver.DriverBean"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Accept Tender</title>

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
				<form action="/vtmp/vehicle/delete" method="POST">
					<div class="heading">
						<p>Accept Tender</p>
					</div>

					<div class="form-section">
						<label>Tender Details</label>
						<div id="additional" class="input">
							<label class="input-descriptor">Vehicle</label>
							<div>
								<select name="location_id" required>
									<option value="" selected disabled>Select Vehicle</option>
									<%
									List<VehicleBean> vehicles = (List<VehicleBean>) request.getAttribute("vehicles");
									for (VehicleBean v : vehicles) {
									%>
									<option value="<%=v.getVehicle_id()%>"><%=v.getVehicle_type().substring(0, 1).toUpperCase() + v.getVehicle_type().substring(1) + " " + v.getVehicle_no()%></option>
									<%
									}
									%>
								</select>
							</div>

							<label class="input-descriptor">Driver</label>
							<div>
								<select name="location_id" required>
									<option value="" selected disabled>Select Driver</option>
									<%
									List<DriverBean> drivers = (List<DriverBean>) request.getAttribute("drivers");
									for (DriverBean d : drivers) {
										String driver_name = d.getFname() + " "
										+ ((d.getMname() == null || d.getMname().isEmpty()) ? "" : d.getMname() + " ") + d.getLname();
									%>
									<option value="<%=d.getDriver_id()%>"><%=driver_name%></option>
									<%
									}
									%>
								</select>
							</div>
						</div>
					</div>

					<div id="submit">
						<input type="submit" value="Accept" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>