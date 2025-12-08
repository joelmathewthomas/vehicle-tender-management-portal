package com.vtmp.home.admin.tender;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.payment.PaymentService;
import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
import com.vtmp.util.ErrorUtil;
import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class PayTenderServlet
 */
@WebServlet("/admin/tender/pay")
public class PayTenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TenderDetailsService tenderDetailsService = new TenderDetailsService();
	private PaymentService paymentService = new PaymentService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int tender_id = RequestUtil.getIntParam(request, "tid");
		if (tender_id < 1) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Tender ID");
			return;
		}

		try {
			TenderDetails tenderDetails = tenderDetailsService.getTenderDetailsById(tender_id);
			int payment_id = paymentService.addPayment(tenderDetails.getTenderBean());
			if (payment_id > 0) {
				response.sendRedirect(request.getContextPath() + "/payment#r" + payment_id);
				return;
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Internal Server Error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal Server Error");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtil.sendError(request, response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED");
	}

}
