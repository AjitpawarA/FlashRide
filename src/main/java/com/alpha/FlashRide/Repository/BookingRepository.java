package com.alpha.FlashRide.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{

	List<Booking> findByCustomerMobileNo(Long mobileNo);

	@Query("SELECT b FROM Booking b WHERE b.customer.mobileNo = :mobileNo AND LOWER(b.bookingStatus) = 'booked'")
	Booking findActiveBookingByCustomerId(@Param("mobileNo") Long mobileNo);

	
	    /**
	     * This query fetches all bookings for:
	     *  - a specific driver
	     *  - on a specific booking date (today)
	     * WHY JOIN IS REQUIRED?
	     * Booking does NOT have a direct Driver reference.
	     * Relationship chain:
	     *
	     * Booking → Vehicle → Driver
	     *
	     * JPQL joins allow us to navigate through entity relationships.
	     */
	    @Query("""
	        SELECT b FROM Booking b
	        JOIN b.vehicle v        
	        JOIN v.driver d        
	        WHERE d.id = :driverId  
	        AND b.bookingDate = :bookingDate  
	    """)
	    List<Booking> findByDriverIdAndBookingDate(
	            @Param("driverId") int driverId,
	            @Param("bookingDate") Date bookingDate);
	}


