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
}
