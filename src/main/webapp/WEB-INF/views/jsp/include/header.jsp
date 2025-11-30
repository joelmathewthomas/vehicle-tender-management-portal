<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("ctxPth", request.getContextPath());
%>
<header>
	<div>
		<span>Vehicle Tender Management Portal</span>
	</div>

	<nav class="nav-links">
		<a href="${ctxPth}/admin">Home</a>
		<div class="dropdown">
			<a href="${ctxPth}/admin/owner">Owners</a>
			<div class="submenu">
				<a href="${ctxPth}/admin/owner/add">Add New Owner</a> <a href="#">Edit/Delete</a>
			</div>
		</div>

		<div class="dropdown">
			<a href="#">Tenders</a>
			<div class="submenu">
				<a href="#">Add New Tender</a> <a href="#">Edit/Delete</a>
			</div>
		</div>
		<a href="${ctxPth}/auth/logout" class="logout">Logout</a>
	</nav>
</header>