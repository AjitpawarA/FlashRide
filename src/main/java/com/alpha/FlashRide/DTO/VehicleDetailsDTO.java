package com.alpha.FlashRide.DTO;

import org.springframework.beans.factory.annotation.Autowired;

import com.alpha.FlashRide.entity.Vehicle;

public class VehicleDetailsDTO {
	@Autowired
	private Vehicle v;
	private int fare;
	private int estimatedTime;
	private double averagespeed;
	public Vehicle getV() {
		return v;
	}
	public void setV(Vehicle v) {
		this.v = v;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public double getAveragespeed() {
		return averagespeed;
	}
	public void setAveragespeed(double averagespeed) {
		this.averagespeed = averagespeed;
	}
	public VehicleDetailsDTO(Vehicle v, int fare, int estimatedTime, double averagespeed) {
		super();
		this.v = v;
		this.fare = fare;
		this.estimatedTime = estimatedTime;
		this.averagespeed = averagespeed;
	}
	public VehicleDetailsDTO() {
		super();
	}
	
	
	
	
	

}
