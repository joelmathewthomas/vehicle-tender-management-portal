package com.vtmp.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vtmp.util.DbDao;

/**
 * DAO class for handling user authentication queries.
 */
public class AuthDao {

	/**
	 * Checks username and password against the database.
	 * 
	 * @param authBean contains the username and password to validate
	 * @return an AuthBean with user details if authentication succeeds, or null if
	 *         it fails
	 * @throws SQLException if a database error occurs
	 */
	public AuthBean authenticate(AuthBean authBean) throws SQLException {
		String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, authBean.getUsername());
			pst.setString(2, authBean.getPassword());
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					AuthBean result = new AuthBean();
					result.setUser_id(rs.getInt("user_id"));
					result.setUsername(rs.getString("user_name"));
					result.setRole(rs.getString("user_role"));
					return result;
				}
			}

			return null;

		}
	}
}
