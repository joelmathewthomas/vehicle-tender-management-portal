package com.vtmp.home.admin.driver;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.driver.DriverService;
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
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.setContentType("text/plain");
					response.getWriter().write("Failed to toggle driver status!");
				}
			} catch (SQLException e) {
				e.printStackTrace();

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Failed to toggle driver status!");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write("Invalid Driver ID");
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
