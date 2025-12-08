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

import com.vtmp.auth.AuthBean;
import com.vtmp.util.ErrorUtil;

/**
 * Servlet implementation class OwnerAdd
 */
@WebServlet("/admin/owner/add")
public class OwnerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService service = new OwnerService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/admin/owner/addOwner.jsp").forward(request, response);

		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = null;
		AuthBean authBean = new AuthBean();
		authBean.setUsername(request.getParameter("username"));
		authBean.setPassword(request.getParameter("password"));
		authBean.setRole("owner");
		OwnerBean ownerBean = service.mapRequestToOwner(request);

		// Validate Form
		errors = service.validateForm(authBean, ownerBean);
		if (!errors.isEmpty()) {
			StringBuilder messages = new StringBuilder();
			messages.append("Invalid FormData\n");
			for (String error : errors) {
				messages.append(error).append("\n");
			}
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, messages.toString());
			return;
		}

		// Create new user
		try {
			int owner_id = service.addOwner(authBean, ownerBean);
			if (owner_id != -1) {
				response.sendRedirect(request.getContextPath() + "/admin/owner#r" + owner_id);
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Failed to create new owner!");
			}
		} catch (SQLIntegrityConstraintViolationException e) {

			if (e.getMessage().contains("user_name")) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Username already exists.");
			} else if (e.getMessage().contains("owner_phone")) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Phone number already exists.");
			} else if (e.getMessage().contains("owner_aadhaar")) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Aadhaar already exists.");
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Database constraint error: " + e.getMessage());
			}

		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Failed to create new owner! " + e.getMessage());
		}
	}

}
