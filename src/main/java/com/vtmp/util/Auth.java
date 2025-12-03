package com.vtmp.util;

import javax.servlet.http.HttpSession;

/**
 * Authentication utility for verifying user sessions and roles.
 */
public class Auth {

	/**
	 * Validates that a session exists and that it contains the expected user role.
	 *
	 * @param session the HTTP session to validate; may be {@code null}
	 * @param role    the required user role (e.g., "owner", "admin")
	 * @return {@code true} if the session is valid and the stored role matches;
	 *         {@code false} otherwise
	 */
	public static boolean checkSession(HttpSession session, String role) {

		if (session == null) {
			return false;
		} else if (role.equals(session.getAttribute("userrole"))) {
			return true;
		} else {
			return false;
		}
	}
}
