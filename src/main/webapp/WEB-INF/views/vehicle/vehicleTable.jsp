<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.vehicle.VehicleBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Vehicles List</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/content.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/table.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet" />
</head>

<body>
	<div id="root">
		<%@ include file="../jsp/include/header.jsp"%>
		<div class="content top">
			<div class="table-container">
				<h2>Vehicles List</h2>
				<div class="table-wrapper">
					<table>
						<thead>
							<tr>
								<th>Vehicle ID</th>

								<%
								if (role.equals("admin")) {
								%>
								<th>Owner ID</th>
								<%
								}
								%>
								<th>Vehicle Number</th>
								<th>Vehicle Type</th>
								<th>Vehicle Status</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>
							<%
							List<VehicleBean> vehiclesList = (List<VehicleBean>) request.getAttribute("vehicles");

							if (!vehiclesList.isEmpty()) {
								for (VehicleBean v : vehiclesList) {
							%>
							<tr id="<%="r" + v.getOwner_id()%>">
								<td><%=v.getVehicle_id()%></td>

								<%
								if (role.equals("admin")) {
								%>
								<td><%=v.getOwner_id()%></td>
								<%
								}
								%>

								<td><%=v.getVehicle_no()%></td>
								<td><%=v.getVehicle_type().substring(0,1).toUpperCase() + v.getVehicle_type().substring(1)%></td>
								<td><%=v.getVehicle_status().substring(0,1).toUpperCase() + v.getVehicle_status().substring(1)%></td>
								<td><a
									href="${ctxPth}/vehicle/edit?vid=<%= v.getVehicle_id() %>">Edit</a>
									<a class="delete"
									href="${ctxPth}/vehicle/delete?vid=<%= v.getVehicle_id() %>">Delete</a>
								</td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="5" style="text-align: center;">No vehicles
									found.</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
