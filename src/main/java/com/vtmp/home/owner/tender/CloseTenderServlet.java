package com.vtmp.home.owner.tender;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.tender.TenderService;
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
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write("Invalid Tender ID");
			return;
		}

		try {
			if (tenderService.closeTender(tender_id)) {
				response.sendRedirect(request.getContextPath() + "/owner/tender/closed#r" + tender_id);
				return;
			} else {

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("INTERNAL SERVER ERROR");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("INTERNAL SERVER ERROR");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("text/plain");
		response.getWriter().write("Forbidden");
	}

}
