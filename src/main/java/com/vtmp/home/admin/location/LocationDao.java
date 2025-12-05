package com.vtmp.home.admin.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.util.DbDao;

public class LocationDao {

	/**
	 * Fetches a LocationBean for the given ID.
	 *
	 * @param locationId the database primary key
	 * @return LocationBean if found, otherwise null
	 * @throws SQLException if a database access error occurs
	 */
	public LocationBean getLocationById(int locationId) throws SQLException {
		String sql = "SELECT location_id, location_name FROM vtmp.locations WHERE location_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, locationId);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					LocationBean location = new LocationBean();
					location.setLocation_id(rs.getInt("location_id"));
					location.setLocation_name(rs.getString("location_name"));
					return location;
				}
				return null;
			}
		}

	}

	/**
	 * Retrieves all locations from the database.
	 *
	 * @return a list of LocationBean objects; empty if none found
	 * @throws SQLException if a database access error occurs
	 */
	public List<LocationBean> getLocations() throws SQLException {
		List<LocationBean> locations = new ArrayList<>();
		String sql = "SELECT location_id, location_name FROM vtmp.locations";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				LocationBean location = new LocationBean();
				location.setLocation_id(rs.getInt("location_id"));
				location.setLocation_name(rs.getString("location_name"));
				locations.add(location);
			}
		}

		return locations;
	}

	/**
	 * Inserts a new location into the database.
	 *
	 * @param location a LocationBean containing the data to be inserted
	 * @return generated location ID if successful, otherwise -1
	 * @throws SQLException if a database access error occurs
	 */
	public int addLocation(LocationBean location) throws SQLException {

		String sql = "INSERT INTO `vtmp`.`locations` (`location_name`) VALUES (?)";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, location.getLocation_name());

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
	 * Deletes a location by its ID.
	 *
	 * @param locationId the primary key of the location to delete
	 * @return true if a record was deleted, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean deleteLocation(int locationId) throws SQLException {
		String sql = "DELETE FROM `vtmp`.`locations` WHERE location_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, locationId);

			return pst.executeUpdate() == 1;
		}
	}
}