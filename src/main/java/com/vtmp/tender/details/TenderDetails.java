package com.vtmp.tender.details;

import com.vtmp.driver.DriverBean;
import com.vtmp.home.admin.location.LocationBean;
import com.vtmp.tender.TenderBean;

/**
 * DTO representing tender details along with related driver and location
 * information.
 */
public class TenderDetails {
	private TenderBean tenderBean;
	private LocationBean locationBean;
	private DriverBean driverBean;

	/**
	 * Initializes the DTO with default bean instances.
	 */
	public TenderDetails() {
		this.tenderBean = new TenderBean();
		this.locationBean = new LocationBean();
		this.driverBean = new DriverBean();
	}

	public TenderBean getTenderBean() {
		return tenderBean;
	}

	public void setTenderBean(TenderBean tenderBean) {
		this.tenderBean = tenderBean;
	}

	public LocationBean getLocationBean() {
		return locationBean;
	}

	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	public DriverBean getDriverBean() {
		return driverBean;
	}

	public void setDriverBean(DriverBean driverBean) {
		this.driverBean = driverBean;
	}

}
