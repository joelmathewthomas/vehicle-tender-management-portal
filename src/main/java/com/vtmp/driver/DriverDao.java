package com.vtmp.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String sql = "SELECT * FROM `vtmp`.`drivers`";

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
		String sql = "SELECT * FROM `vtmp`.`drivers` WHERE owner_id = ?";

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

	/**
	 * Inserts a new driver record into the database.
	 *
	 * @param driverBean data for the owner to insert
	 * @return driver_id if the insert was successful, -1 otherwise
	 * @throws SQLException if a database error occurs
	 */
	public int insertDriver(DriverBean driverBean) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`drivers` (`owner_id`, `driver_fname`, `driver_mname`, `driver_lname`, `driver_phone`, `driver_address`, `driver_aadhaar`, `driver_status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setInt(1, driverBean.getOwner_id());
			pst.setString(2, driverBean.getFname());
			pst.setString(3, driverBean.getMname());
			pst.setString(4, driverBean.getLname());
			pst.setString(5, driverBean.getPhone());
			pst.setString(6, driverBean.getAddress());
			pst.setString(7, driverBean.getAadhaar());
			pst.setString(8, driverBean.getStatus());

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
	 * Toggles the driver_status field between 'approved' and 'unapproved' for the
	 * given driver ID. Performs the toggle in a single SQL UPDATE using a CASE
	 * expression.
	 *
	 * @param driverId the ID of the driver to update
	 * @return true if exactly one row was updated; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean toggleDriverStatus(int driverId) throws SQLException {
		String sql = "UPDATE vtmp.drivers " + "SET driver_status = CASE "
				+ "    WHEN driver_status = 'approved' THEN 'unapproved' "
				+ "    WHEN driver_status = 'unapproved' THEN 'approved' " + "    ELSE driver_status " + "END "
				+ "WHERE driver_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, driverId);
			return pst.executeUpdate() == 1;
		}
	}

	/**
	 * Updates an existing driver record in the database.
	 *
	 * Fields such as first name, middle name, last name, phone, and address are
	 * updated using values from the provided DriverBean. The middle name is stored
	 * as NULL if it is missing or empty. Driver status is also set to unapproved.
	 *
	 * @param driverBean contains the updated driver details and the driver_id to
	 *                   update
	 * @return true if exactly one row was updated, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean updateDriver(DriverBean driverBean) throws SQLException {
		String sql = "UPDATE `vtmp`.`drivers` SET `driver_fname` = ?, `driver_mname` = ?, `driver_lname` = ?, `driver_phone` = ?, `driver_address` = ?, `driver_status` = ? WHERE `driver_id` = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setString(1, driverBean.getFname());

			if (driverBean.getMname() == null || driverBean.getMname().isEmpty()) {
				pst.setNull(2, java.sql.Types.VARCHAR);
			} else {
				pst.setString(2, driverBean.getMname().trim());
			}

			pst.setString(3, driverBean.getLname());
			pst.setString(4, driverBean.getPhone());
			pst.setString(5, driverBean.getAddress());
			pst.setString(6, driverBean.getStatus());
			pst.setInt(7, driverBean.getDriver_id());

			return pst.executeUpdate() == 1;
		}
	}

	/**
	 * Deletes a driver record by its ID.
	 *
	 * @param driverId the ID of the driver to delete
	 * @return true if exactly one record was deleted; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean deleteDriver(int driverId) throws SQLException {
		String sql = "DELETE FROM vtmp.drivers WHERE driver_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, driverId);
			return pst.executeUpdate() == 1;
		}
	}

	/**
	 * Retrieves approved drivers owned by the specified owner that are not
	 * allocated to another accepted tender on the given date.
	 *
	 * @param ownerId     the owner's unique identifier
	 * @param tender_date the date to check for conflicting allocations
	 * @return a list of available drivers; empty if none found
	 * @throws SQLException if database access fails
	 */
	public List<DriverBean> getFreeDrivers(int ownerId, Date tender_date) throws SQLException {
		List<DriverBean> drivers = new ArrayList<>();
		String sql = "SELECT d.*\n" + "FROM vtmp.drivers d\n" + "WHERE d.owner_id = ?\n"
				+ "  AND d.driver_status = 'approved'\n" + "  AND d.driver_id NOT IN (\n"
				+ "        SELECT t.driver_id\n" + "        FROM vtmp.tenders t\n"
				+ "        WHERE t.tender_date = ? AND t.tender_status = 'accept'\n" + "  )";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, ownerId);
			pst.setDate(2, tender_date);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					drivers.add(mapResultSetToDriver(rs));
				}
			}
		}

		return drivers;
	}
}
