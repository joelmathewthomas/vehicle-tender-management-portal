package com.vtmp.driver;

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
import com.vtmp.home.admin.owner.dto.OwnerDetails;
import com.vtmp.util.Auth;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class DriverTableServlet
 */
@WebServlet("/driver")
public class DriverTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final DriverService driverService = new DriverService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		try {
			if (Auth.checkSession(session, "owner")) {
				// Owner – show only drivers belonging to owner
				int userId = SessionUtil.getIntParam(request, "userid");

				if (userId > 0) {
					OwnerDetails ownerDetails = ownerService.getOwnerDetailsByUserID(userId);

					if (ownerDetails != null) {
						int ownerId = ownerDetails.getOwner().getOwner_id();
						List<DriverBean> drivers = driverService.getDrivers(ownerId);
						request.setAttribute("drivers", drivers);
					}
				}

			} else {
				// Admin – show all drivers
				List<DriverBean> drivers = driverService.getDrivers();
				request.setAttribute("drivers", drivers);
			}

		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Failed to fetch vehicles!");
			e.printStackTrace();
			return;
		}

		request.getRequestDispatcher("/WEB-INF/views/driver/driverTable.jsp").forward(request, response);
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
