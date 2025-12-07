package com.vtmp.tender;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import com.vtmp.util.RequestUtil;

/**
 * Service class for handling business operations related to tenders.
 */
public class TenderService {
	private TenderDao tenderDao;

	public TenderService() {
		this.tenderDao = new TenderDao();
	}

	/**
	 * Maps tender details from the HTTP request to a {@link TenderBean}. Validates
	 * tender date (must be today or future) and numeric fields.
	 *
	 * @param request the HttpServletRequest containing tender form parameters
	 * @return a populated {@link TenderBean}, or {@code null} if validation fails
	 */
	public TenderBean mapTenderFromRequest(HttpServletRequest request) {
		TenderBean tender = new TenderBean();

		Date sqlDate = Date.valueOf(request.getParameter("tender_date"));
		LocalDate selectedDate = sqlDate.toLocalDate();

		if (selectedDate.isBefore(LocalDate.now())) {
			return null;
		}
		tender.setTender_date(sqlDate);

		int locationId = RequestUtil.getIntParam(request, "location_id");
		if (locationId <= 0) {
			return null;
		}
		tender.setLocation_id(locationId);

		float distance = RequestUtil.getFloatParam(request, "tender_distance");
		if (distance <= 0) {
			return null;
		}
		tender.setTender_distance(distance);

		float fuelRate = RequestUtil.getFloatParam(request, "tender_fuel_rate");
		if (fuelRate <= 0) {
			return null;
		}
		tender.setTender_fuel_rate(fuelRate);

		float salary = RequestUtil.getFloatParam(request, "tender_salary");
		if (salary <= 0) {
			return null;
		}
		tender.setTender_salary(salary);

		return tender;
	}

	/**
	 * Inserts a tender and returns the generated tender ID.
	 *
	 * @param tender the {@link TenderBean} containing tender details
	 * @return the generated tender_id, or -1 if insertion fails
	 * @throws SQLException if a database access error occurs
	 */
	public int insertTender(TenderBean tender) throws SQLException {
		return tenderDao.insertTender(tender);
	}

}
