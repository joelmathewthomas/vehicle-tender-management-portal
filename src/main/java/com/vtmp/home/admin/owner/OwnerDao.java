package com.vtmp.home.admin.owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.util.DbDao;

public class OwnerDao {

	/**
	 * Fetches all owners from the database.
	 *
	 * @return a list of OwnerBean objects
	 * @throws SQLException if a database error occurs
	 */
	public List<OwnerBean> getOwners() throws SQLException {
		List<OwnerBean> owners = new ArrayList<>();
		String sql = "SELECT * FROM `vtmp`.`owners`";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					OwnerBean tmpOwnerBean = new OwnerBean();
					tmpOwnerBean.setOwner_id(rs.getInt("owner_id"));
					tmpOwnerBean.setUser_id(rs.getInt("user_id"));
					tmpOwnerBean.setFname(rs.getString("owner_fname"));
					tmpOwnerBean.setMname(rs.getString("owner_mname"));
					tmpOwnerBean.setLname(rs.getString("owner_lname"));
					tmpOwnerBean.setPhone(rs.getString("owner_phone"));
					tmpOwnerBean.setAddress(rs.getString("owner_address"));
					tmpOwnerBean.setAadhaar(rs.getString("owner_aadhaar"));
					owners.add(tmpOwnerBean);
				}

			}
		}

		return owners;

	}

	/**
	 * Inserts a new owner record into the database.
	 *
	 * @param conn      Connection object
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
