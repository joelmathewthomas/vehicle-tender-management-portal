package com.vtmp.home.admin.owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vtmp.auth.AuthBean;
import com.vtmp.auth.AuthDao;
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
	 * Validates the fields of the given OwnerBean.
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
	 * Creates a new user and owner record.
	 *
	 * Inserts the user into the users table, retrieves the generated user_id,
	 * assigns it to the owner, and then inserts the owner into the owners table.
	 *
	 * @param authBean  authentication details for the new user
	 * @param ownerBean owner profile data
	 * @return true if both inserts succeed, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean addOwner(AuthBean authBean, OwnerBean ownerBean) throws SQLException {

		try (Connection conn = DbDao.getConnection()) {
			conn.setAutoCommit(false);

			int user_id = authDao.insertUser(conn, authBean);
			if (user_id <= 0) {
				conn.rollback();
				return false;
			}

			ownerBean.setUser_id(user_id);

			if (ownerDao.insertOwner(conn, ownerBean)) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		}
	}

}
