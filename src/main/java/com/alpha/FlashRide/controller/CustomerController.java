package com.alpha.FlashRide.controller;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.FlashRide.ResponseStructure;
import com.alpha.FlashRide.DTO.AvailableVehiclesDTO;
import com.alpha.FlashRide.DTO.CustomerCancelBookingResponseDTO;
import com.alpha.FlashRide.DTO.RegisterCustomerDTO;
import com.alpha.FlashRide.Service.CustomerService;
import com.alpha.FlashRide.entity.Customer;

	
	@RestController
	public class CustomerController {

	    @Autowired
	    private CustomerService customerservice;

	    @PostMapping("/registercustomer")
	    public ResponseStructure<String> registerCustomer(@RequestBody RegisterCustomerDTO dto) {
	        return customerservice.saveCustomer(dto);
	    }

	    @DeleteMapping("/deletecustomer")
	    public ResponseEntity<ResponseStructure<String>> deleteCustomer(@RequestParam long mobileNo) {
	        return ResponseEntity.ok(customerservice.deletecustomer(mobileNo));
	    }

	    @GetMapping("/findcustomer")
	    public ResponseEntity<ResponseStructure<Customer>> findCustomer(@RequestParam long mobileNo) {
	        return ResponseEntity.ok(customerservice.findCustomer(mobileNo));
	    }

	    @GetMapping("/seeavailableVehicles")
	    public ResponseStructure<AvailableVehiclesDTO> availableVehicles(@RequestParam long mobileNo,@RequestParam String destination) {
          
	        return customerservice.getAvailableVehicles(mobileNo, destination);
	    }
	    
	    @GetMapping("/seeactivebookings")
	    public ResponseStructure<Customer> CustomerSeeActiveBooking(@RequestParam long mobileno)
	    {
	    	return customerservice.findCustomer(mobileno);
	    }
	    
	    @GetMapping("/servicemethod")
	    public void servicemethod(@RequestParam long mobileNo) {
	    	customerservice.findCustomer(mobileNo);
	    }
	    
	    @PostMapping("/customercancelbooking")
	    public ResponseEntity<CustomerCancelBookingResponseDTO> cancelBooking(
	            @RequestParam("bookingid") int bookingId,
	            @RequestParam("custid") int customerId) {

	        return ResponseEntity.ok(
	                customerservice.cancelBookingByCustomer(customerId, bookingId)
	        );
	    }
	}


