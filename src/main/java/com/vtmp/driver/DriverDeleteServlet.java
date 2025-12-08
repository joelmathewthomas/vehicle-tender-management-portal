package com.vtmp.driver;

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
 * Servlet implementation class DriverDeleteServlet
 */
@WebServlet("/driver/delete")
public class DriverDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DriverService driverService = new DriverService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int driver_id = RequestUtil.getIntParam(request, "did");
		if (driver_id != 0) {
			try {
				request.setAttribute("driverInfo", driverService.getDriverById(driver_id));
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"INTERNAL SERVER ERROR");
				return;
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/driver/deleteDriver.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int driver_id = RequestUtil.getIntParam(request, "driver_id");
		if (driver_id != 0) {
			try {
				if (driverService.deleteDriver(driver_id)) {
					response.sendRedirect(request.getContextPath() + "/driver");
					return;
				} else {
					ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"INTERNAL SERVER ERROR! Failed to delete driver!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"INTERNAL SERVER ERROR! Failed to delete driver!");

			}
		} else {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
			return;
		}
	}

}
