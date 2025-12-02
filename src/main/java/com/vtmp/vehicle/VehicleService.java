package com.vtmp.vehicle;

import java.sql.SQLException;
import java.util.List;

public class VehicleService {

	private VehicleDao vehicleDao = new VehicleDao();

	/**
	 * Retrieves all vehicles from the database.
	 *
	 * @return a list of VehicleBean objects
	 * @throws SQLException if a database access error occurs
	 */
	public List<VehicleBean> getAllVehicles() throws SQLException {
		return vehicleDao.getVehicles();
	}

	/**
	 * Retrieves all vehicles for a specific owner.
	 *
	 * @param ownerId the ID of the owner whose vehicles should be returned
	 * @return a list of VehicleBean objects belonging to that owner
	 * @throws SQLException if a database access error occurs
	 */
	public List<VehicleBean> getAllVehicles(int ownerId) throws SQLException {
		return vehicleDao.getVehiclesByOwnerId(ownerId);
	}

	/**
	 * Retrieves vehicle details by vehicle_id
	 *
	 * @param vehicleId the vehicle_id to look up
	 * @return VehicleBean containing vehicle details
	 * @throws SQLException if a database error occurs
	 */
	public VehicleBean getVehicleDetails(int vehicleId) throws SQLException {

		return vehicleDao.getVehicleById(vehicleId);
	}

}
