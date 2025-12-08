package com.vtmp.payment.details;

import com.vtmp.home.admin.owner.OwnerBean;
import com.vtmp.payment.PaymentBean;

/**
 * DTO representing payment details along with owner_id information
 */
public class PaymentDetails {
	private OwnerBean owner;
	private PaymentBean payment;

	public OwnerBean getOwner() {
		return owner;
	}

	public void setOwner(OwnerBean owner) {
		this.owner = owner;
	}

	public PaymentBean getPayment() {
		return payment;
	}

	public void setPayment(PaymentBean payment) {
		this.payment = payment;
	}

}
