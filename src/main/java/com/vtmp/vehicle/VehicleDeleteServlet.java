package com.vtmp.vehicle;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.ErrorUtil;
import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class VehicleDeleteServlet
 */
@WebServlet("/vehicle/delete")
public class VehicleDeleteServlet extends HttpServlet {
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
				request.setAttribute("vehicleInfo", service.getVehicleDetails(vehicle_id));
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Internal Server Error");
				return;
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/vehicle/deleteVehicle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int vehicle_id = RequestUtil.getIntParam(request, "vid");
		if (vehicle_id != 0) {
			try {
				if (service.deleteVehicle(vehicle_id)) {
					response.sendRedirect(request.getContextPath() + "/vehicle");
					return;
				} else {
					ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Failed to delete vehicle!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Failed to delete vehicle!");
			}
		} else {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Vehicle ID");
			return;
		}
	}

}
