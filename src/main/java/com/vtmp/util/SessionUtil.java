package com.vtmp.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility methods for safely reading integer values from the HTTP session.
 */
public class SessionUtil {

	/**
	 * Returns an integer session attribute value using the current request.
	 *
	 * @param req  the HTTP request
	 * @param name the session attribute name
	 * @return the integer value, or 0 if missing or invalid
	 */
	public static int getIntParam(HttpServletRequest req, String name) {
		HttpSession session = req.getSession(false);
		return getIntParam(session, name);
	}

	/**
	 * Returns an integer session attribute value. If the attribute is missing or
	 * invalid, returns 0.
	 *
	 * @param session the session to read from
	 * @param name    the session attribute name
	 * @return the integer value, or 0 if missing or invalid
	 */
	public static int getIntParam(HttpSession session, String name) {
		if (session == null) {
			return 0;
		}

		Object obj = session.getAttribute(name);

		try {
			return (int) obj;
		} catch (Exception e) {
			System.out.println("SessionUtil: Invalid int attribute '" + name + "' = '" + obj + "'");
			return 0;
		}
	}
}
