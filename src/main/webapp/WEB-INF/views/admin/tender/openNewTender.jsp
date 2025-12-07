<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.home.admin.location.LocationBean"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Open New Tender</title>
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
				<form action="${ctxPth}/admin/tender/open" method="POST">
					<div class="heading">
						<p>Open New Tender</p>
					</div>

					<div class="form-section">
						<label>Tender Details</label>
						<div id="additional" class="input">
							<label class="input-descriptor">Date</label>
							<div>
								<input type="date" name="tender_date"
									min="<%=LocalDate.now().toString()%>" required />
							</div>

							<label class="input-descriptor">Location</label>
							<div>
								<select name="location_id" required>
									<option value="" selected disabled>Select Location</option>
									<%
									List<LocationBean> locations = (List<LocationBean>) request.getAttribute("locations");
									for (LocationBean l : locations) {
									%>
									<option value="<%=l.getLocation_id()%>"><%=l.getLocation_name()%></option>
									<%
									}
									%>
								</select>
							</div>

							<label class="input-descriptor">Distance (in km)</label>
							<div>
								<input type="number" name="tender_distance" required min="0.1"
									step="0.1" />
							</div>

							<label class="input-descriptor">Fuel Rate (per Litre)</label>
							<div>
								<input type="number" name="tender_fuel_rate" required min="0.1"
									step="0.1" />
							</div>

							<label class="input-descriptor">Salary</label>
							<div>
								<input type="number" name="tender_salary" required min="1000"
									step="500" />
							</div>

						</div>
					</div>

					<div id="submit">
						<input type="submit" value="Create" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
