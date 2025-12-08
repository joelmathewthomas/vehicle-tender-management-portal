package com.vtmp.home.admin.driver;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.driver.DriverService;
import com.vtmp.util.ErrorUtil;
import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class DriverToggleStatusServlet
 */
@WebServlet("/admin/driver/togglestatus")
public class DriverToggleStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DriverService service = new DriverService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int driver_id = RequestUtil.getIntParam(request, "did");
		if (driver_id != 0) {
			try {
				if (service.toggleDriverStatus(driver_id)) {
					response.sendRedirect(request.getContextPath() + "/driver#r" + driver_id);
					return;
				} else {
					ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"INTERNAL SERVER ERROR! Failed to toggle driver status!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"INTERNAL SERVER ERROR! Failed to toggle driver status!");
			}
		} else {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtil.sendError(request, response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "METHOD NOT ALLOWED");
	}

}
