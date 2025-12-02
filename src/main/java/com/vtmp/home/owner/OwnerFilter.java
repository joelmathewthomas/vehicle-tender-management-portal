package com.vtmp.home.owner;

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

/**
 * Servlet Filter implementation class OwnerFilter
 */
@WebFilter("/owner/*")
public class OwnerFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!Auth.checkSession(req.getSession(false), "owner")) {
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			res.setContentType("text/plain");
			res.getWriter().write("Forbidden");
			return;
		} else {

			chain.doFilter(request, response);
		}
	}

}
