package com.vtmp.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vtmp.util.RequestUtil;

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
	 * Adds a new driver without validation.
	 *
	 * @param driverBean the driver details to insert
	 * @return the generated driver ID, or -1 if insertion failed
	 * @throws SQLException if a database access error occurs
	 */
	public int addDriver(DriverBean driverBean) throws SQLException {
		return driverDao.insertDriver(driverBean);
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

	/**
	 * Updates an existing driver record.
	 *
	 * This method simply delegates the update operation to the DAO layer and
	 * returns whether the update was successful.
	 *
	 * @param driverBean the driver data to update, including the driver_id
	 * @return true if the record was updated, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean updateDriver(DriverBean driverBean) throws SQLException {
		return driverDao.updateDriver(driverBean);
	}

	/**
	 * Deletes a driver by its ID.
	 *
	 * @param driverId the ID of the vehicle to delete
	 * @return true if the deletion succeeded; false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean deleteDriver(int driverId) throws SQLException {
		return driverDao.deleteDriver(driverId);
	}

	/**
	 * Creates an DriverBean using form values from the request.
	 *
	 * @param request incoming HTTP form request
	 * @return DriverBean populated with request parameters, null if invalid Owner
	 *         Id
	 */
	public DriverBean mapRequestToDriver(HttpServletRequest request) {
		DriverBean bean = new DriverBean();

		bean.setOwner_id(RequestUtil.getIntParam(request, "owner_id"));
		if (bean.getOwner_id() < 1) {
			return null;
		}
		bean.setFname(request.getParameter("fname"));
		bean.setMname(request.getParameter("mname"));
		bean.setLname(request.getParameter("lname"));
		bean.setPhone(request.getParameter("phone"));
		bean.setAddress(request.getParameter("address"));
		bean.setAadhaar(request.getParameter("aadhaar"));
		bean.setStatus("unapproved");

		return bean;
	}

	/**
	 * Validates DriverBean fields
	 *
	 * @param driverBean driver details from the form
	 * @return list of validation errors (empty if valid)
	 */
	public List<String> validateForm(DriverBean driverBean) {
		List<String> errors = new ArrayList<>();

		if (driverBean.getOwner_id() < 1) {
			errors.add("Invalid Owner ID");
		}

		if (driverBean.getFname() == null || !driverBean.getFname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid First Name");
		}
		if (driverBean.getMname() == null || !driverBean.getMname().matches("^[A-Za-z ]*$")) {
			errors.add("Invalid Middle Name");
		}
		if (driverBean.getLname() == null || !driverBean.getLname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid Last Name");
		}
		if (driverBean.getPhone() == null || !driverBean.getPhone().matches("^[0-9]{10}$")) {
			errors.add("Invalid Phone Number");
		}
		if (driverBean.getAddress() == null || !driverBean.getAddress().matches("^.{3,}$")) {
			errors.add("Invalid Address");
		}
		if (driverBean.getAadhaar() == null || !driverBean.getAadhaar().matches("^[0-9]{12}$")) {
			errors.add("Invalid Aadhaar");
		}

		if (driverBean.getStatus() == null || !(driverBean.getStatus().equals("approved")
				|| driverBean.getStatus().equalsIgnoreCase("unapproved"))) {
			errors.add("Invalid Vehicle Status");
		}

		return errors;
	}

}
