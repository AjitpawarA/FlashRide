package com.alpha.FlashRide.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.FlashRide.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDriverNotFound(DriverNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Driver not found");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleCustomerNotFound(CustomerNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Customer with given mobile number not found");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoordinatesNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleCoordinatesError(CoordinatesNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.setMessage("Failed to get coordinates");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DistanceCalculationFailedException.class)
    public ResponseEntity<ResponseStructure<String>> handleDistanceError(DistanceCalculationFailedException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.setMessage("Failed to calculate distance");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidLocationException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidLocation(InvalidLocationException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Invalid Location");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGenericException(Exception ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Internal Server Error");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
  }
