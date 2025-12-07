package com.vtmp.vehicle;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vtmp.util.RequestUtil;

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

	/**
	 * Creates a new vehicle record
	 *
	 * @param vehicleBean the vehicle details to insert
	 * @return the generated vehicle ID, or -1 if insertion failed
	 * @throws SQLException if a database access error occurs
	 */
	public int addVehicle(VehicleBean vehicleBean) throws SQLException {
		return vehicleDao.insertVehicle(vehicleBean);
	}

	/**
	 * Toggles the approval status of a vehicle.
	 *
	 * @param vehicleId the ID of the vehicle to toggle
	 * @return true if the status was updated; false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean toggleVehicleStatus(int vehicleId) throws SQLException {
		return vehicleDao.toggleVehicleStatus(vehicleId);
	}

	/**
	 * Deletes a vehicle by its ID.
	 *
	 * @param vehicleId the ID of the vehicle to delete
	 * @return true if the deletion succeeded; false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean deleteVehicle(int vehicleId) throws SQLException {
		return vehicleDao.deleteVehicle(vehicleId);
	}

	/**
	 * Creates a VehicleBean using form values from the request.
	 *
	 * @param request the incoming HTTP form request
	 * @return a VehicleBean populated with request parameters, or {@code null} if
	 *         the owner_id is invalid
	 */
	public VehicleBean mapRequestToVehicle(HttpServletRequest request) {
		VehicleBean bean = new VehicleBean();

		bean.setOwner_id(RequestUtil.getIntParam(request, "owner_id"));
		if (bean.getOwner_id() < 1) {
			return null;
		}

		bean.setVehicle_no(request.getParameter("vehicle_no"));
		bean.setVehicle_type(request.getParameter("vehicle_type"));
		bean.setVehicle_status("unapproved");

		return bean;
	}

	/**
	 * Validates VehicleBean fields.
	 *
	 * @param vehicleBean the vehicle data to validate
	 * @return list of validation errors (empty if valid)
	 */
	public List<String> validateForm(VehicleBean vehicleBean) {
		List<String> errors = new ArrayList<>();

		if (vehicleBean.getOwner_id() < 1) {
			errors.add("Invalid Owner ID");
		}

		String vehicleNoPattern = "^[A-Z]{2}\\s?\\d{1,2}\\s?[A-Z]{0,3}\\s?\\d{1,4}$";
		if (vehicleBean.getVehicle_no() == null || !vehicleBean.getVehicle_no().matches(vehicleNoPattern)) {
			errors.add("Invalid Vehicle Number");
		}

		if (vehicleBean.getVehicle_type() == null
				|| !(vehicleBean.getVehicle_type().equals("car") || vehicleBean.getVehicle_type().equals("bus"))) {
			errors.add("Invalid Vehicle Type");
		}

		if (vehicleBean.getVehicle_status() == null || !(vehicleBean.getVehicle_status().equals("approved")
				|| vehicleBean.getVehicle_status().equalsIgnoreCase("unapproved"))) {
			errors.add("Invalid Vehicle Status");
		}

		return errors;
	}

	/**
	 * Returns the list of approved vehicles belonging to the specified owner that
	 * are not allocated to another accepted tender on the given date.
	 *
	 * @param ownerId     the owner's unique identifier
	 * @param tender_date the date to check availability for
	 * @return list of available vehicles; empty if none found
	 * @throws SQLException if database access fails
	 */
	public List<VehicleBean> getFreeVehicles(int ownerId, Date tender_date) throws SQLException {
		return vehicleDao.getFreeVehicles(ownerId, tender_date);
	}

}
