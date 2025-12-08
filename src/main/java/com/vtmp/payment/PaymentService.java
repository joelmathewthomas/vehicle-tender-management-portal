package com.vtmp.payment;

import java.sql.SQLException;

import com.vtmp.tender.TenderBean;

/**
 * Service layer for payment operations.
 */
public class PaymentService {

	private final PaymentDao paymentDao;

	public PaymentService() {
		this.paymentDao = new PaymentDao();
	}

	/**
	 * Calculates the payment amount for the given tender and stores it in the
	 * database.
	 * 
	 * <p>
	 * Payment calculation formula:
	 * </p>
	 * 
	 * <pre>
	 * fuelUsed  = tender_distance / mileage
	 * fuelCost  = fuelUsed * fuel_rate
	 * amount    = fuelCost + salary
	 * </pre>
	 *
	 * @param tender the tender details used to compute the payment
	 * @return the generated payment ID, or -1 if the insert fails
	 * @throws SQLException if a database access error occurs
	 */
	public int addPayment(TenderBean tender) throws SQLException {
		PaymentBean payment = new PaymentBean();
		payment.setTender_id(tender.getTender_id());

		final float MILEAGE = 10f;
		float fuelUsed = tender.getTender_distance() / MILEAGE;
		float fuelCost = fuelUsed * tender.getTender_fuel_rate();
		float amount = fuelCost + tender.getTender_salary();

		payment.setPayment_amount(amount);

		return paymentDao.insertPayment(payment);
	}

}
