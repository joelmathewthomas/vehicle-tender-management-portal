package com.vtmp.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.setContentType("text/plain");
		response.getWriter().write("METHOD_NOT_ALLOWED");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthDao dao = new AuthDao();
		HttpSession session = request.getSession();
		AuthBean authBean = new AuthBean();
		authBean.setUsername(request.getParameter("username"));
		authBean.setPassword(request.getParameter("password"));

		try {
			authBean = dao.authenticate(authBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (authBean != null) {
			session.setAttribute("userid", authBean.getUser_id());
			session.setAttribute("username", authBean.getUsername());
			session.setAttribute("userrole", authBean.getRole());
			response.sendRedirect("admin");
			return;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain");
			response.getWriter().write("Invalid Username/Password");
			return;
		}

	}

}
