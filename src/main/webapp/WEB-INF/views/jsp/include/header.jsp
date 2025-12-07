<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("ctxPth", request.getContextPath());
String role = (String) session.getAttribute("userrole");
pageContext.setAttribute("role", role);
%>
<header>
	<div>
		<span>Vehicle Tender Management Portal</span>
	</div>

	<nav class="nav-links">
		<div class="dropdown">
			<a href="${ctxPth}/${role}">Tenders</a>
			<div class="submenu">

				<%
				if (role.equals("admin")) {
				%>
				<a href="${ctxPth}/admin/tender/open">Open New Tender</a>
				<%
				}
				%>

				<a href="${ctxPth}/tender/open">Open Tenders</a>
			</div>
		</div>


		<%
		if (role.equals("admin")) {
		%>
		<div class="dropdown">
			<a href="${ctxPth}/admin/owner">Owners</a>
			<div class="submenu">
				<a href="${ctxPth}/admin/owner/add">Add New Owner</a> <a
					href="${ctxPth}/admin/owner/edit">Update Owner Details</a> <a
					href="${ctxPth}/admin/owner/delete">Delete Owner</a>
			</div>
		</div>
		<%
		}
		%>

		<div class="dropdown">
			<a href="${ctxPth}/vehicle">Vehicles</a>
			<div class="submenu">
				<%
				if (role.equals("owner")) {
				%>
				<a href="${ctxPth}/owner/vehicle/add">Add New Vehicle</a>
				<%
				}
				%>
				<a <% if (role.equals("admin")) { %> href="${ctxPth}/vehicle?s=1">Approve
					Vehicles</a> <a <% } %> href="${ctxPth}/vehicle/delete">Delete
					Vehicle</a>
			</div>
		</div>

		<div class="dropdown">
			<a href="${ctxPth}/driver">Drivers</a>
			<div class="submenu">

				<%
				if (role.equals("owner")) {
				%>
				<a href="${ctxPth}/owner/driver/add">Add New Driver</a>
				<%
				}
				%>

				<%
				if (role.equals("admin")) {
				%>
				<a href="${ctxPth}/driver?s=1">Approve Drivers</a>
				<%
				} else if (role.equals("owner")) {
				%>
				<a href="${ctxPth}/owner/driver/edit">Update Driver Details</a>
				<%
				}
				%>

				<a href="${ctxPth}/driver/delete">Delete Driver</a>

			</div>
		</div>

		<%
		if (role.equals("admin")) {
		%>
		<div class="dropdown">
			<a href="${ctxPth}/admin/location">Locations</a>
		</div>
		<%
		}
		%>

		<a href="${ctxPth}/auth/logout" class="logout">Logout</a>
	</nav>
</header>