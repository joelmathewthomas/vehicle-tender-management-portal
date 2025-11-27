package com.vtmp.home.admin.owner;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OwnerAdd
 */
@WebServlet("/admin/owner/add")
public class OwnerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		OwnerBean ownerBean = new OwnerBean();
		ownerBean.setUsername(request.getParameter("username"));
		ownerBean.setPassword(request.getParameter("password"));
		ownerBean.setFname(request.getParameter("fname"));
		ownerBean.setMname(request.getParameter("mname"));
		ownerBean.setLname(request.getParameter("lname"));
		ownerBean.setPhone(request.getParameter("phone"));
		ownerBean.setAddress(request.getParameter("address"));
		ownerBean.setAadhaar(request.getParameter("aadhaar"));

		// Validate Form
		errors = OwnerService.validateForm(ownerBean);
		if (!errors.isEmpty()) {
			StringBuilder messages = new StringBuilder();
			messages.append("Invalid FormData\n");
			for (String error : errors) {
				messages.append(error).append("\n");
			}
			response.getWriter().write(messages.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write(messages.toString());
		}
	}

}
