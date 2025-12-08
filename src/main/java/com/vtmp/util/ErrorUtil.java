package com.vtmp.util;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for handling application error responses.
 */
public class ErrorUtil {

	/**
	 * Sends a Base64-encoded error message to the error page with the given HTTP
	 * status code.
	 *
	 * @param request  the current HTTP servlet request
	 * @param response the current HTTP servlet response
	 * @param status   the HTTP status code to set in the response
	 * @param message  the error message to encode and forward to the error page
	 * @throws IOException if an I/O error occurs while redirecting
	 */
	public static void sendError(HttpServletRequest request, HttpServletResponse response, int status, String message)
			throws IOException {
		String encoded = Base64.getUrlEncoder().encodeToString(message.getBytes());
		response.setStatus(status);
		response.sendRedirect(request.getContextPath() + "/error?e=" + encoded);
	}
}
