package com.vtmp.payment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.details.OwnerDetails;
import com.vtmp.payment.details.PaymentDetails;
import com.vtmp.payment.details.PaymentDetailsService;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class PaymentTableServlet
 */
@WebServlet("/payment")
public class PaymentTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final PaymentDetailsService paymentDetailsService = new PaymentDetailsService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			int userId = SessionUtil.getIntParam(request, "userid");

			if (session == null || session.getAttribute("userrole") == null || userId <= 0) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("text/plain");
				response.getWriter().write("Forbidden");
				return;
			}

			String role = (String) session.getAttribute("userrole");

			if (role.equals("owner")) {
				OwnerDetails ownerDetails = ownerService.getOwnerDetailsByUserID(userId);

				if (ownerDetails != null) {
					int ownerId = ownerDetails.getOwner().getOwner_id();
					List<PaymentDetails> paymentsList = paymentDetailsService.getPaymentsByOwner(ownerId);
					request.setAttribute("paymentsList", paymentsList);

					request.getRequestDispatcher("/WEB-INF/views/payment/paymentsTable.jsp").forward(request, response);
					return;
				}

			} else if (role.equals("admin")) {
				List<PaymentDetails> paymentsList = paymentDetailsService.getAllPayments();
				request.setAttribute("paymentsList", paymentsList);

				request.getRequestDispatcher("/WEB-INF/views/payment/paymentsTable.jsp").forward(request, response);
				return;
			}

		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.setContentType("text/plain");
		response.getWriter().write("METHOD_NOT_ALLOWED");
	}

}
