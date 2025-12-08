package com.vtmp.home.owner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.details.OwnerDetails;
import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
import com.vtmp.util.SessionUtil;
import com.vtmp.util.ErrorUtil;

/**
 * Servlet implementation class OwnerHomeServlet
 */
@WebServlet("/owner")
public class OwnerHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final TenderDetailsService tenderDetailsService = new TenderDetailsService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int userId = SessionUtil.getIntParam(request, "userid");

			if (userId > 0) {
				OwnerDetails ownerDetails = ownerService.getOwnerDetailsByUserID(userId);

				if (ownerDetails != null) {
					int ownerId = ownerDetails.getOwner().getOwner_id();

					List<TenderDetails> tenderDetailsList = tenderDetailsService
							.getTenderDetailsByOwnerAndStatus("accept", ownerId);

					request.setAttribute("tenderDetailsList", tenderDetailsList);
				}
			} else {

				ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST,
						"Please login and try again!");
				return;
			}

			request.getRequestDispatcher("/WEB-INF/views/owner/ownerHome.jsp").forward(request, response);

		} catch (SQLException e) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
			e.printStackTrace();
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
