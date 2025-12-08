package com.vtmp.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentDao {

	/**
	 * Inserts a payment record using the provided connection.
	 *
	 * @param conn    the database connection to use
	 * @param payment the payment data containing tender ID and amount
	 * @return the generated payment ID, or -1 if insertion failed
	 * @throws SQLException if a database access error occurs
	 */
	public int insertPayment(Connection conn, PaymentBean payment) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`payments` (`tender_id`, `payment_amount`) VALUES (?, ?)";

		try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, payment.getTender_id());
			pst.setFloat(2, payment.getPayment_amount());

			if (pst.executeUpdate() == 1) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}

			return -1;
		}
	}
}
