package com.vtmp.home.admin.owner;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.RequestUtil;

/**
 * Servlet implementation class OwnerDeleteServlet
 */
@WebServlet("/admin/owner/delete")
public class OwnerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OwnerDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OwnerService service = new OwnerService();
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
			    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			    response.getWriter().write("Invalid owner id");
			    return;
			}

			if (service.deleteOwner(ownerId)) {
				response.sendRedirect(request.getContextPath() + "/admin/owner");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Failed to delete owner!");
			}
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Internal Server Error");
			e.printStackTrace();
		}

	}

}
