<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.tender.details.TenderDetails"%>
<%@ page import="com.vtmp.tender.TenderBean"%>
<%@ page import="com.vtmp.driver.DriverBean"%>
<%@ page import="com.vtmp.home.admin.location.LocationBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Home</title>
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
				<h2>Open Tenders</h2>
				<div class="table-wrapper">
					<table>
						<thead>
							<tr>
								<th>Tender ID</th>
								<th>Location Name</th>
								<th>Tender Date</th>
								<th>Distance(km)</th>
								<th>Fuel Rate</th>
								<th>Salary</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>
							<%
							List<TenderDetails> tenderDetailsList = (List<TenderDetails>) request.getAttribute("tenderDetailsList");

							boolean anyRowPrinted = false;
							for (TenderDetails td : tenderDetailsList) {
								anyRowPrinted = true;
								TenderBean tender = td.getTenderBean();
								LocationBean location = td.getLocationBean();
							%>
							<tr id="r<%=tender.getTender_id()%>">
								<td><%=tender.getTender_id()%></td>
								<td><%=location.getLocation_name()%></td>
								<td><%=tender.getTender_date()%></td>
								<td><%=tender.getTender_distance()%></td>
								<td><%=tender.getTender_fuel_rate()%></td>
								<td><%=tender.getTender_salary()%></td>
								<%
								if (role.equals("admin")) {
								%>
								<td><a class="delete">Delete</a></td>
								<%
								} else if (role.equals("owner")) {
								%>
								<td><a
									href="${ctxPth}/owner/tender/accept?tid=<%= tender.getTender_id()%>">Accept</a></td>
								<%
								}
								%>
							</tr>
							<%
							}

							if (!anyRowPrinted) {
							%>

							<tr>
								<td colspan="9" style="text-align: center;">No open tenders
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