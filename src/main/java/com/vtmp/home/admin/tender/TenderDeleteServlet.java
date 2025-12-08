package com.vtmp.home.admin.tender;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.tender.TenderService;
import com.vtmp.util.ErrorUtil;
import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class TenderDeleteServlet
 */
@WebServlet("/admin/tender/delete")
public class TenderDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TenderService tenderService = new TenderService();

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
			if (tenderService.deleteTender(tender_id)) {
				String referer = request.getHeader("referer");
				if (referer != null) {
					response.sendRedirect(referer);
				} else {
					response.sendRedirect(request.getContextPath() + "/admin");
				}

				return;
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Internal Server Error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal Server Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

}
