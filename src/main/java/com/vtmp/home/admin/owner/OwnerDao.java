package com.vtmp.home.admin.owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OwnerDao {

	/**
	 * Inserts a new owner record into the database.
	 *
	 * @param conn Connection object
	 * @param ownerBean data for the owner to insert
	 * @return true if the insert was successful, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean insertOwner(Connection conn, OwnerBean ownerBean) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`owners` (`user_id`, `owner_fname`, `owner_mname`, `owner_lname`, `owner_phone`, `owner_address`, `owner_aadhaar`) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, ownerBean.getUser_id());
			pst.setString(2, ownerBean.getFname());
			pst.setString(3, ownerBean.getMname());
			pst.setString(4, ownerBean.getLname());
			pst.setString(5, ownerBean.getPhone());
			pst.setString(6, ownerBean.getAddress());
			pst.setString(7, ownerBean.getAadhaar());

			return pst.executeUpdate() == 1;
		}
	}
}
