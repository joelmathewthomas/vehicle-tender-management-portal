package com.vtmp.home.admin.owner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.ErrorUtil;

/**
 * Servlet implementation class OwnerTable
 */
@WebServlet("/admin/owner")
public class OwnerTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService service = new OwnerService();

	/**
	 * Handles GET requests and loads the owners list.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<OwnerBean> owners = service.getAllOwners();
			request.setAttribute("owners", owners);
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Failed to fetch owners!");
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

		ErrorUtil.sendError(request, response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED");
	}
}
