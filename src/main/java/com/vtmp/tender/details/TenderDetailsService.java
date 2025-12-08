package com.vtmp.tender.details;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for accessing tender-related joined data, including driver and
 * location information.
 */
public class TenderDetailsService {

	private final TenderDetailsDao tenderDetailsDao;

	/**
	 * Initializes the service with a DAO instance.
	 */
	public TenderDetailsService() {
		this.tenderDetailsDao = new TenderDetailsDao();
	}

	/**
	 * Retrieves tender details for a specific tender ID.
	 *
	 * @param tenderId the unique identifier of the tender
	 * @return a {@link TenderDetails} object, or {@code null} if not found
	 * @throws SQLException if a database access error occurs
	 */
	public TenderDetails getTenderDetailsById(int tenderId) throws SQLException {
		return tenderDetailsDao.getTenderDetailsById(tenderId);
	}

	/**
	 * Retrieves full details for all tenders.
	 *
	 * @return a list of {@link TenderDetails}; empty list if none found
	 * @throws SQLException if a database access error occurs
	 */
	public List<TenderDetails> getAllTenderDetails() throws SQLException {
		return tenderDetailsDao.getTenderDetails();
	}

	/**
	 * Returns tender details filtered by status. Supports multiple comma-separated
	 * statuses.
	 */
	public List<TenderDetails> getTenderDetailsByStatus(String tenderStatus) throws SQLException {
		return tenderDetailsDao.getTenderDetailsByStatus(tenderStatus);
	}

	/**
	 * Returns tender details filtered by owner and status. Supports multiple
	 * comma-separated statuses.
	 */
	public List<TenderDetails> getTenderDetailsByOwnerAndStatus(String tenderStatus, int ownerId) throws SQLException {
		return tenderDetailsDao.getTenderDetailsByOwnerAndStatus(tenderStatus, ownerId);
	}

}
