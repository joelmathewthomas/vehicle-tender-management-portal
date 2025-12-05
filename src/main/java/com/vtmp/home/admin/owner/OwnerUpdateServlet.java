package com.vtmp.home.admin.owner;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class OwnerUpdateServlet
 */
@WebServlet("/admin/owner/edit")
public class OwnerUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService service = new OwnerService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int owner_id = RequestUtil.getIntParam(request, "oid");
		if (owner_id != 0) {
			try {
				request.setAttribute("ownerInfo", service.getOwnerDetails(owner_id));
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Internal Server Error");
				return;
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/admin/owner/updateOwner.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = null;
		OwnerBean ownerBean = service.mapRequestToOwner(request);
		ownerBean.setOwner_id(RequestUtil.getIntParam(request, "owner_id"));

		// Validate Form
		errors = service.validateForm(ownerBean);
		if (!errors.isEmpty()) {
			StringBuilder messages = new StringBuilder();
			messages.append("Invalid FormData\n");
			for (String error : errors) {
				messages.append(error).append("\n");
			}
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write(messages.toString());
			return;
		}

		// Update owner details
		try {
			if (service.updateOwner(ownerBean)) {
				response.sendRedirect(request.getContextPath() + "/admin/owner#r" + request.getParameter("owner_id"));
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Failed to update owner details!");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			if (e.getMessage().contains("user_name")) {
				response.getWriter().write("Username already exists.");
			} else if (e.getMessage().contains("owner_phone")) {
				response.getWriter().write("Phone number already exists.");
			} else {
				response.getWriter().write("Database constraint error: " + e.getMessage());
			}
			response.setContentType("text/plain");
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");

			response.getWriter().write("Failed to update owner details! " + e.getMessage());
			e.printStackTrace();
		}

	}
}
