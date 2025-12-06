package com.vtmp.home.owner.vehicle;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.details.OwnerDetails;
import com.vtmp.util.SessionUtil;
import com.vtmp.vehicle.VehicleBean;
import com.vtmp.vehicle.VehicleService;

/**
 * Servlet implementation class VehicleAddServlet
 */
@WebServlet("/owner/vehicle/add")
public class VehicleAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final VehicleService vehicleService = new VehicleService();

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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		VehicleBean vehicleBean = vehicleService.mapRequestToVehicle(request);

		if (vehicleBean == null) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Owner ID");
			return;
		}

		List<String> errors = vehicleService.validateForm(vehicleBean);
		if (!errors.isEmpty()) {
			StringBuilder msg = new StringBuilder("Invalid FormData\n");
			errors.forEach(e -> msg.append(e).append("\n"));
			sendError(response, HttpServletResponse.SC_BAD_REQUEST, msg.toString());
			return;
		}

		try {
			int vehicleId = vehicleService.addVehicle(vehicleBean);

			if (vehicleId != -1) {
				response.sendRedirect(request.getContextPath() + "/vehicle#r" + vehicleId);
				return;
			}

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
		} catch (SQLIntegrityConstraintViolationException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			if (e.getMessage().contains("vehicle_no")) {
				response.getWriter().write("Vehicle Number already exists.");
			} else {
				response.getWriter().write("Database constraint error: " + e.getMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
		}
	}

	/** Helper method for sending plain-text error responses */
	private void sendError(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("text/plain");
		response.getWriter().write(message);
	}

}
