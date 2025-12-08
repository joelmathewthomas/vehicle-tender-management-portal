package com.vtmp.home.admin.tender;

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
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write("Invalid Tender ID");
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
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Internal Server Error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.setContentType("text/plain");
		response.getWriter().write("Internal Server Error");
	}

}
