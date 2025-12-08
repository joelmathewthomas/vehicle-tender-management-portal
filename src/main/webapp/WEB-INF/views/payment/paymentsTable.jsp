<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.home.admin.owner.OwnerBean"%>
<%@ page import="com.vtmp.payment.PaymentBean"%>
<%@ page import="com.vtmp.payment.details.PaymentDetails"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Payments List</title>
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
				<h2>Payments List</h2>
				<div class="table-wrapper">
					<table>
						<thead>
							<tr>
								<th>Payment ID</th>
								<%
								if (role.equals("admin")) {
								%>
								<th>Owner ID</th>
								<%
								}
								%>
								<th>Tender ID</th>
								<th>Payment Amount</th>
							</tr>
						</thead>

						<tbody>
							<%
							List<PaymentDetails> paymentsList = (List<PaymentDetails>) request.getAttribute("paymentsList");

							boolean anyRowPrinted = false;
							for (PaymentDetails p : paymentsList) {
								anyRowPrinted = true;
								OwnerBean owner = p.getOwner();
								PaymentBean payment = p.getPayment();
							%>
							<tr id="r<%=payment.getPayment_id()%>">
								<td><%=payment.getPayment_id()%></td>

								<%
								if (role.equals("admin")) {
								%>
								<td><%=owner.getOwner_id()%></td>
								<%
								}
								%>

								<td><%=payment.getTender_id()%></td>
								<td><%=payment.getPayment_amount()%></td>
							</tr>
							<%
							}

							if (!anyRowPrinted) {
							%>
							<tr>
								<%
								if (role.equals("admin")) {
								%>
								<td colspan="4" style="text-align: center;">No payments
									found.</td>
								<%
								} else {
								%>
								<td colspan="3" style="text-align: center;">No payments
									found.</td>
								<%
								}
								%>
							</tr>
							<%
							}
							%>
						</tbody>


					</table>
				</div>
			</div>
		</div>