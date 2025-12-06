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
import com.vtmp.util.RequestUtil;
import com.vtmp.util.SessionUtil;

/**
 * Servlet implementation class DriverUpdateServlet
 */
@WebServlet("/owner/driver/edit")
public class DriverUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final DriverService driverService = new DriverService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int driverId = RequestUtil.getIntParam(request, "did");
		if (driverId > 0) {
			try {
				request.setAttribute("driverInfo", driverService.getDriverById(driverId));
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Internal Server Error");
				return;
			}
		}

		int userId = SessionUtil.getIntParam(request, "userid");
		if (userId <= 0) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain");
			response.getWriter().write("Please logout and try again!");
			return;
		}

		try {
			OwnerDetails ownerDetails = ownerService.getOwnerDetailsByUserID(userId);
			if (ownerDetails == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("text/plain");
				response.getWriter().write("Permission denied (no owner details found)");
				return;
			}

			request.setAttribute("owner_id", ownerDetails.getOwner().getOwner_id());

			request.getRequestDispatcher("/WEB-INF/views/owner/driver/updateDriver.jsp").forward(request, response);

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
		List<String> errors = null;
		DriverBean driverBean = driverService.mapRequestToDriver(request);
		if (driverBean == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain");
			response.getWriter().write("Please logout and try again!");
		}

		int driver_id = RequestUtil.getIntParam(request, "driver_id");
		if (driver_id < 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write("Invalid Driver Id");
			return;
		}

		driverBean.setDriver_id(driver_id);

		// Validate Form
		errors = driverService.validateForm(driverBean);
		if (!errors.isEmpty()) {
			StringBuilder messages = new StringBuilder();
			messages.append("Invalid FormData\n");
			for (String error : errors) {
				messages.append(error).append("\n");
			}
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write(messages.toString());
			return;
		}

		// Update driver details
		try {
			if (driverService.updateDriver(driverBean)) {
				response.sendRedirect(request.getContextPath() + "/driver#r" + request.getParameter("driver_id"));
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Failed to update driver details!");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			if (e.getMessage().contains("driver_phone")) {
				response.getWriter().write("Phone number already exists.");
			} else {
				response.getWriter().write("Database constraint error: " + e.getMessage());
			}
			response.setContentType("text/plain");
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");

			response.getWriter().write("Failed to update driver details! " + e.getMessage());
			e.printStackTrace();
		}

	}

}
