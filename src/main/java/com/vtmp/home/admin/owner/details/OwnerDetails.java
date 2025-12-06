package com.vtmp.home.admin.owner.details;

import com.vtmp.auth.AuthBean;
import com.vtmp.home.admin.owner.OwnerBean;

/**
 * A simple wrapper that holds both owner information and the associated user
 * account details.
 *
 * Used when an operation needs to return both OwnerBean and AuthBean.
 */
public class OwnerDetails {

	private OwnerBean owner;
	private AuthBean user;

	/**
	 * Creates a combined owner/user details object.
	 *
	 * @param owner the owner profile data
	 * @param user  the authentication/user account data
	 */
	public OwnerDetails(OwnerBean owner, AuthBean user) {
		this.owner = owner;
		this.user = user;
	}

	/**
	 * @return the owner details as an OwnerBean
	 */
	public OwnerBean getOwner() {
		return owner;
	}

	/**
	 * @return the user credentials as an AuthBean
	 */
	public AuthBean getUser() {
		return user;
	}
}
