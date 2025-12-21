package com.alpha.FlashRide.DTO;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alpha.FlashRide.entity.Customer;



public class AvailableVehiclesDTO {
	@Autowired
	private Customer c;
	
	private double distance;
	private String sourceLocation;
	private String destination;
	
	private List<VehicleDetailsDTO> availableVehicles;

	public Customer getC() {
		return c;
	}

	public void setC(Customer c) {
		this.c = c;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double d) {
		this.distance = d;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<VehicleDetailsDTO> getAvailableVehicles() {
		return availableVehicles;
	}

	public void setAvailableVehicles(List<VehicleDetailsDTO> availableVehicles) {
		this.availableVehicles = availableVehicles;
	}

	public AvailableVehiclesDTO(Customer c, int distance, String sourceLocation, String destination,
			List<VehicleDetailsDTO> availableVehicles) {
		super();
		this.c = c;
		this.distance = distance;
		this.sourceLocation = sourceLocation;
		this.destination = destination;
		this.availableVehicles = availableVehicles;
	}

	public AvailableVehiclesDTO() {
		super();
	}

	@Override
	public String toString() {
		return "AvailableVehiclesDTO [c=" + c + ", distance=" + distance + ", sourceLocation=" + sourceLocation
				+ ", destination=" + destination + ", availableVehicles=" + availableVehicles + "]";
	}
	
	

}
