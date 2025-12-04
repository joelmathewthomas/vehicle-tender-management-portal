package com.vtmp.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.util.DbDao;

public class DriverDao {

	private DriverBean mapResultSetToDriver(ResultSet rs) throws SQLException {
		DriverBean driver = new DriverBean();
		driver.setDriver_id(rs.getInt("driver_id"));
		driver.setOwner_id(rs.getInt("owner_id"));
		driver.setFname(rs.getString("driver_fname"));
		driver.setMname(rs.getString("driver_mname"));
		driver.setLname(rs.getString("driver_lname"));
		driver.setPhone(rs.getString("driver_phone"));
		driver.setAddress(rs.getString("driver_address"));
		driver.setAadhaar(rs.getString("driver_aadhaar"));
		driver.setStatus(rs.getString("driver_status"));
		return driver;
	}

	/**
	 * Fetches a driver by its ID.
	 *
	 * @param driverID the ID of the driver to retrieve
	 * @return an DriverBean if found, or null if not found
	 * @throws SQLException if a database error occurs
	 */
	public DriverBean getDriverById(int driverId) throws SQLException {
		String sql = "SELECT * FROM vtmp.drivers WHERE driver_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, driverId);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToDriver(rs);
				}
			}
		}

		return null;
	}

	/**
	 * Fetches all drivers from the database.
	 *
	 * @return a list of DriverBean objects
	 * @throws SQLException if a database error occurs
	 */
	public List<DriverBean> getDrivers() throws SQLException {
		List<DriverBean> drivers = new ArrayList<>();
		String sql = "SELECT * FROM `vtmp`.`drivers` ORDER BY driver_id DESC";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					DriverBean driver = mapResultSetToDriver(rs);
					drivers.add(driver);
				}

			}
		}

		return drivers;

	}
	
	/**
	 * Fetches all drivers from the database by OwnerId.
	 *
	 * @param ownerId the Owner Id to fetch all associated drivers
	 * @return a list of DriverBean objects
	 * @throws SQLException if a database error occurs
	 */
	public List<DriverBean> getDrivers(int ownerId) throws SQLException {
		List<DriverBean> drivers = new ArrayList<>();
		String sql = "SELECT * FROM `vtmp`.`drivers` WHERE owner_id = ? ORDER BY driver_id DESC";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, ownerId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					DriverBean driver = mapResultSetToDriver(rs);
					drivers.add(driver);
				}

			}
		}

		return drivers;

	}
}
