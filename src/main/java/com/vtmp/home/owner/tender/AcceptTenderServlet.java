package com.vtmp.home.owner.tender;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtmp.driver.DriverBean;
import com.vtmp.driver.DriverService;
import com.vtmp.home.admin.owner.OwnerService;
import com.vtmp.home.admin.owner.details.OwnerDetails;
import com.vtmp.tender.TenderService;
import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
import com.vtmp.util.ErrorUtil;
import com.vtmp.util.RequestUtil;
import com.vtmp.util.SessionUtil;
import com.vtmp.vehicle.VehicleBean;
import com.vtmp.vehicle.VehicleService;

/**
 * Servlet implementation class AcceptTenderServlet
 */
@WebServlet("/owner/tender/accept")
public class AcceptTenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OwnerService ownerService = new OwnerService();
	private final DriverService driverService = new DriverService();
	private final VehicleService vehicleService = new VehicleService();
	private final TenderService tenderService = new TenderService();
	private final TenderDetailsService tenderDetailsService = new TenderDetailsService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int userId = SessionUtil.getIntParam(request, "userid");

			if (userId > 0) {
				OwnerDetails ownerDetails = ownerService.getOwnerDetailsByUserID(userId);

				if (ownerDetails != null) {
					int ownerId = ownerDetails.getOwner().getOwner_id();
					int tender_id = RequestUtil.getIntParam(request, "tid");
					if (tender_id < 1) {
						ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Tender ID");
						return;
					}

					TenderDetails tenderDetails = tenderDetailsService.getTenderDetailsById(tender_id);
					if (tenderDetails == null) {
						ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST,
								"Invalid Tender ID. Tender does not exist!");
						return;
					}

					Date tender_date = tenderDetails.getTenderBean().getTender_date();
					List<VehicleBean> vehicles = vehicleService.getFreeVehicles(ownerId, tender_date);
					List<DriverBean> drivers = driverService.getFreeDrivers(ownerId, tender_date);

					request.setAttribute("vehicles", vehicles);
					request.setAttribute("drivers", drivers);

				}
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Please logout and try again!");
				return;

			}

			request.getRequestDispatcher("/WEB-INF/views/owner/tender/acceptTender.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"INTERNAL SERVER ERROR");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int tender_id = RequestUtil.getIntParam(request, "tender_id");
		if (tender_id < 1) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Tender ID");
			return;
		}

		int vehicle_id = RequestUtil.getIntParam(request, "vehicle_id");
		if (vehicle_id < 1) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Tender ID");
			return;
		}

		int driver_id = RequestUtil.getIntParam(request, "driver_id");
		if (driver_id < 1) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Tender ID");
			return;
		}

		try {
			if (tenderService.acceptTender(tender_id, vehicle_id, driver_id)) {
				response.sendRedirect(request.getContextPath() + "/owner#r" + tender_id);
			} else {
				ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Internal Server Error");
			}
		} catch (SQLException e) {
			ErrorUtil.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal Server Error");
		}

	}

}
