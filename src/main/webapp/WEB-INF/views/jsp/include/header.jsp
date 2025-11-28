<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header>
	<div>
		<span>Vehicle Tender Management Portal</span>
	</div>

	<nav class="nav-links">
		<a href="${pageContext.request.contextPath}/admin">Home</a>
		<div class="dropdown">
			<a href="#">Owners</a>
			<div class="submenu">
				<a href="${pageContext.request.contextPath}/admin/owner/add">Add New Owner</a> <a href="#">Edit/Delete</a>
			</div>
		</div>

		<div class="dropdown">
			<a href="#">Tenders</a>
			<div class="submenu">
				<a href="#">Add New Tender</a> <a href="#">Edit/Delete</a>
			</div>
		</div>
		<a href="#" class="logout">Logout</a>
	</nav>
</header>