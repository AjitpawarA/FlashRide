package com.alpha.FlashRide.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Booking;
import com.alpha.FlashRide.entity.Customer;
import com.alpha.FlashRide.entity.Driver;
import com.alpha.FlashRide.entity.Payment;
import com.alpha.FlashRide.entity.Vehicle;



@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

	Optional<Driver> findByMobileno(long mobileno);

    void deleteByMobileno(long mobileno);

	void save(Customer c);

	void save(Vehicle v);

	void save(Booking booking);

	void save(Payment p);
}