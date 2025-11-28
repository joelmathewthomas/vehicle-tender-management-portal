package com.vtmp.home.admin.owner;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles owner-related operations for the admin module. Currently provides
 * form validation, and can be extended with additional business logic as
 * needed.
 */
public class OwnerService {

	/**
	 * Validates the fields of the given OwnerBean.
	 *
	 * @param ownerBean the data submitted from the form
	 * @return a list of validation error messages (empty if valid)
	 */
	public static List<String> validateForm(OwnerBean ownerBean) {
		List<String> errors = new ArrayList<>();

		if (ownerBean.getUsername() == null || !ownerBean.getUsername().matches("^[A-Za-z0-9_]+$")) {
			errors.add("Invalid Username");
		}
		if (ownerBean.getPassword() == null || !ownerBean.getPassword().matches("^.{6,}$")) {
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
	public static OwnerBean mapRequestToOwner(HttpServletRequest request) {
		OwnerBean bean = new OwnerBean();

		bean.setUsername(request.getParameter("username"));
		bean.setPassword(request.getParameter("password"));
		bean.setFname(request.getParameter("fname"));
		bean.setMname(request.getParameter("mname"));
		bean.setLname(request.getParameter("lname"));
		bean.setPhone(request.getParameter("phone"));
		bean.setAddress(request.getParameter("address"));
		bean.setAadhaar(request.getParameter("aadhaar"));

		return bean;
	}

}
