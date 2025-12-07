package com.vtmp.home.owner.tender;

import java.io.IOException;
import java.sql.Date;
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
import com.vtmp.tender.details.TenderDetails;
import com.vtmp.tender.details.TenderDetailsService;
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
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.setContentType("text/plain");
						response.getWriter().write("Invalid Tender ID");
						return;
					}
					
					TenderDetails tenderDetails = tenderDetailsService.getTenderDetailsById(tender_id);
					if (tenderDetails == null) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.setContentType("text/plain");
						response.getWriter().write("Invalid Tender ID. Tender does not exist!");
						return;
					}
					
					Date tender_date = tenderDetails.getTenderBean().getTender_date();
					List<VehicleBean> vehicles = vehicleService.getFreeVehicles(ownerId, tender_date);
					List<DriverBean> drivers = driverService.getFreeDrivers(ownerId, tender_date);
					
					request.setAttribute("vehicles", vehicles);
					request.setAttribute("drivers", drivers);
					
				}
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Please logout and try again!");
				return;

			}

			request.getRequestDispatcher("/WEB-INF/views/owner/tender/acceptTender.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("INTERNAL SERVER ERROR");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
