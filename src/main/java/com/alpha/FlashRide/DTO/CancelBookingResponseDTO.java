package com.alpha.FlashRide.DTO;

public class CancelBookingResponseDTO {

    private String message;
    private String driverStatus;
    private String bookingStatus;

    public CancelBookingResponseDTO(String message,
                                    String driverStatus,
                                    String bookingStatus) {
        this.message = message;
        this.driverStatus = driverStatus;
        this.bookingStatus = bookingStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}
