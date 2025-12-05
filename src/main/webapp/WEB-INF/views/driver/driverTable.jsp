<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.driver.DriverBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Drivers List</title>

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
				<h2>Drivers List</h2>
				<div class="table-wrapper">

					<table>
						<thead>
							<tr>
								<th>Driver ID</th>

								<%
								if ("admin".equals(role)) {
								%>
								<th>Owner ID</th>
								<%
								}
								%>

								<th>Name</th>
								<th>Phone</th>
								<th>Aadhaar</th>
								<th>Address</th>
								<th>Status</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>
							<%
							List<DriverBean> drivers = (List<DriverBean>) request.getAttribute("drivers");

							String sParam = request.getParameter("s");
							boolean showNormal = (sParam == null || "0".equals(sParam));

							boolean anyRowPrinted = false;

							if (drivers != null && !drivers.isEmpty()) {
								for (DriverBean d : drivers) {

									boolean showFiltered = (sParam != null && d.getStatus().equals("unapproved"));
									if (!(showNormal || showFiltered))
								continue;

									anyRowPrinted = true;
									String name = "";
									if (d.getFname() != null)
								name += d.getFname() + " ";
									if (d.getMname() != null && !d.getMname().trim().isEmpty())
								name += d.getMname() + " ";
									name += d.getLname();
							%>

							<tr id="r<%=d.getDriver_id()%>">

								<td><%=d.getDriver_id()%></td>

								<%
								if ("admin".equals(role)) {
								%>
								<td><%=d.getOwner_id()%></td>
								<%
								}
								%>

								<td><%=name%></td>
								<td><%=d.getPhone()%></td>
								<td><%=d.getAadhaar()%></td>
								<td><%=d.getAddress()%></td>

								<%
								if ("owner".equals(role)) {
								%>

								<td><%=d.getStatus().substring(0, 1).toUpperCase() + d.getStatus().substring(1)%></td>

								<%
								} else if ("admin".equals(role)) {
								%>

								<td>
									<%
									if (d.getStatus().equals("unapproved")) {
									%> <a class="unapproved" title="Click to approve"
									href="${ctxPth}/admin/driver/togglestatus?did=<%= d.getDriver_id() %>">
										Unapproved </a> <%
 } else {
 %> <a class="approved" title="Click to unapprove"
									href="${ctxPth}/admin/driver/togglestatus?did=<%= d.getDriver_id() %>">
										Approved </a> <%
 }
 %>
								</td>

								<%
								}
								%>

								<td>
									<%
									if (role.equals("owner")) {
									%> <a
									href="${ctxPth}/owner/driver/edit?did=<%= d.getDriver_id() %>">
										Edit </a>

								<%
								}
								%>
								<a class="delete"
									href="${ctxPth}/driver/delete?did=<%= d.getDriver_id() %>">
									Delete </a>
								</td>

							</tr>

							<%
							}
							}
							%>

							<%
							if (!anyRowPrinted) {
							%>

							<tr>
								<td colspan="8" style="text-align: center;">No drivers
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
