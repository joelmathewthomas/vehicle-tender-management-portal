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
	
	/**
	 * Safely parses an float request parameter. Returns 0 if the parameter is
	 * missing or invalid.
	 */
	public static float getFloatParam(HttpServletRequest req, String name) {
		String value = req.getParameter(name);

		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			System.out.println(
					"RequestUtil: Invalid float param '" + name + "' = '" + value + "' at " + req.getRequestURI());
			return 0;
		}
	}

}
