package com.vtmp.tender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vtmp.util.DbDao;

/**
 * DAO class for handling tender queries.
 */
public class TenderDao {

	/**
	 * Inserts a new tender record and returns the generated tender ID.
	 *
	 * @param tender the {@link TenderBean} containing tender details to insert
	 * @return the generated tender_id on success, or {@code -1} if insertion fails
	 * @throws SQLException if a database access error occurs
	 */
	public int insertTender(TenderBean tender) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`tenders` (`location_id`, `tender_date`, `tender_distance`, `tender_fuel_rate`, `tender_salary`, `tender_status`) VALUES (?, ?, ?, ?, ?, 'open')";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, tender.getLocation_id());
			pst.setDate(2, tender.getTender_date());
			pst.setFloat(3, tender.getTender_distance());
			pst.setFloat(4, tender.getTender_fuel_rate());
			pst.setFloat(5, tender.getTender_salary());

			if (pst.executeUpdate() == 1) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}
		}

		return -1;
	}

	/**
	 * Accepts a tender by assigning a vehicle and driver to it.
	 *
	 * @param tender_id  the unique ID of the tender to be accepted
	 * @param vehicle_id the vehicle assigned to the tender
	 * @param driver_id  the driver assigned to the tender
	 * @return true if the tender was successfully accepted (updated), false
	 *         otherwise
	 * @throws SQLException if any database error occurs during the update
	 */
	public boolean acceptTender(int tender_id, int vehicle_id, int driver_id) throws SQLException {
		String sql = "UPDATE `vtmp`.`tenders` SET `driver_id` = ?, `vehicle_id` = ?, tender_status = 'accept' WHERE (`tender_id` = ?)";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, driver_id);
			pst.setInt(2, vehicle_id);
			pst.setInt(3, tender_id);

			return pst.executeUpdate() == 1;
		}
	}

}
