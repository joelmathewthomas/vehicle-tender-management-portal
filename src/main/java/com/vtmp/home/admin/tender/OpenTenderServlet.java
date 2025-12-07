package com.vtmp.home.admin.tender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.home.admin.location.LocationBean;
import com.vtmp.home.admin.location.LocationService;

/**
 * Servlet implementation class OpenTenderServlet
 */
@WebServlet("/admin/tender/open")
public class OpenTenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LocationService locationService = new LocationService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<LocationBean> locations = new ArrayList<>();
		try {
			locations = locationService.getAllLocations();
			request.setAttribute("locations", locations);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("INTERNAL SERVER ERROR");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/views/admin/tender/openNewTender.jsp").forward(request, response);
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
