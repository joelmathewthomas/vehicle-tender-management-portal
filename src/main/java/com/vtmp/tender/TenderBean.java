package com.vtmp.tender;

import java.sql.Date;

/**
 * Bean class used for storing Tender details.
 */
public class TenderBean {
	private int tender_id;
	private int driver_id;
	private int vehicle_id;
	private int location_id;
	private Date tender_date;
	private float tender_distance;
	private float tender_fuel_rate;
	private float tender_salary;
	private String tender_status;

	public int getTender_id() {
		return tender_id;
	}

	public void setTender_id(int tender_id) {
		this.tender_id = tender_id;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public int getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public Date getTender_date() {
		return tender_date;
	}

	public void setTender_date(Date tender_date) {
		this.tender_date = tender_date;
	}

	public float getTender_distance() {
		return tender_distance;
	}

	public void setTender_distance(float tender_distance) {
		this.tender_distance = tender_distance;
	}

	public float getTender_fuel_rate() {
		return tender_fuel_rate;
	}

	public void setTender_fuel_rate(float tender_fuel_rate) {
		this.tender_fuel_rate = tender_fuel_rate;
	}

	public float getTender_salary() {
		return tender_salary;
	}

	public void setTender_salary(float tender_salary) {
		this.tender_salary = tender_salary;
	}

	public String getTender_status() {
		return tender_status;
	}

	public void setTender_status(String tender_status) {
		this.tender_status = tender_status;
	}

}
