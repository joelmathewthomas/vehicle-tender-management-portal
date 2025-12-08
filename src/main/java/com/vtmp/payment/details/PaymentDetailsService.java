package com.vtmp.payment.details;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for business logic related to payment details.
 */
public class PaymentDetailsService {

	private final PaymentDetailsDao paymentDetailsDao;

	/**
	 * Creates a new instance of PaymentDetailsService.
	 */
	public PaymentDetailsService() {
		this.paymentDetailsDao = new PaymentDetailsDao();
	}

	/**
	 * Retrieves all payment records.
	 *
	 * @return list of payment details
	 * @throws SQLException if a database access error occurs
	 */
	public List<PaymentDetails> getAllPayments() throws SQLException {
		return paymentDetailsDao.getAllPayments();
	}

	/**
	 * Retrieves payment records for a specific owner.
	 *
	 * @param ownerId the owner ID to filter payment details
	 * @return list of payment details for the given owner
	 * @throws SQLException if a database access error occurs
	 */
	public List<PaymentDetails> getPaymentsByOwner(int ownerId) throws SQLException {
		return paymentDetailsDao.getAllPaymentsByOwner(ownerId);
	}

}
