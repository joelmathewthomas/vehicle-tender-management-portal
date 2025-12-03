package com.vtmp.home.owner.vehicle;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.dto.OwnerDetails;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class VehicleAddServlet
 */
@WebServlet("/owner/vehicle/add")
public class VehicleAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();

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
					request.setAttribute("owner_id", ownerId);
					request.getRequestDispatcher("/WEB-INF/views/owner/vehicle/addVehicle.jsp").forward(request,
							response);
					return;
				}
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Please logout and try again!");
				return;

			}

		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
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
		doGet(request, response);
	}

}
