package com.vtmp.vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}