package com.alpha.FlashRide.DTO;

import com.alpha.FlashRide.entity.Booking;
import com.alpha.FlashRide.entity.Customer;
import com.alpha.FlashRide.entity.Payment;
import com.alpha.FlashRide.entity.Vehicle;
public class RideCompletionDTO {

    private Booking booking;
    private Payment payment;
    private Vehicle vehicle;
    private Customer customer;

    // ✅ OPTIONAL (ONLY FOR UPI)
    private UpiDTO upiDetails;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UpiDTO getUpiDetails() {
        return upiDetails;
    }

    public void setUpiDetails(UpiDTO upiDetails) {
        this.upiDetails = upiDetails;
    }
}
