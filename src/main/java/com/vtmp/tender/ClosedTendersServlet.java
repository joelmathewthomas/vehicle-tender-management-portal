package com.vtmp.tender;

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
import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class ClosedTendersServlet
 */
@WebServlet("/tender/closed")
public class ClosedTendersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final TenderDetailsService tenderDetailsService = new TenderDetailsService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && "owner".equals(session.getAttribute("userrole"))) {

			int userId = SessionUtil.getIntParam(request, "userid");

			if (userId <= 0) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Please logout and try again!");
				return;
			}

			OwnerDetails ownerDetails;
			try {
				ownerDetails = ownerService.getOwnerDetailsByUserID(userId);
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("INTERNAL SERVER ERROR");
				return;
			}

			if (ownerDetails == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Owner details not found!");
				return;
			}

			int ownerId = ownerDetails.getOwner().getOwner_id();

			List<TenderDetails> tenderDetailsList;
			try {
				tenderDetailsList = tenderDetailsService.getTenderDetailsByOwnerAndStatus("closed", ownerId);
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("INTERNAL SERVER ERROR");
				return;
			}

			request.setAttribute("tenderDetailsList", tenderDetailsList);
		}

		else if (session != null && "admin".equals(session.getAttribute("userrole"))) {

			List<TenderDetails> tenderDetailsList;
			try {
				tenderDetailsList = tenderDetailsService.getTenderDetailsByStatus("closed");
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("INTERNAL SERVER ERROR");
				return;
			}

			request.setAttribute("tenderDetailsList", tenderDetailsList);
		}

		else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("text/plain");
			response.getWriter().write("UNAUTHORIZED ACCESS");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/views/tender/closedTendersTable.jsp").forward(request, response);
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
