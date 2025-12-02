package com.vtmp.home.admin.vehicle;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.RequestUtil;
import com.vtmp.vehicle.VehicleService;

/**
 * Servlet implementation class VehicleToggleStatusServlet
 */
@WebServlet("/admin/vehicle/togglestatus")
public class VehicleToggleStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VehicleService service = new VehicleService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int vehicle_id = RequestUtil.getIntParam(request, "vid");
		if (vehicle_id != 0) {
			try {
				if (service.toggleVehicleStatus(vehicle_id)) {
					response.sendRedirect(request.getContextPath() + "/vehicle#r" + vehicle_id);
					return;
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.setContentType("text/plain");
					response.getWriter().write("Failed to toggle vehicle status!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Failed to toggle vehicle status!");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle ID");
			return;
		}
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
