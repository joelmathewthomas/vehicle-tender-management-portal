package com.vtmp.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vtmp.util.DbDao;

public class PaymentDao {

	/**
	 * Inserts a new payment record and returns the generated payment ID.
	 *
	 * @param payment the payment bean containing tender ID and amount
	 * @return generated payment ID, or -1 if the insert fails
	 * @throws SQLException if a database access error occurs
	 */
	public int insertPayment(PaymentBean payment) throws SQLException {
		String sql = "INSERT INTO `vtmp`.`payments` (`tender_id`, `payment_amount`) VALUES (?, ?)";

		try (Connection conn = DbDao.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
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
