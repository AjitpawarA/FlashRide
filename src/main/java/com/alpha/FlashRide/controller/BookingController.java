package com.alpha.FlashRide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.FlashRide.ResponseStructure;
import com.alpha.FlashRide.DTO.BookingDTO;
import com.alpha.FlashRide.Service.BookingService;
import com.alpha.FlashRide.entity.Booking;

@RestController
public class BookingController {
	@Autowired
	private BookingService bookingservice;
	

	@PostMapping("/bookVehicle")
	public ResponseEntity<ResponseStructure<Booking>> bookVehicle(@RequestParam Long mobileNo, @RequestBody BookingDTO bookingdto) {
		
		ResponseStructure<Booking> response = bookingservice.bookVehicle(mobileNo, bookingdto);
	    return ResponseEntity.ok(response);
	}
	@GetMapping("/bookvnotification")
	public void bookVehi() {
		bookingservice.bookveh();
	}
}
