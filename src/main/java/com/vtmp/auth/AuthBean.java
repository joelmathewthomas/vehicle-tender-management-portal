package com.vtmp.auth;

/**
 * Bean class used for storing basic login/authentication data. Holds the
 * user_id, username, password, and the user's role in the system.
 */
public class AuthBean {
	private int user_id;
	private String username;
	private String password;
	private String role;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
