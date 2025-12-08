package com.vtmp.home.owner.tender;

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
 * Servlet implementation class CloseTenderServlet
 */
@WebServlet("/owner/tender/close")
public class CloseTenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TenderService tenderService = new TenderService();

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
			if (tenderService.closeTender(tender_id)) {
				response.sendRedirect(request.getContextPath() + "/tender/closed#r" + tender_id);
				return;
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"INTERNAL SERVER ERROR");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtil.sendError(request, response, HttpServletResponse.SC_FORBIDDEN, "Forbidden");
	}

}
