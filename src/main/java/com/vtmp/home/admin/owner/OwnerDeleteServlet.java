package com.vtmp.home.admin.owner;

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
 * Servlet implementation class OwnerDeleteServlet
 */
@WebServlet("/admin/owner/delete")
public class OwnerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService service = new OwnerService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OwnerDeleteServlet() {
		super();
	}

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
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
				return;
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/admin/owner/deleteOwner.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OwnerService service = new OwnerService();

		// Delete owner
		try {
			int ownerId = RequestUtil.getIntParam(request, "owner_id");

			if (ownerId <= 0) {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid owner id");
				return;
			}

			if (service.deleteOwner(ownerId)) {
				response.sendRedirect(request.getContextPath() + "/admin/owner");
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete owner!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
		}

	}

}
