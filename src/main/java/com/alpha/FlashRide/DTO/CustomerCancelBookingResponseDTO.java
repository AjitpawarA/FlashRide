package com.alpha.FlashRide.DTO;

public class CustomerCancelBookingResponseDTO {
	

	    private String message;
	    private String bookingStatus;
	    private int penaltyAmount;

	    public  CustomerCancelBookingResponseDTO(String message, String bookingStatus, int penaltyAmount) {
	        this.message = message;
	        this.bookingStatus = bookingStatus;
	        this.penaltyAmount = penaltyAmount;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public String getBookingStatus() {
	        return bookingStatus;
	    }

	    public int getPenaltyAmount() {
	        return penaltyAmount;
	    }
	}


