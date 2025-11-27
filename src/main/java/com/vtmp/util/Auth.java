package com.vtmp.util;

import javax.servlet.http.HttpSession;

public class Auth {
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
