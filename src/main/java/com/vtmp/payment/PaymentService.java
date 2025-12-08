package com.vtmp.payment;

import java.sql.Connection;
import java.sql.SQLException;

import com.vtmp.tender.TenderBean;
import com.vtmp.tender.TenderDao;
import com.vtmp.util.DbDao;

/**
 * Service layer for payment operations.
 */
public class PaymentService {

	private final PaymentDao paymentDao;
	private final TenderDao tenderDao;

	public PaymentService() {
		this.paymentDao = new PaymentDao();
		this.tenderDao = new TenderDao();
	}

	/**
	 * Computes the payment amount for the specified tender and stores it in the
	 * database. The associated tender record is also marked as paid. Both
	 * operations run within a single transaction; failure of either causes a
	 * rollback.
	 *
	 * <p>
	 * Payment calculation:
	 * </p>
	 * 
	 * <pre>
	 * fuelUsed  = tender_distance / mileage
	 * fuelCost  = fuelUsed * fuel_rate
	 * amount    = fuelCost + salary
	 * </pre>
	 *
	 * @param tender the tender from which the payment amount is derived
	 * @return the generated payment ID, or -1 if the operation fails
	 * @throws SQLException if a database access or transaction error occurs
	 */

	public int addPayment(TenderBean tender) throws SQLException {
		try (Connection conn = DbDao.getConnection()) {
			conn.setAutoCommit(false);
			PaymentBean payment = new PaymentBean();
			payment.setTender_id(tender.getTender_id());

			final float MILEAGE = 10f;
			float fuelUsed = tender.getTender_distance() / MILEAGE;
			float fuelCost = fuelUsed * tender.getTender_fuel_rate();
			float amount = fuelCost + tender.getTender_salary();

			payment.setPayment_amount(amount);
			int payment_id = paymentDao.insertPayment(conn, payment);

			if (payment_id < 1) {
				conn.rollback();
				return -1;
			}

			if (tenderDao.payTender(conn, tender.getTender_id())) {
				conn.commit();
				return payment_id;
			} else {
				conn.rollback();
				return -1;
			}

		}
	}

}
