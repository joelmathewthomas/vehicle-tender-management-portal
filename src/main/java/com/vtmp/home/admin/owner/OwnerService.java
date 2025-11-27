package com.vtmp.home.admin.owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles owner-related operations for the admin module.
 * Currently provides form validation, and can be extended
 * with additional business logic as needed.
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

        if (!ownerBean.getUsername().matches("^[A-Za-z0-9_]+$")) {
            errors.add("Invalid Username");
        }
        if (!ownerBean.getPassword().matches("^.{6,}$")) {
            errors.add("Invalid Password");
        }
        if (!ownerBean.getFname().matches("^[A-Za-z ]+$")) {
            errors.add("Invalid First Name");
        }
        if (!ownerBean.getMname().matches("^[A-Za-z ]*$")) {
            errors.add("Invalid Middle Name");
        }
        if (!ownerBean.getLname().matches("^[A-Za-z ]+$")) {
            errors.add("Invalid Last Name");
        }
        if (!ownerBean.getPhone().matches("^[0-9]{10}$")) {
            errors.add("Invalid Phone Number");
        }
        if (!ownerBean.getAddress().matches("^.{3,}$")) {
            errors.add("Invalid Address");
        }
        if (!ownerBean.getAadhaar().matches("^[0-9]{12}$")) {
            errors.add("Invalid Aadhaar");
        }

        return errors;
    }
}
