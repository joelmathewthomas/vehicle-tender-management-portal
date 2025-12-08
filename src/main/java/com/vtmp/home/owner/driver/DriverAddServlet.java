package com.vtmp.home.owner.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.driver.DriverBean;
import com.vtmp.driver.DriverService;
import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.details.OwnerDetails;
import com.vtmp.util.ErrorUtil;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class DriverAddServlet
 */
@WebServlet("/owner/driver/add")
public class DriverAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final DriverService driverService = new DriverService();

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
					request.getRequestDispatcher("/WEB-INF/views/owner/driver/addDriver.jsp").forward(request,
							response);
					return;
				}
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST,
						"Please logout and try again");
				return;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DriverBean driverBean = driverService.mapRequestToDriver(request);

		if (driverBean == null) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Owner ID");
			return;
		}

		List<String> errors = driverService.validateForm(driverBean);
		if (!errors.isEmpty()) {
			StringBuilder msg = new StringBuilder("Invalid FormData\n");
			errors.forEach(e -> msg.append(e).append("\n"));
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, msg.toString());
			return;
		}

		try {
			int driverId = driverService.addDriver(driverBean);

			if (driverId != -1) {
				response.sendRedirect(request.getContextPath() + "/driver#r" + driverId);
				return;
			}

			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains("driver_phone")) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Phone number already exists");
			} else if (e.getMessage().contains("driver_aadhaar")) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Aadhaar already exists");
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
		}

	}

}
