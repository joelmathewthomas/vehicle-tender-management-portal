package com.vtmp.payment;

/**
 * Bean class for holding payment details
 */
public class PaymentBean {
	private int payment_id;
	private int tender_id;
	private float payment_amount;

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public int getTender_id() {
		return tender_id;
	}

	public void setTender_id(int tender_id) {
		this.tender_id = tender_id;
	}

	public float getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(float payment_amount) {
		this.payment_amount = payment_amount;
	}

}
