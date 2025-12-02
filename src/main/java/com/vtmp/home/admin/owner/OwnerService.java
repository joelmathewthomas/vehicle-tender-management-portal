package com.vtmp.home.admin.owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vtmp.auth.AuthBean;
import com.vtmp.auth.AuthDao;
import com.vtmp.home.admin.owner.dto.OwnerDetails;
import com.vtmp.util.DbDao;

/**
 * Handles owner-related operations for the admin module. Currently provides
 * form validation, and can be extended with additional business logic as
 * needed.
 */
public class OwnerService {

	private AuthDao authDao = new AuthDao();
	private OwnerDao ownerDao = new OwnerDao();

	/**
	 * Validates the fields of the given OwnerBean and AuthBean.
	 *
	 * @param authBean  the authentication details submitted from the form
	 * @param ownerBean the owner details submitted from the form
	 * @return a list of validation error messages (empty if valid)
	 */
	public List<String> validateForm(AuthBean authBean, OwnerBean ownerBean) {
		List<String> errors = new ArrayList<>();

		if (authBean.getUsername() == null || !authBean.getUsername().matches("^[A-Za-z0-9_]+$")) {
			errors.add("Invalid Username");
		}
		if (authBean.getPassword() == null || !authBean.getPassword().matches("^.{6,}$")) {
			errors.add("Invalid Password");
		}
		if (ownerBean.getFname() == null || !ownerBean.getFname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid First Name");
		}
		if (ownerBean.getMname() == null || !ownerBean.getMname().matches("^[A-Za-z ]*$")) {
			errors.add("Invalid Middle Name");
		}
		if (ownerBean.getLname() == null || !ownerBean.getLname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid Last Name");
		}
		if (ownerBean.getPhone() == null || !ownerBean.getPhone().matches("^[0-9]{10}$")) {
			errors.add("Invalid Phone Number");
		}
		if (ownerBean.getAddress() == null || !ownerBean.getAddress().matches("^.{3,}$")) {
			errors.add("Invalid Address");
		}
		if (ownerBean.getAadhaar() == null || !ownerBean.getAadhaar().matches("^[0-9]{12}$")) {
			errors.add("Invalid Aadhaar");
		}

		return errors;
	}

	/**
	 * Validates only OwnerBean fields
	 *
	 * @param ownerBean owner details from the form
	 * @return list of validation errors (empty if valid)
	 */
	public List<String> validateForm(OwnerBean ownerBean) {
		List<String> errors = new ArrayList<>();

		if (ownerBean.getFname() == null || !ownerBean.getFname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid First Name");
		}
		if (ownerBean.getMname() == null || !ownerBean.getMname().matches("^[A-Za-z ]*$")) {
			errors.add("Invalid Middle Name");
		}
		if (ownerBean.getLname() == null || !ownerBean.getLname().matches("^[A-Za-z ]+$")) {
			errors.add("Invalid Last Name");
		}
		if (ownerBean.getPhone() == null || !ownerBean.getPhone().matches("^[0-9]{10}$")) {
			errors.add("Invalid Phone Number");
		}
		if (ownerBean.getAddress() == null || !ownerBean.getAddress().matches("^.{3,}$")) {
			errors.add("Invalid Address");
		}
		if (ownerBean.getAadhaar() == null || !ownerBean.getAadhaar().matches("^[0-9]{12}$")) {
			errors.add("Invalid Aadhaar");
		}

		return errors;
	}

	/**
	 * Creates an OwnerBean using form values from the request.
	 *
	 * @param request incoming HTTP form request
	 * @return OwnerBean populated with request parameters
	 */
	public OwnerBean mapRequestToOwner(HttpServletRequest request) {
		OwnerBean bean = new OwnerBean();

		bean.setFname(request.getParameter("fname"));
		bean.setMname(request.getParameter("mname"));
		bean.setLname(request.getParameter("lname"));
		bean.setPhone(request.getParameter("phone"));
		bean.setAddress(request.getParameter("address"));
		bean.setAadhaar(request.getParameter("aadhaar"));

		return bean;
	}

	/**
	 * Retrieves all owners from the database.
	 *
	 * @return a list of OwnerBean objects
	 * @throws SQLException if a database access error occurs
	 */
	public List<OwnerBean> getAllOwners() throws SQLException {
		return ownerDao.getOwners();
	}

	/**
	 * Retrieves both owner information and its associated user account.
	 *
	 * @param ownerId the owner_id to look up
	 * @return OwnerDetails containing OwnerBean and AuthBean, or null if not found
	 * @throws SQLException if a database error occurs
	 */
	public OwnerDetails getOwnerDetails(int ownerId) throws SQLException {

		OwnerBean owner = ownerDao.getOwnerById(ownerId);
		if (owner == null) {
			return null;
		}

		AuthBean user = authDao.getUserById(owner.getUser_id());
		if (user == null) {
			return null;
		}

		return new OwnerDetails(owner, user);
	}

	/**
	 * Retrieves both owner information and its associated user account by its User
	 * ID.
	 *
	 * @param userId the user_id to look up
	 * @return OwnerDetails containing OwnerBean and AuthBean, or null if not found
	 * @throws SQLException if a database error occurs
	 */
	public OwnerDetails getOwnerDetailsByUserID(int userId) throws SQLException {

		OwnerBean owner = ownerDao.getOwnerByUserId(userId);
		if (owner == null) {
			return null;
		}

		AuthBean user = authDao.getUserById(owner.getUser_id());
		if (user == null) {
			return null;
		}

		return new OwnerDetails(owner, user);
	}

	/**
	 * Creates a new user and owner record.
	 *
	 * Inserts the user into the users table, retrieves the generated user_id,
	 * assigns it to the owner, and then inserts the owner into the owners table.
	 *
	 * @param authBean  authentication details for the new user
	 * @param ownerBean owner profile data
	 * @return owner_id if transaction is successful, else -1
	 * @throws SQLException if a database error occurs
	 */
	public int addOwner(AuthBean authBean, OwnerBean ownerBean) throws SQLException {

		try (Connection conn = DbDao.getConnection()) {
			conn.setAutoCommit(false);

			int user_id = authDao.insertUser(conn, authBean);
			if (user_id <= 0) {
				conn.rollback();
				return -1;
			}

			ownerBean.setUser_id(user_id);

			int owner_id = ownerDao.insertOwner(conn, ownerBean);
			if (owner_id != -1) {
				conn.commit();
				return owner_id;
			} else {
				conn.rollback();
				return -1;
			}
		}
	}

	/**
	 * Updates an existing owner record.
	 *
	 * This method simply delegates the update operation to the DAO layer and
	 * returns whether the update was successful.
	 *
	 * @param ownerBean the owner data to update, including the owner_id
	 * @return true if the record was updated, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean updateOwner(OwnerBean ownerBean) throws SQLException {
		return ownerDao.updateOwner(ownerBean);
	}

	/**
	 * Deletes an owner and their associated user account.
	 *
	 * <p>
	 * The method first loads the owner and its linked user record. If both exist,
	 * it deletes the user. With ON DELETE CASCADE configured, the owner's row is
	 * removed automatically.
	 * </p>
	 *
	 * @param ownerId the owner_id to delete
	 * @return true if the user (and therefore owner) was deleted, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean deleteOwner(int ownerId) throws SQLException {
		OwnerBean owner = ownerDao.getOwnerById(ownerId);
		if (owner == null) {
			return false;
		}

		return authDao.deleteUser(owner.getUser_id());
	}

}
