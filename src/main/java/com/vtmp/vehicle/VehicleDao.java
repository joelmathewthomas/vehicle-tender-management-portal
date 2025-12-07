package com.vtmp.vehicle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.util.DbDao;

/**
 * DAO class for accessing vehicle data from the database.
 */
public class VehicleDao {

	/**
	 * Maps the current ResultSet row to a VehicleBean.
	 *
	 * @param rs the ResultSet positioned at a valid row
	 * @return a populated VehicleBean
	 * @throws SQLException if a column read fails
	 */
	private VehicleBean mapVehicle(ResultSet rs) throws SQLException {
		VehicleBean v = new VehicleBean();
		v.setVehicle_id(rs.getInt("vehicle_id"));
		v.setOwner_id(rs.getInt("owner_id"));
		v.setVehicle_no(rs.getString("vehicle_no"));
		v.setVehicle_type(rs.getString("vehicle_type"));
		v.setVehicle_status(rs.getString("vehicle_status"));
		return v;
	}

	/**
	 * Retrieves a vehicle by its ID.
	 *
	 * @param vehicleId the ID of the vehicle to fetch
	 * @return the matching VehicleBean, or null if not found
	 * @throws SQLException if a database error occurs
	 */
	public VehicleBean getVehicleById(int vehicleId) throws SQLException {
		String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, vehicleId);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return mapVehicle(rs);
				}
			}
		}

		return null;
	}

	/**
	 * Retrieves all vehicles from the database.
	 *
	 * @return a list of all VehicleBean records
	 * @throws SQLException if a database error occurs
	 */
	public List<VehicleBean> getVehicles() throws SQLException {
		List<VehicleBean> vehicles = new ArrayList<>();
		String sql = "SELECT * FROM vehicles";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				vehicles.add(mapVehicle(rs));
			}
		}

		return vehicles;
	}

	/**
	 * Retrieves all vehicles from the database by Owner ID.
	 *
	 * @param ownerId the ownerId to look up vehicles
	 * @return a list of all VehicleBean records
	 * @throws SQLException if a database error occurs
	 */
	public List<VehicleBean> getVehiclesByOwnerId(int ownerId) throws SQLException {
		List<VehicleBean> vehicles = new ArrayList<>();
		String sql = "SELECT * FROM vehicles WHERE owner_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, ownerId);
			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					vehicles.add(mapVehicle(rs));
				}
			}
		}

		return vehicles;
	}

	/**
	 * Inserts a new vehicle record into the database.
	 *
	 * @param vehicle the VehicleBean containing vehicle details
	 * @return the generated vehicle ID, or -1 if insertion failed
	 * @throws SQLException if a database access error occurs
	 */
	public int insertVehicle(VehicleBean vehicle) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`vehicles` (`owner_id`, `vehicle_no`, `vehicle_type`, `vehicle_status`) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setInt(1, vehicle.getOwner_id());
			pst.setString(2, vehicle.getVehicle_no());
			pst.setString(3, vehicle.getVehicle_type().toLowerCase());
			pst.setString(4, vehicle.getVehicle_status().toLowerCase());

			int rows = pst.executeUpdate();

			if (rows == 0) {
				return -1;
			}

			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return -1;
	}

	/**
	 * Toggles the vehicle_status field between 'approved' and 'unapproved' for the
	 * given vehicle ID. Performs the toggle in a single SQL UPDATE using a CASE
	 * expression.
	 *
	 * @param vehicleId the ID of the vehicle to update
	 * @return true if exactly one row was updated; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean toggleVehicleStatus(int vehicleId) throws SQLException {
		String sql = "UPDATE vtmp.vehicles " + "SET vehicle_status = CASE "
				+ "    WHEN vehicle_status = 'approved' THEN 'unapproved' "
				+ "    WHEN vehicle_status = 'unapproved' THEN 'approved' " + "    ELSE vehicle_status " + "END "
				+ "WHERE vehicle_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setInt(1, vehicleId);
			return pst.executeUpdate() == 1;
		}
	}

	/**
	 * Deletes a vehicle record by its ID.
	 *
	 * @param vehicleId the ID of the vehicle to delete
	 * @return true if exactly one record was deleted; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean deleteVehicle(int vehicleId) throws SQLException {
		String sql = "DELETE FROM vtmp.vehicles WHERE vehicle_id = ?";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, vehicleId);
			return pst.executeUpdate() == 1;
		}
	}

	/**
	 * Retrieves approved vehicles owned by the specified owner that are not
	 * allocated to another accepted tender on the given date.
	 *
	 * @param ownerId     the owner's unique identifier
	 * @param tender_date the date to check for conflicting allocations
	 * @return a list of available vehicles; empty if none found
	 * @throws SQLException if database access fails
	 */
	public List<VehicleBean> getFreeVehicles(int ownerId, Date tender_date) throws SQLException {
		List<VehicleBean> vehicles = new ArrayList<>();
		String sql = "SELECT v.*\n" + "FROM vtmp.vehicles v\n" + "WHERE v.owner_id = ?\n"
				+ "  AND v.vehicle_status = 'approved'\n" + "  AND v.vehicle_id NOT IN (\n"
				+ "        SELECT t.vehicle_id \n" + "        FROM vtmp.tenders t\n"
				+ "        WHERE t.tender_date = ? AND t.tender_status = 'accept'\n" + "  )";

		try (Connection conn = DbDao.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, ownerId);
			pst.setDate(2, tender_date);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					vehicles.add(mapVehicle(rs));
				}
			}
		}

		return vehicles;
	}
}