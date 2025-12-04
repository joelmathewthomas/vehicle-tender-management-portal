package com.vtmp.driver;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing Driver operations.
 */
public class DriverService {

	private final DriverDao driverDao = new DriverDao();

	/**
	 * Retrieves a driver by ID.
	 *
	 * @param driverId the driver ID
	 * @return the DriverBean if found, or null if not found
	 * @throws SQLException if a database access error occurs
	 */
	public DriverBean getDriverById(int driverId) throws SQLException {
		return driverDao.getDriverById(driverId);
	}

	/**
	 * Retrieves all drivers.
	 *
	 * @return a list of DriverBean objects
	 * @throws SQLException if a database access error occurs
	 */
	public List<DriverBean> getDrivers() throws SQLException {
		return driverDao.getDrivers();
	}

	/**
	 * Retrieves all drivers belonging to a specific owner.
	 *
	 * @param ownerId the owner ID
	 * @return a list of DriverBean objects
	 * @throws SQLException if a database access error occurs
	 */
	public List<DriverBean> getDrivers(int ownerId) throws SQLException {
		return driverDao.getDrivers(ownerId);
	}
	
	/**
	 * Toggles the approval status of a driver.
	 *
	 * @param driverId the ID of the driver to toggle
	 * @return true if the status was updated; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean toggleDriverStatus(int driverId) throws SQLException {
		return driverDao.toggleDriverStatus(driverId);
	}

}
