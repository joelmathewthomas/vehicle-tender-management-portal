package com.vtmp.tender.details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.driver.DriverBean;
import com.vtmp.home.admin.location.LocationBean;
import com.vtmp.tender.TenderBean;
import com.vtmp.util.DbDao;

/**
 * DAO class for retrieving combined tender, driver and location data.
 */
public class TenderDetailsDao {

	/**
	 * Maps ResultSet fields to a TenderBean.
	 *
	 * @param rs a valid result set positioned on a row
	 * @return populated TenderBean
	 * @throws SQLException if column access fails
	 */
	private TenderBean mapTenderFromResultSet(ResultSet rs) throws SQLException {
		TenderBean tender = new TenderBean();
		tender.setTender_id(rs.getInt("tender_id"));
		tender.setVehicle_id(rs.getInt("vehicle_id"));
		tender.setTender_date(rs.getDate("tender_date"));
		tender.setTender_distance(rs.getFloat("tender_distance"));
		tender.setTender_fuel_rate(rs.getFloat("tender_fuel_rate"));
		tender.setTender_salary(rs.getFloat("tender_salary"));
		tender.setTender_status(rs.getString("tender_status"));
		return tender;
	}

	/**
	 * Maps ResultSet fields to a DriverBean.
	 *
	 * @param rs a valid result set positioned on a row
	 * @return populated DriverBean
	 * @throws SQLException if column access fails
	 */
	private DriverBean mapDriverFromResultSet(ResultSet rs) throws SQLException {
		DriverBean driver = new DriverBean();
		driver.setDriver_id(rs.getInt("driver_id"));
		driver.setOwner_id(rs.getInt("owner_id"));
		driver.setFname(rs.getString("driver_fname"));
		driver.setMname(rs.getString("driver_mname"));
		driver.setLname(rs.getString("driver_lname"));
		return driver;
	}

	/**
	 * Maps ResultSet fields to a LocationBean.
	 *
	 * @param rs a valid result set positioned on a row
	 * @return populated LocationBean
	 * @throws SQLException if column access fails
	 */
	private LocationBean mapLocationFromResultSet(ResultSet rs) throws SQLException {
		LocationBean location = new LocationBean();
		location.setLocation_id(rs.getInt("location_id"));
		location.setLocation_name(rs.getString("location_name"));
		return location;
	}

	/**
	 * Retrieves full tender, driver and location details for a specific tender ID.
	 *
	 * @param tender_id unique identifier of the tender
	 * @return a TenderDetails object, or null if not found
	 * @throws SQLException if database access fails
	 */
	public TenderDetails getTenderDetailsById(int tender_id) throws SQLException {

		String sql = "SELECT " + " t.tender_id, t.vehicle_id, t.tender_date, t.tender_distance, "
				+ " t.tender_fuel_rate, t.tender_salary, t.tender_status, " + " l.location_id, l.location_name, "
				+ " d.driver_id, d.owner_id, d.driver_fname, d.driver_mname, d.driver_lname " + "FROM vtmp.tenders t "
				+ "LEFT JOIN vtmp.locations l ON t.location_id = l.location_id "
				+ "LEFT JOIN vtmp.drivers d ON t.driver_id = d.driver_id " + "WHERE t.tender_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, tender_id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					TenderDetails details = new TenderDetails();
					details.setTenderBean(mapTenderFromResultSet(rs));
					details.setDriverBean(mapDriverFromResultSet(rs));
					details.setLocationBean(mapLocationFromResultSet(rs));
					return details;
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves full tender details for all tenders.
	 *
	 * @return list of TenderDetails; empty list if none found
	 * @throws SQLException if database access fails
	 */
	public List<TenderDetails> getTenderDetails() throws SQLException {
		List<TenderDetails> list = new ArrayList<>();
		String sql = "SELECT " + " t.tender_id, t.vehicle_id, t.tender_date, t.tender_distance, "
				+ " t.tender_fuel_rate, t.tender_salary, t.tender_status, " + " l.location_id, l.location_name, "
				+ " d.driver_id, d.owner_id, d.driver_fname, d.driver_mname, d.driver_lname " + "FROM vtmp.tenders t "
				+ "LEFT JOIN vtmp.locations l ON t.location_id = l.location_id "
				+ "LEFT JOIN vtmp.drivers  d ON t.driver_id  = d.driver_id";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				TenderDetails details = new TenderDetails();
				details.setTenderBean(mapTenderFromResultSet(rs));
				details.setDriverBean(mapDriverFromResultSet(rs));
				details.setLocationBean(mapLocationFromResultSet(rs));
				list.add(details);
			}
		}
		return list;
	}

	/**
	 * Retrieves full tender details filtered by status.
	 *
	 * @param tender_status tender status to filter
	 * @return list of TenderDetails; empty if none match
	 * @throws SQLException if database access fails
	 */
	public List<TenderDetails> getTenderDetailsByStatus(String tender_status) throws SQLException {
		List<TenderDetails> list = new ArrayList<>();

		String sql = "SELECT\n" + "    t.tender_id,\n" + "    t.vehicle_id,\n" + "    t.tender_date,\n"
				+ "    t.tender_distance,\n" + "    t.tender_fuel_rate,\n" + "    t.tender_salary,\n"
				+ "    t.tender_status,\n" + "    l.location_id,\n" + "    l.location_name,\n" + "    d.driver_id,\n"
				+ "    d.driver_fname,\n" + "    d.owner_id,\n" + "    d.driver_mname,\n" + "    d.driver_lname\n"
				+ "FROM vtmp.tenders t\n" + "LEFT JOIN vtmp.locations l ON t.location_id = l.location_id\n"
				+ "LEFT JOIN vtmp.drivers  d ON t.driver_id  = d.driver_id\n" + "WHERE t.tender_status = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setString(1, tender_status);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					TenderDetails details = new TenderDetails();
					details.setTenderBean(mapTenderFromResultSet(rs));
					details.setDriverBean(mapDriverFromResultSet(rs));
					details.setLocationBean(mapLocationFromResultSet(rs));
					list.add(details);
				}
			}
		}
		return list;
	}

	/**
	 * Retrieves full tender details filtered by status and owner_id.
	 *
	 * @param tender_status tender status to filter
	 * @param ownerId       owner id to filter
	 * @return list of TenderDetails; empty if none match
	 * @throws SQLException if database access fails
	 */
	public List<TenderDetails> getTenderDetailsByOwnerAndStatus(String tender_status, int ownerId) throws SQLException {
		List<TenderDetails> list = new ArrayList<>();

		String sql = "SELECT " + " t.tender_id, t.vehicle_id, t.tender_date, t.tender_distance, "
				+ " t.tender_fuel_rate, t.tender_salary, t.tender_status, " + " l.location_id, l.location_name, "
				+ " d.driver_id, d.owner_id, d.driver_fname, d.driver_mname, d.driver_lname " + "FROM vtmp.tenders t "
				+ "JOIN vtmp.locations l ON t.location_id = l.location_id "
				+ "JOIN vtmp.drivers d ON t.driver_id = d.driver_id " + "WHERE t.tender_status = ? AND d.owner_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setString(1, tender_status);
			pst.setInt(2, ownerId);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					TenderDetails details = new TenderDetails();
					details.setTenderBean(mapTenderFromResultSet(rs));
					details.setDriverBean(mapDriverFromResultSet(rs));
					details.setLocationBean(mapLocationFromResultSet(rs));
					list.add(details);
				}
			}
		}
		return list;
	}
}
