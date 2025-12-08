package com.vtmp.home.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.util.Auth;
import com.vtmp.util.ErrorUtil;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/admin/*")
public class AdminFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!Auth.checkSession(req.getSession(false), "admin")) {
			ErrorUtil.sendError(req, res, HttpServletResponse.SC_FORBIDDEN, "Forbidden");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

}
