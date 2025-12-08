package com.vtmp.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vtmp.util.ErrorUtil;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AuthDao dao = new AuthDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.setContentType("text/plain");
		response.getWriter().write("METHOD_NOT_ALLOWED");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = null;
		AuthBean authBean = new AuthBean();
		AuthBean authenticatedUser = null;
		authBean.setUsername(request.getParameter("username"));
		authBean.setPassword(request.getParameter("password"));

		try {
			authenticatedUser = dao.authenticate(authBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (authenticatedUser != null) {
			session = request.getSession();
			session.setAttribute("userid", authenticatedUser.getUser_id());
			session.setAttribute("username", authenticatedUser.getUsername());
			session.setAttribute("userrole", authenticatedUser.getRole());
			if (authenticatedUser.getRole().equals("admin")) {
				response.sendRedirect("admin");
			} else if (authenticatedUser.getRole().equals("owner")) {
				response.sendRedirect("owner");
			}
			return;
		} else {
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//			response.setContentType("text/plain");
//			response.getWriter().write("Invalid Username/Password");
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_FORBIDDEN, "Invalid Username/Password!");
			return;
		}

	}

}
