package com.vtmp.payment.details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vtmp.home.admin.owner.OwnerBean;
import com.vtmp.payment.PaymentBean;
import com.vtmp.util.DbDao;

/**
 * DAO class for retrieving payment details with associated owner information.
 */
public class PaymentDetailsDao {

    /**
     * Maps a ResultSet row to a PaymentDetails object.
     *
     * @param rs the current ResultSet row
     * @return a populated PaymentDetails instance
     * @throws SQLException if a database access error occurs
     */
    private PaymentDetails mapPaymentDetailsFromResultSet(ResultSet rs) throws SQLException {
        PaymentDetails paymentsList = new PaymentDetails();
        OwnerBean owner = new OwnerBean();
        PaymentBean payment = new PaymentBean();
        
        owner.setOwner_id(rs.getInt("owner_id"));
        payment.setPayment_id(rs.getInt("payment_id"));
        payment.setTender_id(rs.getInt("tender_id"));
        payment.setPayment_amount(rs.getFloat("payment_amount"));
        
        paymentsList.setOwner(owner);
        paymentsList.setPayment(payment);
        return paymentsList;
    }

    /**
     * Retrieves all payment records with their associated owner information.
     *
     * @return list of payment details
     * @throws SQLException if a database access error occurs
     */
    public List<PaymentDetails> getAllPayments() throws SQLException {
        List<PaymentDetails> paymentsList = new ArrayList<>();
        String sql =
                "SELECT p.payment_id, p.tender_id, p.payment_amount, d.owner_id " +
                "FROM payments p " +
                "JOIN tenders t ON p.tender_id = t.tender_id " +
                "JOIN drivers d ON t.driver_id = d.driver_id";

        try (Connection conn = DbDao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                paymentsList.add(mapPaymentDetailsFromResultSet(rs));
            }
        }
        return paymentsList;
    }

    /**
     * Retrieves payment records for a specific owner.
     *
     * @param ownerId the owner ID to filter payments
     * @return list of payment details for the given owner
     * @throws SQLException if a database access error occurs
     */
    public List<PaymentDetails> getAllPaymentsByOwner(int ownerId) throws SQLException {
        List<PaymentDetails> paymentsList = new ArrayList<>();
        String sql =
                "SELECT p.payment_id, p.tender_id, p.payment_amount, d.owner_id " +
                "FROM payments p " +
                "JOIN tenders t ON p.tender_id = t.tender_id " +
                "JOIN drivers d ON t.driver_id = d.driver_id " +
                "WHERE d.owner_id = ?";

        try (Connection conn = DbDao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ownerId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    paymentsList.add(mapPaymentDetailsFromResultSet(rs));
                }
            }
        }
        return paymentsList;
    }
}
