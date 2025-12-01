package com.vtmp.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	/**
	 * Safely parses an integer request parameter. Returns 0 if the parameter is
	 * missing or invalid.
	 */
	public static int getIntParam(HttpServletRequest req, String name) {
		String value = req.getParameter(name);

		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			System.out.println(
					"RequestUtil: Invalid int param '" + name + "' = '" + value + "' at " + req.getRequestURI());
			return 0;
		}
	}

}
