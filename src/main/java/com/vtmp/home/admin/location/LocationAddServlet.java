package com.vtmp.home.admin.location;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.ErrorUtil;

@WebServlet("/admin/location/add")
public class LocationAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LocationService service = new LocationService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtil.sendError(request, response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String location = request.getParameter("location_name");

		if (location != null) {
			location = location.trim().replaceAll("\\s+", " ");
		}

		if (location == null || location.isEmpty() || !location.matches("^[A-Za-z]+(?:[ .' -][A-Za-z]+)*$")) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Location Name");
			return;
		}

		try {
			int location_id = service.addLocation(location);
			if (location_id > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/location#r" + location_id);
				return;
			}
		} catch (SQLException e) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Location already exists!");
			e.printStackTrace();
		} catch (Exception e) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
			e.printStackTrace();
		}
	}
}
