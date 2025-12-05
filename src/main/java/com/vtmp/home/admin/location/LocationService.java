package com.vtmp.home.admin.location;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LocationService {

	private final LocationDao locationDao;

	public LocationService() {
		this.locationDao = new LocationDao();
	}

	/**
	 * Returns a location by its ID.
	 *
	 * @param locationId the location primary key
	 * @return LocationBean if found, otherwise null
	 * @throws SQLException if DAO layer fails
	 */
	public LocationBean getLocationById(int locationId) throws SQLException {
		if (locationId <= 0) {
			return null;
		}
		return locationDao.getLocationById(locationId);
	}

	/**
	 * Returns a list of all locations.
	 *
	 * @return list of LocationBean; empty if none
	 * @throws SQLException if DAO layer fails
	 */
	public List<LocationBean> getAllLocations() throws SQLException {
		List<LocationBean> list = locationDao.getLocations();
		return (list != null) ? list : Collections.emptyList();
	}

	/**
	 * Adds a new location.
	 *
	 * @param name name of the location
	 * @return generated ID if successful, -1 otherwise
	 * @throws SQLException if DAO layer fails
	 */
	public int addLocation(String name) throws SQLException {
		if (name == null || name.isBlank()) {
			return -1;
		}

		LocationBean bean = new LocationBean();
		bean.setLocation_name(name.trim());

		return locationDao.addLocation(bean);
	}

	/**
	 * Deletes a location by its ID.
	 *
	 * @param locationId the location primary key
	 * @return true if deleted, false otherwise
	 * @throws SQLException if DAO layer fails
	 */
	public boolean deleteLocation(int locationId) throws SQLException {
		if (locationId <= 0) {
			return false;
		}
		return locationDao.deleteLocation(locationId);
	}

}
