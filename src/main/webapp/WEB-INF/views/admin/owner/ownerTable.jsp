<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.home.admin.owner.OwnerBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Owners List</title>
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
		<%@ include file="../../jsp/include/header.jsp"%>
		<div class="content top">
			<div class="table-container">
				<h2>Owners List</h2>
				<div class="table-wrapper">
					<table>
						<thead>
							<tr>
								<th>Owner ID</th>
								<th>Name</th>
								<th>Phone</th>
								<th>Address</th>
								<th>Aadhaar</th>
							</tr>
						</thead>

						<tbody>
							<%
							List<OwnerBean> ownersList = (List<OwnerBean>) request.getAttribute("owners");

							if (ownersList != null) {
								for (OwnerBean o : ownersList) {
							%>
							<tr id="<%="r" + o.getOwner_id()%>">
								<td><%=o.getOwner_id()%></td>
								<td><%=o.getFname() + " " + ((o.getMname() != null && !o.getMname().isEmpty()) ? o.getMname() + " " : "")
		+ o.getLname()%></td>
								<td><%=o.getPhone()%></td>
								<td><%=o.getAddress()%></td>
								<td><%=o.getAadhaar()%></td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="4" style="text-align: center;">No owners
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
