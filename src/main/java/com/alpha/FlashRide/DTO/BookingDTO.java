package com.alpha.FlashRide.DTO;


public class BookingDTO {
	
	private int vehicleid;	
	private String sourceLoc;
	private String destinationLoc;
	private int distanceTravelled;
	private int fare;
	private int estimatedTime;
	
	
	public int getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(int vehicleid) {
		this.vehicleid = vehicleid;
	}
	public String getSourceLoc() {
		return sourceLoc;
	}
	public void setSourceLoc(String sourceLoc) {
		this.sourceLoc = sourceLoc;
	}
	public String getDestinationLoc() {
		return destinationLoc;
	}
	public void setDestinationLoc(String destinationLoc) {
		this.destinationLoc = destinationLoc;
	}
	public int getDistanceTravelled() {
		return distanceTravelled;
	}
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
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
	public BookingDTO(int vehicleid, String sourceLoc, String destinationLoc,
            int distanceTravelled, int fare, int estimatedTime) {
	this.vehicleid = vehicleid;
	this.sourceLoc = sourceLoc;
	this.destinationLoc = destinationLoc;
	this.distanceTravelled = distanceTravelled;
	this.fare = fare;
	this.estimatedTime = estimatedTime;
	}

	public BookingDTO() {
		super();
	}
	@Override
	public String toString() {
		return "BookingDTO [vehicleid=" + vehicleid + ", sourceLoc=" + sourceLoc + ", destinationLoc=" + destinationLoc
				+ ", distanceTravelled=" + distanceTravelled + ", fare=" + fare + ", estimatedTime=" + estimatedTime
				+ "]";
	}
}