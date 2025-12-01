package com.vtmp.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		String sql = "SELECT * FROM users WHERE user_name = BINARY ? AND user_password = BINARY ?";

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

	/**
	 * Fetches a user by its user_id.
	 *
	 * @param user_id the ID of the user to retrieve
	 * @return an AuthBean if found, or null if not found
	 * @throws SQLException if a database error occurs
	 */
	public AuthBean getUserById(int user_id) throws SQLException {
		String sql = "SELECT * FROM vtmp.users WHERE user_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, user_id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					AuthBean user = new AuthBean();
					user.setUser_id(rs.getInt("user_id"));
					user.setUsername(rs.getString("user_name"));
					user.setRole(rs.getString("user_role"));
					return user;
				}
			}
		}

		return null;
	}

	/**
	 * Inserts a new user and returns the generated user_id.
	 *
	 * @param conn     Connection object
	 * @param authBean user data to insert
	 * @return generated user_id, or -1 if insert failed
	 * @throws SQLException if a database error occurs
	 */
	public int insertUser(Connection conn, AuthBean authBean) throws SQLException {

		String sql = "INSERT INTO `vtmp`.`users` (`user_name`, `user_password`, `user_role`) VALUES (?, ?, 'owner')";

		try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, authBean.getUsername());
			pst.setString(2, authBean.getPassword());

			if (pst.executeUpdate() == 0)
				return -1;

			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return -1;
	}

	/**
	 * Deletes a user from the database.
	 *
	 * <p>
	 * This removes the row with the given user_id from the `users` table. If
	 * foreign keys are configured with ON DELETE CASCADE, any related owner records
	 * will be deleted automatically.
	 * </p>
	 *
	 * @param user_id the ID of the user to delete
	 * @return true if one row was removed, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean deleteUser(int user_id) throws SQLException {
		String sql = "DELETE FROM `vtmp`.`users` WHERE user_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {

			pst.setInt(1, user_id);
			return pst.executeUpdate() == 1;

		}
	}

}
