package com.vtmp.home.admin.owner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OwnerTable
 */
@WebServlet("/admin/owner")
public class OwnerTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests and loads the owners list.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OwnerDao dao = new OwnerDao();

		try {
			List<OwnerBean> owners = dao.getOwners();
			request.setAttribute("owners", owners);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Failed to fetch owners!");
			e.printStackTrace();
			return;
		}

		request.getRequestDispatcher("/WEB-INF/views/admin/owner/ownerTable.jsp").forward(request, response);
	}

	/**
	 * Disallows POST requests.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.setContentType("text/plain");
		response.getWriter().write("METHOD_NOT_ALLOWED");
	}
}
