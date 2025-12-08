package com.vtmp.tender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
import com.vtmp.util.ErrorUtil;

/**
 * Servlet implementation class OpenTendersServlet
 */
@WebServlet("/tender/open")
public class OpenTendersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TenderDetailsService tenderDetailsService = new TenderDetailsService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<TenderDetails> tenderDetailsList = tenderDetailsService.getTenderDetailsByStatus("open");
			request.setAttribute("tenderDetailsList", tenderDetailsList);
		} catch (SQLException e) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/views/tender/openTendersTable.jsp").forward(request, response);
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
