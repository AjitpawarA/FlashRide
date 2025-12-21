package com.alpha.FlashRide.DTO;

import com.alpha.FlashRide.entity.Booking;

public class CustomerActiveBookingDTO {

	private String customername;
	private long customerMobile;
	private Booking booking;
	private String currentLocation;
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public long getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(long customerMobile) {
		this.customerMobile = customerMobile;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public CustomerActiveBookingDTO(String customername, long customerMobile, Booking booking, String currentLocation) {
		super();
		this.customername = customername;
		this.customerMobile = customerMobile;
		this.booking = booking;
		this.currentLocation = currentLocation;
	}
	public CustomerActiveBookingDTO() {
		super();
	}
	
	
}

